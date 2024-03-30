package com.prjt.explorateursautonomes.joueur;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.prjt.explorateursautonomes.Explorateurs;
import com.prjt.explorateursautonomes.algo.Graph;
import com.prjt.explorateursautonomes.algo.Node;
import com.prjt.explorateursautonomes.algo.Pathfinding;
import com.prjt.explorateursautonomes.monstre.Monstre;
import com.prjt.explorateursautonomes.tresor.TreasureManager;
import com.prjt.explorateursautonomes.tresor.Tresor;

import java.util.List;

public class JoueurThread extends Thread {

    private final Joueur joueur;
    private final Graph graph;
    private Tresor tresor;
    private Monstre monstre;
    private final TiledMap world;
    private final int id;
    private final Tresor[] tresors;
    private final Monstre[] monstres;
    private final Explorateurs explorateurs;
    private final int spawnX = 496;
    private final int spawnY = 496;

    public JoueurThread(int id, Explorateurs explorateurs, Tresor[] tresors, TiledMap world, Tresor tresor, Joueur joueur, Monstre[] monstres, Monstre monstre) {
        this.joueur = joueur;
        this.graph = Pathfinding.buildGraph(world);
        this.world = world;
        this.tresor = tresor;
        this.tresors = tresors;
        this.monstres = monstres;
        this.monstre = monstre;
        this.id = id;
        this.explorateurs = explorateurs;
    }


    public void run() {

        List<Node> path = Pathfinding.AStar(graph, new Node((int) joueur.getX(), (int) joueur.getY()), new Node(tresor.getPositionX(), tresor.getPositionY()));
        joueur.setPath(path);

        long lastTime = System.currentTimeMillis();
        long timer = 0;
        while (true) {

            long currentTime = System.currentTimeMillis();
            long deltaTime = currentTime - lastTime;

            timer += deltaTime;

            lastTime = currentTime;

            while (timer >= 1000.0f / 1.0f) {
                tick();
                timer -= 1000.0f / 1.0f;
            }
        }
    }

    private void tick() {

        if (joueur.getState() == PlayerState.FINDING_TREASURE) {

            for (Monstre m : explorateurs.getMonstre()) {
                joueur.detecterProximite(m);
                if (joueur.getState() == PlayerState.COMBATTING) {
                    System.out.println("Le joueur " + id + " a lancé un combat avec un monstre !");
                    if(joueur.lancerCombat(m)){
                        System.out.println("je contine");
                        joueur.setState(PlayerState.FINDING_TREASURE);
                    }else{
                        System.out.println("jpp");
                    }
                }
            }

            if (joueur.getState() != PlayerState.FINDING_TREASURE) {
                return;
            }

            if ((int) joueur.getX() == tresor.getPositionX() && (int) joueur.getY() == tresor.getPositionY()) {
                System.out.println("Le joueur " + id + " a trouvé le trésor !");
                joueur.incrementerTresorsRecoltes();//incrementer le nombre de tresrs trouvé par le joueur
                List<Node> spawnPath = Pathfinding.AStar(graph, new Node((int) joueur.getX(), (int) joueur.getY()), new Node(spawnX, spawnY));
                joueur.setPath(spawnPath);
                joueur.setState(PlayerState.GOING_TO_SPAWN);
                tresor.setPositionY(spawnY);
                tresor.setPositionX(spawnX);
            }
        }

        if (joueur.getState() == PlayerState.GOING_TO_SPAWN) {

            if ((int) joueur.getX() == spawnX && (int) joueur.getY() == spawnY) {
                synchronized (explorateurs.getTresor()) {
                    this.tresor = TreasureManager.generateTreasure(world, tresors, id);
                    List<Node> treasurePath = Pathfinding.AStar(graph, new Node((int) joueur.getX(), (int) joueur.getY()), new Node(tresor.getPositionX(), tresor.getPositionY()));
                    joueur.setPath(treasurePath);
                    joueur.setState(PlayerState.FINDING_TREASURE);
                }
            }
        }
    }
}
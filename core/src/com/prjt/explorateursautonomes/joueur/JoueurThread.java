package com.prjt.explorateursautonomes.joueur;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.prjt.explorateursautonomes.algo.Graph;
import com.prjt.explorateursautonomes.algo.Node;
import com.prjt.explorateursautonomes.algo.Pathfinding;
import com.prjt.explorateursautonomes.tresor.TreasureManager;
import com.prjt.explorateursautonomes.tresor.Tresor;

import java.util.List;

public class JoueurThread extends Thread {

    private final Joueur joueur;
    private final Graph graph;
    private Tresor tresor;
    private final TiledMap world;
    private final int id;
    private final Tresor[] tresors;

    public JoueurThread(int id, Tresor[] tresors, TiledMap world, Tresor tresor, Joueur joueur) {
        this.joueur = joueur;
        this.graph = Pathfinding.buildGraph(world);
        this.world = world;
        this.tresor = tresor;
        this.tresors = tresors;
        this.id = id;
    }

    public void run() {

        int spawnX = 496;
        int spawnY = 496;

        List<Node> path = Pathfinding.AStar(graph, new Node((int) joueur.getX(), (int) joueur.getY()), new Node(tresor.getPositionX(), tresor.getPositionY()));
        joueur.setPath(path);

        while (true) {


            if (joueur.getState() == PlayerState.FINDING_TREASURE) {
                if (id == 0) {
                    System.out.println("TX " + tresor.getPositionX() + " TY " + tresor.getPositionY() + " PX " + joueur.getX() + " PY " + joueur.getY());
                }
                if ((int) joueur.getX() == tresor.getPositionX() && (int) joueur.getY() == tresor.getPositionY()) {
                    System.out.println("Le joueur " + id + " a trouvé le trésor !");
                    List<Node> spawnPath = Pathfinding.AStar(graph, new Node((int) joueur.getX(), (int) joueur.getY()), new Node(spawnX, spawnY));
                    joueur.setPath(spawnPath);
                    joueur.setState(PlayerState.GOING_TO_SPAWN);
                }
            }

            if (joueur.getState() == PlayerState.GOING_TO_SPAWN) {
                if ((int) joueur.getX() == spawnX && (int) joueur.getY() == spawnY) {
                    this.tresor = TreasureManager.generateTreasure(world, tresors, id);
                    List<Node> treasurePath = Pathfinding.AStar(graph, new Node((int) joueur.getX(), (int) joueur.getY()), new Node(tresor.getPositionX(), tresor.getPositionY()));
                    joueur.setPath(treasurePath);
                    joueur.setState(PlayerState.FINDING_TREASURE);
                }
            }
        }
    }
}


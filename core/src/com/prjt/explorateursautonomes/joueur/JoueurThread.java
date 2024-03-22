package com.prjt.explorateursautonomes.joueur;

import com.prjt.explorateursautonomes.algo.Graph;

public class JoueurThread extends Thread {
    private final Joueur joueur;
    private final Graph graph;

    public JoueurThread(Joueur joueur, Graph graph) {
        this.joueur = joueur;
        this.graph = graph;
    }

    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(100); // Attente entre chaque mouvement
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

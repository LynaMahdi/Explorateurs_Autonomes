package com.prjt.explorateursautonomes.joueur;

import java.util.ArrayList;

public class JoueurInitializer {

    public static ArrayList<Joueur> initializePlayers(int numberOfPlayers) {
        ArrayList<Joueur> listOfPlayers = new ArrayList<Joueur>();

        switch (numberOfPlayers) {
            case 1:
                listOfPlayers.add(new Joueur(50, 20, 5, 8 * 16, 320 - 16, 1));
                break;
            case 2:
                listOfPlayers.add(new Joueur(50, 20, 5, 8 * 16, 320 - 16, 1));
                listOfPlayers.add(new Joueur(50, 20, 5, 896, 384, 1));
                break;
            case 3:
                listOfPlayers.add(new Joueur(50, 20, 5, 8 * 16, 320 - 16, 1));
                listOfPlayers.add(new Joueur(50, 20, 5, 896, 384, 1));
                listOfPlayers.add(new Joueur(50, 20, 5, 144, 672, 1));
                break;
            case 4:
                listOfPlayers.add(new Joueur(50, 20, 5, 8 * 16, 320 - 16, 1));
                listOfPlayers.add(new Joueur(50, 20, 5, 896, 384, 1));
                listOfPlayers.add(new Joueur(50, 20, 5, 144, 672, 1));
                listOfPlayers.add(new Joueur(50, 20, 5, 784, 768, 1));
                break;
            default:
                // Handle unsupported number of players
                break;
        }

        return listOfPlayers;
    }
}

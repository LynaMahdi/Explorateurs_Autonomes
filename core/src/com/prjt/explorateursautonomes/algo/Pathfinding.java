package com.prjt.explorateursautonomes.algo;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.*;

public class Pathfinding {



    public static Graph buildGraph(TiledMap world) {
        TiledMapTileLayer[] obstacleLayers = {
            (TiledMapTileLayer) world.getLayers().get("obstacle"),
            (TiledMapTileLayer) world.getLayers().get("mur supp2"),
            (TiledMapTileLayer) world.getLayers().get("mur inférieur"),
            (TiledMapTileLayer) world.getLayers().get("mur supérieur"),
            (TiledMapTileLayer) world.getLayers().get("déco")
        };
        Graph graph = new Graph();
        for (int x = 0; x < 950; x += 16) {
            for (int y = 0; y < 950; y += 16) {
                boolean solid = Pos(x,y,obstacleLayers);
                Node node = new Node(x, y);
                if (!solid) {
                    graph.addNode(node);
                    graph.getSolidNodes().put(node, solid);
                }
            }
        }
        graph.connectNodes();
        return graph;
    }

    private static  boolean Pos(int x, int y, TiledMapTileLayer[] obstacleLayers) {
        for (TiledMapTileLayer obstacleLayer : obstacleLayers) {
            // Check if the position is not on an obstacle in any of the layers
            TiledMapTileLayer.Cell cell = obstacleLayer.getCell(x / obstacleLayer.getTileWidth(), y / obstacleLayer.getTileHeight());
            if (cell != null && cell.getTile() != null) {
                return true; // obstacle trouvé
            }
        }
        return false; // pas d'obstacle
    }


    private static List<Node> buildPath(Node node) {
        List<Node> path = new ArrayList<>();
        Node current = node.getParent();
        while (current != null) {
            path.add(current);
            current = current.getParent();
            if (path.contains(current)) {
                break;
            }
        }
        Collections.reverse(path);
        path.add(node);
        return path;
    }

    public static List<Node> AStar(Graph graph, Node start, Node end) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparing(Node::getFCost));
        Set<Node> closedList = new HashSet<>();

        for (Node node : graph.getNodes().keySet()) {
            node.setGCost(node.distance(start));
            node.setHCost(node.distance(end));
            for (Node neighbor : graph.getNeighbors(node)) {
                neighbor.setGCost(neighbor.distance(start));
                neighbor.setHCost(neighbor.distance(end));
            }
        }
        start.setGCost(0);
        start.setHCost(start.distance(end));
        openList.add(start);

        while (!openList.isEmpty()) {
            Node node = openList.poll();

            if (node.getX() == end.getX() && node.getY() == end.getY()) {
                return buildPath(node);
            }

            for (Node neighbor : graph.getNeighbors(node)) {

                float newCost = node.getGCost() + node.distance(neighbor);

                if (!closedList.contains(neighbor) || newCost < neighbor.getGCost()) {
                    neighbor.setGCost(newCost);
                    neighbor.setParent(node);
                    neighbor.setFCost(neighbor.getGCost() + neighbor.getHCost());
                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }

            closedList.add(node);
        }

        return new ArrayList<>();
    }

}

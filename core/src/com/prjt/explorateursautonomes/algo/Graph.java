package com.prjt.explorateursautonomes.algo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {

    private final Map<Node, Set<Node>> nodes;
    private final Map<Node, Node> world;
    private final Map<Node, Boolean> solidNodes;

    public Graph() {
        this.nodes = new HashMap<>();
        this.world = new HashMap<>();
        this.solidNodes = new HashMap<>();
    }

    public void addNode(Node node) {
        nodes.put(node, new HashSet<>());
        world.put(node, node);
    }

    public void addLink(Node a, Node b) {
        nodes.get(a).add(b);
        nodes.get(b).add(a);
    }

    public Set<Node> getNeighbors(Node node) {
        Set<Node> neighbors = nodes.get(node);
        if (neighbors == null) {
            return new HashSet<>();
        }
        return neighbors;
    }

    public Map<Node, Set<Node>> getNodes() {

        return nodes;
    }

    private Node[] getAdjacentsNode(Node node) {

        int x = node.getX();
        int y = node.getY();

        return new Node[] {

            world.get(new Node(x + 16, y)),
            world.get(new Node(x - 16, y)),
            world.get(new Node(x, y + 16)),
            world.get(new Node(x, y - 16)),

        };

    }

    public void connectNodes() {
        for (Node node : nodes.keySet()) {

            Node[] adjacentsNode = this.getAdjacentsNode(node);

            for (Node adjacentNode : adjacentsNode) {
                if (adjacentNode != null) {
                    if (!solidNodes.get(adjacentNode)) {
                        this.addLink(node, adjacentNode);
                    }
                }
            }
        }
    }

    public Map<Node, Boolean> getSolidNodes() {
        return solidNodes;
    }

    @Override
    public String toString() {
        String graph = "";
        for (Node node : nodes.keySet()) {
            graph += node + " " + node.hashCode() + " -> ";
            for (Node neighbor : this.getNeighbors(node)) {
                graph += neighbor + " " + neighbor.hashCode() + ", ";
            }
            graph += "\n";
        }
        return graph;
    }

    public Map<Node, Node> getWorld() {
        return world;
    }
}

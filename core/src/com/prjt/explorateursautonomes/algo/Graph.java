package com.prjt.explorateursautonomes.algo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {

    private final Map<Node, Set<Node>> nodes;

    public Graph() {
        this.nodes = new HashMap<>();
    }

    public void addNode(Node node) {
        nodes.put(node, new HashSet<>());
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
            new Node(x + 16, y, false),
            new Node(x - 16, y, false),
            new Node(x, y + 16, false),
            new Node(x, y - 16, false),
        };
    }

    public void connectNodes() {
        for (Node node : nodes.keySet()) {
            Node[] adjacentsNode = this.getAdjacentsNode(node);
            for (Node adjacentNode : adjacentsNode) {
                if (nodes.containsKey(adjacentNode)) {
                    this.addLink(node, adjacentNode);
                }
            }
        }
    }

    @Override
    public String toString() {
        String graph = "";
        for (Node node : nodes.keySet()) {
            graph += node + "->";
            for (Node neighbor : this.getNeighbors(node)) {
                graph += neighbor + ", ";
            }
            graph += "\n";
        }
        return graph;
    }
}

package com.prjt.explorateursautonomes.algo;

import java.util.Objects;

public class Node {

    private final int x, y;
    private float gCost, hCost, fCost;
    private final boolean solid;
    private Node parent;

    public Node(int x, int y, boolean solid) {
        this.x = x;
        this.y = y;
        this.gCost = -1;
        this.hCost = -1;
        this.fCost = -1;
        this.solid = solid;
        this.parent = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isSolid() {
        return solid;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public float getGCost() {
        return gCost;
    }

    public void setGCost(float gCost) {
        this.gCost = gCost;
    }

    public float getHCost() {
        return hCost;
    }

    public void setHCost(float hCost) {
        this.hCost = hCost;
    }

    public float getFCost() {
        return fCost;
    }

    public void setFCost(float fCost) {
        this.fCost = fCost;
    }

    public float distance(Node node) {
        return Math.abs(node.x - this.x) + Math.abs(node.y - this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", solid=" + solid +
                '}';
    }
}

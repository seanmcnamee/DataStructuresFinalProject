package app.tree;

//import java.util.Scanner;

/* Class BTNode */
public class BTNode {
    public BTNode left, right;
    private int startValues, endValues;
    private Glitch glitch;

    /* Constructor */
    public BTNode() {
        left = null;
        right = null;
        startValues = 0;
        endValues = 0;
        glitch = null;
    }

    /* Constructor */
    public BTNode(int startV, int endV, Glitch g) {
        left = null;
        right = null;
        startValues = startV;
        endValues = endV;
        glitch = g;
    }

    public BTNode(int startV, int endV, int startTime, int endTime) {
        left = null;
        right = null;
        startValues = startV;
        endValues = endV;
        glitch = new Glitch(startTime, endTime);
    }

    /* Function to set left node */
    public void setLeft(BTNode n) {
        left = n;
    }

    /* Function to set right node */
    public void setRight(BTNode n) {
        right = n;
    }

    /* Function to get left node */
    public BTNode getLeft() {
        return left;
    }

    /* Function to get right node */
    public BTNode getRight() {
        return right;
    }

    /* Function to set data to node */
    public void setData(int startV, int endV, Glitch g) {
        startValues = startV;
        endValues = endV;
        glitch = g;
    }

    /* Function to get data from node */
    public int getStartValues() {
        return startValues;
    }

    public int getEndValues() {
        return endValues;
    }

    public Glitch getGlitch() {
        return glitch;
    }
}
package app.circuitNode;

import app.linkedList.MyLinkedList;

/**
 * Node
 */
public interface CircuitNode {
    public MyLinkedList getDelays();
    public boolean getValue();
    public int getGateDelay();
    
}
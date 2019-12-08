package app.circuitNode;

import app.Circuit;
import app.linkedList.MyLinkedList;
import app.linkedList.LinkedListNode;

/**
 * Data
 */
public class Data implements CircuitNode {
    private boolean value;

    public Data(boolean value) {
        this.value = value;
    }

    @Override
    public boolean getValue() {
        return this.value;
    }

    @Override
    /**
     * The delay directly from an input is 0
     */
    public MyLinkedList getDelays() {
        return new MyLinkedList(new LinkedListNode(0));
    }

    @Override
    public int getGateDelay() {
        return 0;
    }

    public void setValue(boolean newBool) {
        this.value = newBool;
    }

    public Character getString(Circuit c) {
        return c.findKey(this);
    }


}
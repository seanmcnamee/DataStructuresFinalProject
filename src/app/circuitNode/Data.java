package app.circuitNode;

import java.util.Hashtable;

import app.Circuit;
import app.linkedList.LinkedList;
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

    public void setValue(boolean newBool) {
        this.value = newBool;
    }

    public Character getString(Circuit c) {
        return c.findKey(this);
    }

    @Override
    /**
     * The delay directly from an input is 0
     */
    public LinkedList getDelays() {
        System.out.println("Giving back a zero delay");
        return new LinkedList(new LinkedListNode(0));
    }
}
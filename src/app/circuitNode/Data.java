package app.circuitNode;

import java.util.Hashtable;


import app.Circuit;
/**
 * Data
 */
public class Data implements CircuitNode{
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
}
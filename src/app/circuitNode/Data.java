package app.circuitNode;

/**
 * Data
 */
public class Data implements Node{
    private boolean value;

    public Data(boolean value) {
        this.value = value;
    }
    
    @Override
    public boolean getValue() {
        return value;
    }
}
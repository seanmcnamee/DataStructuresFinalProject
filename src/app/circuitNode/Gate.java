package app.circuitNode;

/**
 * Gate
 */
public class Gate implements Node{

    private Node leftInput, rightInput;
    private enum TYPE {AND, OR, NOT};



    @Override
    public boolean getValue() {
        return false;
    }

    
}
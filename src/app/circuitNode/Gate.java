package app.circuitNode;

import java.util.Hashtable;

import app.Circuit;
import app.linkedList.LinkedList;

/**
 * Gate
 */
public class Gate implements CircuitNode {

    // Gates have a left and right input (NOT only uses the leftInput)
    private final CircuitNode leftInput, rightInput;

    // Gate types and their delays
    public enum TYPE {
        AND, OR, NOT
    };

    private int getGateDelay() {
        if (operator == TYPE.AND)
            return 25;
        if (operator == TYPE.OR)
            return 20;
        if (operator == TYPE.NOT)
            return 10;
        // else
        return 0;
    }

    // The type of gate
    private final TYPE operator;

    // All the times this gate is updated
    private LinkedList setOfDelays;

    // Internal node for gate
    private boolean internalValue;

    public Gate(final CircuitNode leftInput, final TYPE operator, final CircuitNode rightInput) {
        this.leftInput = leftInput;
        this.rightInput = rightInput;
        this.operator = operator;
        calcGateDelays();
    }

    @Override
    public boolean getValue() {
        return internalValue;
    }

    @Override
    public LinkedList getDelays() {
        return setOfDelays;
    }

    /**
     * Calculate internal node value
     */
    public void calcValue() {
        if (this.operator == TYPE.AND) {
            internalValue = leftInput.getValue() && rightInput.getValue();
        } else if (this.operator == TYPE.OR) {
            internalValue = leftInput.getValue() || rightInput.getValue();
        } else if (this.operator == TYPE.NOT) {
            internalValue = !leftInput.getValue();
        } else {
            System.out.println("Error in Gate.java with operator: " + this.operator);
            System.exit(-1);
        }
    }

    // Form list of gate delay times
    private void calcGateDelays() {
        // Possible operator only gates
        if (this.leftInput != null || this.rightInput != null) {
            if (this.rightInput == null) {
                this.setOfDelays = this.leftInput.getDelays();
            } else {
                this.setOfDelays = LinkedList.sortWithOther(this.leftInput.getDelays(), this.rightInput.getDelays());
            }
            this.setOfDelays.RemoveDuplicates();
            System.out.println("Delays:");
            this.setOfDelays.printList();
            System.out.println("Adding " + getGateDelay() + " to each delay");
            this.setOfDelays.addToEach(getGateDelay());
            this.setOfDelays.printList();
        }

    }

    public Gate.TYPE getOperator() {
        return operator;
    }

    public CircuitNode getLeftInput() {
        return this.leftInput;
    }

    public CircuitNode getRightInput() {
        return this.rightInput;
    }

    ///////////////////// ONLY USED FOR OUTPUT FOR USER////////////////////////////
    /**
     * Recursive, Follows the the circuit down to its smallest parts
     */

    public String getGateString(final Circuit c) {
        return  getLeftString(c) + getStringOperator() + getRightString(c);
    }

    public String getLeftString(final Circuit c) {
        if (this.leftInput.getClass() == Gate.class) {
            return "("+((Gate) this.leftInput).getGateString(c)+")";
        } else {
            return ((Data) this.leftInput).getString(c) + "";
        }
    }

    public String getRightString(final Circuit c) {
        if (this.rightInput == null) {
            return "";
        } else if (this.rightInput.getClass() == Gate.class) {
            return "("+((Gate) this.rightInput).getGateString(c)+")";
        } else {
            return ((Data) this.rightInput).getString(c) + "";
        }
    }

    public Character getStringOperator() {
        switch (this.operator) {
        case AND:
            return '*';
        case OR:
            return '+';
        case NOT:
            return '~';
        default:
            System.out.println("Error! Wrong Operator in this GATE!");
            return '#';
        }
    }
}
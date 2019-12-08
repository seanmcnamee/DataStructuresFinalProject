package app.circuitNode;

import java.util.LinkedList;
import java.util.Queue;

import app.Circuit;
import app.linkedList.MyLinkedList;

/**
 * Gate
 */
public class Gate implements CircuitNode {

    // Gates have a left and right input (NOT only uses the leftInput)
    private final CircuitNode leftInput, rightInput;

    // The type of gate
    private final TYPE operator;

    // All the times this gate TAKES PREVIOUS inputs. 
    private MyLinkedList setOfDelays;

    //TODO add support for changeing internal node
    Queue<DelayUpdate> gettingDelays = new LinkedList<DelayUpdate>();
    Queue<DelayUpdate> settingDelays = new LinkedList<DelayUpdate>();

    // Internal node for gate
    private boolean internalValue;

    public Gate(final CircuitNode leftInput, final TYPE operator, final CircuitNode rightInput) {
        this.leftInput = leftInput;
        this.rightInput = rightInput;
        this.operator = operator;
        calcGateDelays();
    }

    // Gate types and their delays
    public enum TYPE {
        AND, OR, NOT
    };

    public int getGateDelay() {
        if (operator == TYPE.AND)
            return 25;
        if (operator == TYPE.OR)
            return 20;
        if (operator == TYPE.NOT)
            return 10;
        // else
        return 0;
    }

    private class DelayUpdate {
        int inputTime, outputTime;
        boolean internalNode = false;

        public DelayUpdate(int inputTime, int gateDelay) {
            this.inputTime = inputTime;
            this.outputTime = this.inputTime+gateDelay;
            internalNode = false;
        }

        public void setInternalNode(boolean inputValue) {
            internalNode = inputValue;
        }

        public boolean getInternalNode() {
            return internalNode;
        }
    }

    @Override
    public boolean getValue() {
        return internalValue;
    }

    @Override
    public MyLinkedList getDelays() {
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
                this.setOfDelays.addToEach(this.leftInput.getGateDelay());
            } else {
                this.setOfDelays = MyLinkedList.sortWithOtherAdded(this.leftInput.getDelays(), this.leftInput.getGateDelay(), this.rightInput.getDelays(), this.rightInput.getGateDelay());
            }
            this.setOfDelays.RemoveDuplicates();
            System.out.println("Delays:");
            this.setOfDelays.printList();
            //System.out.println("Adding " + getGateDelay() + " to each delay");
            //this.setOfDelays.addToEach(getGateDelay());
            //this.setOfDelays.printList();
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
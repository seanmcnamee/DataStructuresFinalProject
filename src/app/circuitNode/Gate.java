package app.circuitNode;

import java.util.LinkedList;
import java.util.Queue;

import app.Circuit;
import app.linkedList.LinkedListNode;
import app.linkedList.MyLinkedList;

/**
 * Gate: Uses a Queue for setting the internal nodes in the proper order
 */
public class Gate implements CircuitNode {

    // Gates have a left and right input (NOT only uses the leftInput)
    private final CircuitNode leftInput, rightInput;

    // The type of gate
    private final TYPE operator;

    // All the times this gate TAKES PREVIOUS inputs. 
    private MyLinkedList setOfDelays;

    //Queues for when to update internalValue
    private Queue<DelayUpdate> inputDelays = new LinkedList<DelayUpdate>();
    private Queue<DelayUpdate> outputDelays = new LinkedList<DelayUpdate>();

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

    /**
     * Calculate and return internal node value
     * Used when the internal node has to be updated
     */
    public boolean calcValue() {
        boolean newInternal;
        if (this.operator == TYPE.AND) {
            newInternal = leftInput.getValue() && rightInput.getValue();
        } else if (this.operator == TYPE.OR) {
            newInternal = leftInput.getValue() || rightInput.getValue();
        } else if (this.operator == TYPE.NOT) {
            newInternal = !leftInput.getValue();
        } else {
            System.out.println("Error in Gate.java with operator: " + this.operator);
            System.exit(-1);
            newInternal = internalValue;
        }
        return newInternal;
    }

    /**
     * Form list of gate delay times 
     * happens when creating the gate
     */
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

    ////////////InternalValue updating with the Queues/////////////////
    //Important for waiting before updating the internalNode of the gate.
    private class DelayUpdate {
        int inputTime, outputTime;
        boolean internalNode = false;

        public DelayUpdate(int inputTime, int gateDelay) {
            this.inputTime = inputTime;
            this.outputTime = this.inputTime+gateDelay;
            internalNode = false;
        }

        //Getters and Setters
        public void setInternalNode(boolean inputValue) {   internalNode = inputValue;  }
        public boolean getInternalNode() {  return internalNode;    }
        public int getInputTime() {     return inputTime;   }
        public int getOutputTime() {    return outputTime;  }
    }

    public void initializeInputDelays() {
        LinkedListNode trav = this.setOfDelays.getRoot();
        while(trav != null) {
            inputDelays.add(new DelayUpdate(trav.getData(), getGateDelay()));
            trav = trav.getNext();
        }
    }

    public void takeInputsAndTransfer() {
        inputDelays.peek().setInternalNode(calcValue());
        outputDelays.add(inputDelays.remove());
    }

    public void updateInternalAndRemove() {
        internalValue = outputDelays.remove().getInternalNode();
    }

    public int getNextInputTime() { 
        return inputDelays.peek().getInputTime();
    }

    public int getNextOutputTime() {
        return outputDelays.peek().getOutputTime();
    }


    /////////////////Getters for variables of the Gate///////////////////////
    public Gate.TYPE getOperator() {
        return operator;
    }

    public CircuitNode getLeftInput() {
        return this.leftInput;
    }

    public CircuitNode getRightInput() {
        return this.rightInput;
    }

    @Override
    public boolean getValue() {
        return internalValue;
    }

    @Override
    public MyLinkedList getDelays() {
        return setOfDelays;
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
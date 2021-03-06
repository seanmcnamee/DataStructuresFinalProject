package app.circuitNode;

import java.util.LinkedList;
import java.util.Queue;

import app.Circuit;
import app.linkedList.LinkedListNode;
import app.linkedList.MyLinkedList;

/**
 * Gate: represents some type of operation and at least one input
 * 
 * //////////Project Requirements/////////
 * Uses a Queue for setting the internal nodes in the proper order
 * Uses a LinkedList for organizing all the delay times for this Gate
 */
public class Gate implements CircuitNode {

    // Gates have a left and right input (NOT only uses the rightInput)
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
        if (this.rightInput != null) {
            internalValue = calcValue();
        }
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
            newInternal = !rightInput.getValue();
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
            if (this.leftInput == null) {
                this.setOfDelays = this.rightInput.getDelays();
                this.setOfDelays.addToEach(this.rightInput.getGateDelay());
            } else {
                this.setOfDelays = MyLinkedList.sortWithOtherAdded(this.leftInput.getDelays(), this.leftInput.getGateDelay(), this.rightInput.getDelays(), this.rightInput.getGateDelay());
            }
            this.setOfDelays.RemoveDuplicates();
            //System.out.println("Delays:");
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

    public void initializeInputQueue() {
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
        //System.out.print("Node: " + this);
        this.internalValue = outputDelays.remove().getInternalNode();
        //System.out.print("Updates to " + this.internalValue);
    }

    public int largestQueueSize() {
        return Math.max(inputDelays.size(), outputDelays.size());
    }

    public int getNextInputTime() { 
        if (inputDelays.size() > 0) {
            return inputDelays.peek().getInputTime();
        }   else {
            return -1;
        }
    }

    public int getNextOutputTime() {
        if (outputDelays.size() > 0) {
            return outputDelays.peek().getOutputTime();
        }   else {
            return -1;
        }
    }

    public String queueLengths() { 
        return "input Length: " + inputDelays.size() + ", Output Length: " + outputDelays.size();
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
        return  getLeftString(c) + getStringOperator(c) + getRightString(c);
    }

    public String getLeftString(final Circuit c) {
        if (this.leftInput == null) {
            return "";
        }   else if (this.leftInput.getClass() == Gate.class) {
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

    public Character getStringOperator(final Circuit c) {
        switch (this.operator) {
        case AND:
            return c.MULT;
        case OR:
            return c.ADD;
        case NOT:
            return c.NOT;
        default:
            System.out.println("Error! Wrong Operator in this GATE!");
            return '#';
        }
    }
}
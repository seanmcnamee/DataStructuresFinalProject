package app.circuitNode;

import java.util.Hashtable;

import app.Circuit;

/**
 * Gate
 */
public class Gate implements CircuitNode{

    private CircuitNode leftInput, rightInput;
    public enum TYPE {AND, OR, NOT};
    private TYPE operator;
    private int[] gateDelays;
    private boolean internalValue;

    public Gate(CircuitNode leftInput, TYPE operator, CircuitNode rightInput) {
        this.leftInput = leftInput;
        this.rightInput = rightInput;
        this.operator = operator;
    }

    @Override
    public boolean getValue() {
        return internalValue;
    }

    public void calcValue() {
        if (this.operator == TYPE.AND) {
            internalValue = leftInput.getValue()&&rightInput.getValue();
        }   else if (this.operator == TYPE.OR) {
            internalValue = leftInput.getValue()||rightInput.getValue();
        }   else if (this.operator == TYPE.NOT) {
            internalValue = !leftInput.getValue();
        }   else {
            System.out.println("Error in Gate.java with operator: " + this.operator);
            System.exit(-1);
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

    public void calcGateDelay(int delayAND, int delayOR, int delayNOT) {

    }

    public void getGateDelay() {
        //return gateDelays;
    }
    


    /////////////////////ONLY USED FOR OUTPUT FOR USER////////////////////////////
    /**
     * Recursive, Follows the the circuit down to its smallest parts
     */

    public String getGateString(Circuit c) {
        return "(" + getLeftString(c) + ")" + getStringOperator() + "(" + getRightString(c) + ")";
    }

    public String getLeftString(Circuit c) {
        if (this.leftInput.getClass() == Gate.class) {
            return ((Gate)this.leftInput).getGateString(c);
        }   else {
            return ((Data)this.leftInput).getString(c)+"";
        }
    }

    public String getRightString(Circuit c) {
        if (this.rightInput == null) {
            return "";
        }   else if (this.rightInput.getClass() == Gate.class) {
            return ((Gate)this.rightInput).getGateString(c);
        }   else {
            return ((Data)this.rightInput).getString(c)+"";
        }
    }

    public Character getStringOperator() {
        switch(this.operator) {
            case AND: return '*';
            case OR: return '+';
            case NOT: return '~';
            default:
                System.out.println("Error! Wrong Operator in this GATE!");
                return '#';
        }
    }
}
package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Stack;

import app.circuitNode.Data;
import app.circuitNode.Gate;
import app.circuitNode.CircuitNode;

/**
 * Represents a Circuit in terms of a Node (Gate and Data) Array.
 * Includes a Hashtable to easily manipulate Data inputs
 */
public class Circuit {

    final char START = '(', END = ')', ADD = '+', MULT = '*', NOT = '~';
    private CircuitNode[] nodes; //All the Important points inside the circuit
    private Hashtable<Character, Data> inputs = new Hashtable<Character, Data> (); //Maps Data to user's characters
    private int firstInput = Integer.MAX_VALUE; //Remembers ASCII of a-Most character

    // Take a file to produce circuit
    public Circuit(File path) {
        String equation = "";
        Scanner input;
        try {
            input = new Scanner(path);
            equation = input.nextLine();
            equationToCircuit(equation);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("BAD");
        }
    }

    public Circuit(String equation) {
        equationToCircuit(equation);
    }


    //TODO make sure the length of this integer is good for the inputs. If not, add FALSE's to the top. Call the below method.
    public void setInputs(int inputs) {
    }

    //Assumes correct size of array, with inputs[0] as the a-Most character
    public void setInputs(boolean[] inputValues) {
        int currentInputAscii = firstInput;
        for (boolean b : inputValues) {
            while (this.inputs.get((char)currentInputAscii) == null) {
                currentInputAscii++;
            }
            this.inputs.get((char)currentInputAscii).setValue(b);
            currentInputAscii++;
        }
    }


    /**
     * Uses a stack to create the Node array and the Mapping of Characters to Data inputs
     * @param equation
     */
    public void equationToCircuit(String equation) {
        int equationNum = 0;
        Stack<CircuitNode> terms = new Stack<CircuitNode>(); //Remember the order of how things are seen in the equation
        this.nodes = new CircuitNode[countOperators(equation)]; //Each of the terms (will be sorted with timings)
        int equationLength = equation.length();

        //Look at each part of the equation
        for (int i = 0; i < equationLength; i++) {
            System.out.println("Equation: " + equation);
            System.out.println("\tLocation: " + i + " < " + equationLength);
            char index = equation.charAt(i);
            switch(index) {
                //A closing parenthasis, create a new Element (using operator and data point(s))
                case START:
                    break;
                case END: //Create a new (Node) Element, and update the position in the array 
                    System.out.println("\tStacking Node");
                    //Create new term
                    addElement(terms, equationNum);
                    equationNum++;
                    break;
                case ADD: //Operator (AND/MULT/NOT), Add a full gate to the stack, but only the operator field
                    System.out.println("\tStacking Operator +");
                    terms.add(new Gate(null, Gate.TYPE.OR, null));
                    break;
                case MULT:
                    System.out.println("\tStacking Operator *");
                    terms.add(new Gate(null, Gate.TYPE.AND, null));
                    break;
                case NOT:
                    System.out.println("\tStacking Operator ~");    
                    terms.add(new Gate(null, Gate.TYPE.NOT, null));
                    break;
                
                default: //Regular input, add a Data to the stack.
                    System.out.println("\tStacking Data " + index);
                    //Remembers the smallest input, and pairs each Data Input with the user's character key
                    if (inputs.containsKey(index)) {
                        System.out.println("\t\tIt already exists");
                        terms.add(inputs.get(index));
                    }   else { //Only add a mapping for new characters
                        System.out.println("\t\tNew Data point");
                        if ((int)index < firstInput) {
                            firstInput = (int)index;
                        }
                        CircuitNode temp = new Data(false);
                        inputs.put(index, (Data)temp);
                        terms.add(temp);
                    }
            }
        }

    }
    
    /**
     * Used to figure out how big the array of Nodes has to be.
     * @param equation The user inputted String with
     * @return The number of *, +, and ~ in the equation.
     */
    private int countOperators(String equation) {
        int count = 0;
        for (int i = 0; i < equation.length(); i++) {
            char index = equation.charAt(i);
            if (index == ADD || index == MULT || index == NOT) {
                count++;
            }
        }
        return count;
    }

    /**
     * Uses the top Circuit Nodes from the stack to create a new Gate.
     * This new Gate is added to the stack and the this.nodes array
     * @param terms The stack which is used for ordering terms (Nodes)
     * @param equationNum the location in the array to place the new complete Gate
     */
    private void addElement(Stack<CircuitNode> terms, int equationNum) {
        CircuitNode fullElement, term;
        term = terms.pop(); //Top element on the stack can be a Data or Gate.
        Gate operator = (Gate)terms.pop(); //Second element on the stack MUST be an Operator (partial Gate)

        //A NOT only requires one Data point. AND/OR require two
        if (operator.getOperator() == Gate.TYPE.NOT) {
            fullElement = new Gate(term, operator.getOperator(), null);
        }   else {
            fullElement = new Gate(terms.pop(), operator.getOperator(), term);
        }

        //The fullElement might become an input to future Gates. Either way, store it into the array.
        terms.add(fullElement);
        nodes[equationNum] = (fullElement);
    }

    /**
     * @return The Character that the user inputted to represent this Node
     * @param value The internally created/stored Circuit Node
     */
    public Character findKey(CircuitNode value) {
        for (Character key : inputs.keySet()) {
            if (inputs.get(key) == value) {
                return key;
            }
        }
        return '_';
    }

    public Hashtable<Character, Data> getInputs() {
        return inputs;
    }

    public CircuitNode[] getNodes() {
        return nodes;
    }
}
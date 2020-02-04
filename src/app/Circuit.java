package app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Stack;

import app.circuitNode.Data;
import app.circuitNode.Gate;
import app.circuitNode.CircuitNode;

/**
 * 
 * Includes a Hashtable to easily manipulate Data inputs
 * 
 * //////Project Requirments//////// Represents a Circuit in terms of a Node
 * (Gate and Data) Array. Includes a BubbleSort to sort the nodes Includes a
 * Stack used when translating from a String equation to its representation
 */
public class Circuit {

    public final char START = '(', END = ')', ADD = '+', MULT = '*', NOT = '~', MULTREPLACEMENT = (char)128;
    private Gate[] nodes; // All the Important points inside the circuit
    private Hashtable<Character, Data> inputs = new Hashtable<Character, Data>(); // Maps Data to user's characters
    private int firstInput = Integer.MAX_VALUE; // Remembers ASCII of a-Most character
    private Gate outputGate;
    private String circuitName;

    // Take a file to produce circuit
    public Circuit(File path) {
        String equation = "";
        Scanner input;
        try {
            input = new Scanner(path);
            equation = input.nextLine();
            equationToCircuit(equation);
            this.circuitName = equation.replace(MULT, MULTREPLACEMENT);
            writeCircuit();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("BAD");
        }
    }

    public Circuit(String equation) {
        equationToCircuit(equation);
        this.circuitName = equation.replace(MULT, MULTREPLACEMENT);
        writeCircuit();
    }

    //For the file that the user can read...
    private void writeCircuit() {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(getOutputFile()));
            writeData(writer);
            writeGates(writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Explains Data input info
    private void writeData(BufferedWriter writer) throws IOException {
        writer.write("Data inputs:\n");
        for (Character key : inputs.keySet()) {
            writer.append(key + " - " + inputs.get(key)+"\n");
        }
    }

    //Explains all the gates of the circuit
    private void writeGates(BufferedWriter writer) throws IOException {
        writer.append("\nCircuit Gates:\n");

        for (Gate s : nodes) {
            writer.append("Memeory Loc: " + s);
            if (s.getClass() == Gate.class) {
                writer.append("\t"+s.getGateString(this)+"\n");
                (s).getDelays().printList(writer);
            }
        }

        writer.append("\n");
    }



    /**
     * Translates an integer into a boolean array, then calls setInputs(boolean[] b)
     * @param inputValues A base 2 integer, where 1 represents true and 0 represents false
     */
    public void setInputs(int inputValues) {
        String strEnteredInputs = inputValues+"";
        int enteredInputs = strEnteredInputs.length();
        int numOfInputs = inputs.size();
        int amountToAdd = numOfInputs-enteredInputs;
        if(amountToAdd < 0) amountToAdd = 0;
        boolean[] boolInputs = new boolean[inputs.size()];

        //System.out.println("Entered: " + strEnteredInputs + ", length: " + enteredInputs + ", numOfInputs: " + numOfInputs + ", amountToAdd: " + amountToAdd);
        for (int i = 0; i < numOfInputs; i++) {
            if (i < amountToAdd) {
                boolInputs[i] = false;
            }   else {
                boolInputs[i] = (strEnteredInputs.charAt(i-amountToAdd)=='1');
            }
        }
        
        setInputs(boolInputs);
    }

    //Assumes correct size of array, with inputs[0] as the a-Most character
    public void setInputs(boolean[] inputValues) {
        int currentInputAscii = firstInput;
        for (boolean b : inputValues) {
            while (this.inputs.get((char)currentInputAscii) == null) {
                currentInputAscii++;
            }
            //System.out.println("Bool: " + b + " for " + (char)currentInputAscii);
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
        this.nodes = new Gate[countOperators(equation)]; //Each of the terms (will be sorted with timings)
        int equationLength = equation.length();

        //Look at each part of the equation
        for (int i = 0; i < equationLength; i++) {
            //System.out.println("Equation: " + equation);
            //System.out.println("\tLocation: " + i + " < " + equationLength);
            char index = equation.charAt(i);
            switch(index) {
                //A closing parenthasis, create a new Element (using operator and data point(s))
                case START:
                    break;
                case END: //Create a new (Node) Element, and update the position in the array 
                    //System.out.println("\tStacking Node");
                    //Create new term
                    addElement(terms, equationNum);
                    equationNum++;
                    break;
                case ADD: //Operator (AND/MULT/NOT), Add a full gate to the stack, but only the operator field
                    //System.out.println("\tStacking Operator +");
                    terms.add(new Gate(null, Gate.TYPE.OR, null));
                    break;
                case MULT:
                    //System.out.println("\tStacking Operator *");
                    terms.add(new Gate(null, Gate.TYPE.AND, null));
                    break;
                case NOT:
                    //System.out.println("\tStacking Operator ~");    
                    terms.add(new Gate(null, Gate.TYPE.NOT, null));
                    break;
                
                default: //Regular input, add a Data to the stack.
                    //System.out.println("\tStacking Data " + index);
                    //Remembers the smallest input, and pairs each Data Input with the user's character key
                    if (inputs.containsKey(index)) {
                        //System.out.println("\t\tIt already exists");
                        terms.add(inputs.get(index));
                    }   else { //Only add a mapping for new characters
                        //System.out.println("\t\tNew Data point");
                        if ((int)index < firstInput) {
                            firstInput = (int)index;
                        }
                        CircuitNode temp = new Data(false);
                        inputs.put(index, (Data)temp);
                        terms.add(temp);
                    }
            }
        }

        outputGate = this.nodes[this.nodes.length-1];
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
        CircuitNode term;
        Gate fullElement;
        term = terms.pop(); //Top element on the stack can be a Data or Gate.
        Gate operator = (Gate)terms.pop(); //Second element on the stack MUST be an Operator (partial Gate)

        //A NOT only requires one Data point. AND/OR require two
        if (operator.getOperator() == Gate.TYPE.NOT) {
            fullElement = new Gate(null, operator.getOperator(), term);
        }   else {
            fullElement = new Gate(terms.pop(), operator.getOperator(), term);
        }

        //The fullElement might become an input to future Gates. Either way, store it into the array.
        terms.add(fullElement);
        nodes[equationNum] = fullElement;
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

    public int getNumOfInputs() {
        return inputs.size();
    }

    public String getCircuitName() {
        return circuitName;
    }
    
    public String getOutputFile() {
        return "res/" + circuitName+".txt";
    }

    public Gate getOutputGate() {
        return outputGate;
    }

    public boolean getCircuitOutput() {
        return outputGate.getValue();
    }

    public int circuitEndTime() {
        return outputGate.getDelays().getLastNode().getData()+outputGate.getGateDelay();
    }

    
    /**
     * Must be done before EACH state change check.
     * This is because the queues get used up throughout a check.
     */
    public void initializeGatesForCheck() {
        for (Gate g : nodes) {
            g.initializeInputQueue();
        }
    }

    /**
     * Bubble sort based on the largest of the InputQueue and OutputQueue's size.
     */
    public void sortByQueues() {
        int numOfInputs = nodes.length;

        //Loop through n-1 times
        for (short num = 0; num < numOfInputs-1; num++) {
            //Loop until the rest of the array is sorted
            for (short i = 0; i< numOfInputs-num-1; i++) {
                Gate currentNode = nodes[i];
                Gate nextNode = nodes[i+1];

                if (currentNode.largestQueueSize() > nextNode.largestQueueSize()) {
                    nodes[i] = nodes[i+1];
                    nodes[i+1] = currentNode;
                }
            }
        }
    }
}
package app;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import app.circuitNode.CircuitNode;
import app.circuitNode.Gate;

import app.tree.BTNode;
import app.tree.BTree;

/**
 * Detector
 */
public class Detector {

    private Circuit circuit;
    private BTree tree;

    public Detector(Circuit c) {
        circuit = c;
        tree = new BTree();
    }

    public Detector(File f) {
        circuit = new Circuit(f);
        tree = new BTree();
    }

    public Detector(String s) {
        circuit = new Circuit(s);
        tree = new BTree();
    }

    /**
     * After a lot of trying, we decided queues would be the best way to
     * figure out every possible state change in a way where
     * we never had to preload a state.
     */
    public void testAllStateChanges() {
        //initialize array of queues
        //A 2 input circuit has 4 possibilities. A 3 input circuit has 8
        int inputCount = circuit.getInputs().size();
        int largest = (int)Math.pow(2, inputCount);
        Queue<Integer>[] transitions = new LinkedList[largest];

        //each queue will have numbers from 1-Math.pow(2, n)-1
        for (int i = 0; i < transitions.length; i++) {
            transitions[i] = new LinkedList<Integer>();
            for (int t = 1; t < largest; t++) {
                transitions[i].add(t);
            }
        }

        System.out.println("All the queues:");
        for (Queue<Integer> q : transitions) {
            System.out.println("Queue next: " + q.size());
        }

        //Loop through 2^n*((2^n)-1) = 2^2n - 2^n times
        int max = (int)(Math.pow(2, 2*inputCount)-Math.pow(2, inputCount));
        int count = 0;
        int currentState = 0;
        int nextState = 0;
        while (count < max) {
            System.out.println("\t\t\t\t\t\t\t\t\t\tCount: " + count + ", max: " + max + ", inputCount: " + inputCount);
            System.out.println("\t\t\t\t\t\t\t\t\t\tCurrent State: " + currentState);
            nextState = (currentState + transitions[currentState].remove()) % largest;
            System.out.println("\t\t\t\t\t\t\t\t\t\tNext state: " + nextState);
            testStateChange(toBase2(currentState), toBase2(nextState), tree);
            currentState = nextState;
            count++;
        }

    }

    private int toBase2(int n) {
        return Integer.parseInt(Integer.toBinaryString(n));
    }

    public BTree getTree() {
        return tree;
    }

    /**
     * Will calculate the circuit values at every given time
     * Assumes that the circuit is initialized with values
     * @param startState base 2 number, where each 1 is true and each 0 is false for a specific output (ABCD corresponds to 0000)
     * @param endState same layout as startState, but the change to this is what is observed
     * @param glitchStates a tree representing all the transitions and if they have glitches
     * @return if the current state change resulted in a glitch
     */
    public boolean testStateChange(int startState, int nextState, BTree glitchStates) {
        int time = 0; //The first possible time to take inputs would be 0
        boolean previousValue, outputValue;
        int earliestSwitch, latestSwitch;
        int outputChangeCount = 0;
        previousValue = outputValue = circuit.getCircuitOutput();
        earliestSwitch = latestSwitch = 0;
        
        //Set initial outputValue (from nextState)
        circuit.setInputs(nextState);
        circuit.initializeGatesForCheck();
        circuit.sortByQueues();

        int circuitEndTime = circuit.circuitEndTime();
        //While there's still more in the queue
        while(circuitEndTime >= time) {
            //Update all gates for this time
            for (CircuitNode c : circuit.getNodes()) {
                Gate g = (Gate)c;
                
                if (g.getNextOutputTime() == time) {
                    g.updateInternalAndRemove();
                    System.out.println(" at time " + time);
                }
                if(g.getNextInputTime() == time) {
                    g.takeInputsAndTransfer();
                }
            }
            time += 5;

            //Check for output change
            if (circuit.getCircuitOutput() != outputValue) {
                System.out.println("Change in output!");
                System.out.println(circuit.getOutputGate() + " is " + circuit.getCircuitOutput());
                outputValue = !outputValue;
                outputChangeCount++;
                System.out.println("Change count: " + outputChangeCount);

                //Figure out earliest and latest output switch times
                if (earliestSwitch == 0) {
                    earliestSwitch = time;
                }
                latestSwitch = time;
            }
        }


        BTNode transition;

        //Must be both because if an output oscillates but ends up changing, its okay
        if (previousValue == circuit.getCircuitOutput() && outputChangeCount >= 2) {
            System.out.println("\n\nGlitch! ////////////////////////////////////////////////////////////////////////////\n\n");
            transition = new BTNode(startState, nextState, earliestSwitch, latestSwitch);
        }   else {
            System.out.println("ALL CLEAR, No Glitch!");
            transition = new BTNode(startState, nextState, null);
        }
        
        glitchStates.insert(transition);

        /*
        for (CircuitNode s : circuit.getNodes()) {
            System.out.println(s);
            System.out.println(s.getValue());
        }
        */

        //Lets the calling function know if this test case was a glitch
        return (transition.getGlitch()!=null);
    }
}
package app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public void setCircuit(Circuit c) {
        this.circuit = c;
    }

    public Circuit getCircuit() {
        return this.circuit;
    }

    /**
     * After a lot of trying, we decided queues would be the best way to figure out
     * every possible state change in a way where we never had to preload a state.
     * 
     * @throws IOException
     */
    public void testAllStateChanges() throws IOException {
        // initialize array of queues
        // A 2 input circuit has 4 possibilities. A 3 input circuit has 8 (2^n)
        BufferedWriter writer = null;
        writer = new BufferedWriter(new FileWriter(circuit.getOutputFile(), true));

        int inputCount = circuit.getInputs().size();
        int largest = (int) Math.pow(2, inputCount);
        Queue<Integer>[] transitions = new LinkedList[largest];

        // each queue will have numbers from 1-Math.pow(2, n)-1
        for (int i = 0; i < transitions.length; i++) {
            transitions[i] = new LinkedList<Integer>();
            for (int t = 1; t < largest; t++) {
                transitions[i].add(t);
            }
        }
        // initialize full circuit for all false
        testStateChange(0, 0, null, null);

        // Loop through 2^n*((2^n)-1) = 2^2n - 2^n times
        writer.append("State Changes:\n");
        int max = (int) (Math.pow(2, 2 * inputCount) - Math.pow(2, inputCount));
        int count = 0;
        int currentState = 0;
        int nextState = 0;
        while (count < max) {
            nextState = (currentState + transitions[currentState].remove()) % largest;

            writer.append("Current State: " + currentState + ",\t Next State: " + nextState);

            testStateChange(toBase2(currentState), toBase2(nextState), tree, writer);
            currentState = nextState;
            count++;
        }
        tree.writeGlitchNodes(writer);
        writer.close();
    }

    private int toBase2(int n) {
        return Integer.parseInt(Integer.toBinaryString(n));
    }

    public BTree getTree() {
        return tree;
    }

    /**
     * Will calculate the circuit values at every given time Assumes that the
     * circuit is initialized with values
     * 
     * @param startState   base 2 number, where each 1 is true and each 0 is false
     *                     for a specific output (ABCD corresponds to 0000)
     * @param endState     same layout as startState, but the change to this is what
     *                     is observed
     * @param glitchStates a tree representing all the transitions and if they have
     *                     glitches
     * @return if the current state change resulted in a glitch
     * @throws IOException
     */
    public boolean testStateChange(int startState, int nextState, BTree glitchStates, BufferedWriter writer)
            throws IOException {
        int time = 0; // The first possible time to take inputs would be 0
        boolean previousValue, outputValue;
        int earliestSwitch, latestSwitch;
        int outputChangeCount = 0;
        previousValue = outputValue = circuit.getCircuitOutput();
        earliestSwitch = latestSwitch = 0;

        // Set initial outputValue (from nextState)
        circuit.setInputs(nextState);
        circuit.initializeGatesForCheck();
        circuit.sortByQueues();

        int circuitEndTime = circuit.circuitEndTime();
        // While there's still more in the queue
        while (circuitEndTime >= time) {
            // Update all gates for this time
            for (CircuitNode c : circuit.getNodes()) {
                Gate g = (Gate) c;

                if (g.getNextOutputTime() == time) {
                    g.updateInternalAndRemove();
                    // System.out.println(" at time " + time);
                }
                if (g.getNextInputTime() == time) {
                    g.takeInputsAndTransfer();
                }
            }
            time += 5;

            // Check for output change
            if (circuit.getCircuitOutput() != outputValue) {
                outputChangeCount++;
                outputValue = !outputValue;

                // Figure out earliest and latest output switch times
                if (earliestSwitch == 0) {
                    earliestSwitch = time;
                }
                latestSwitch = time;
            }
        }

        if (writer != null) {
            writer.append("\t\toutput changes: " + outputChangeCount + "\n");
        }

        BTNode transition;
        // Must be both because if an output oscillates but ends up changing, its okay
        if (previousValue == circuit.getCircuitOutput() && outputChangeCount >= 2) {
            if (writer != null) {
                writer.append("\tGlitch from " + startState + " to " + nextState + "\n");
            }
            transition = new BTNode(startState, nextState, earliestSwitch, latestSwitch);
        } else {
            // System.out.println("ALL CLEAR, No Glitch!");
            transition = new BTNode(startState, nextState, null);
        }

        if (glitchStates != null) {
            glitchStates.insert(transition);
        }

        // Lets the calling function know if this test case was a glitch
        return (transition.getGlitch() != null);
    }
}
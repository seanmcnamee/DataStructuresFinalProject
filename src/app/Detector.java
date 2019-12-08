package app;

import java.io.File;

import app.circuitNode.CircuitNode;
import app.circuitNode.Gate;

import app.tree.BTNode;
import app.tree.BTree;

/**
 * Detector
 */
public class Detector {

    private Circuit circuit;

    public Detector(Circuit c) {
        circuit = c;
    }

    public Detector(File f) {
        circuit = new Circuit(f);
    }

    public Detector(String s) {
        circuit = new Circuit(s);
    }


    /**
     * Will calculate the circuit values at every given time
     * Assumes that the circuit is initialized with values
     * @param startState
     * @param endState
     * @param glitchStates
     * @return
     */
    public  boolean testStateChange(int startState, int nextState, BTree glitchStates) {
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
        if (previousValue == circuit.getCircuitOutput() && outputChangeCount > 0) {
            System.out.println("Glitch! //////////////////////////////////////////");
            transition = new BTNode(startState, nextState, earliestSwitch, latestSwitch);
        }   else {
            System.out.println("ALL CLEAR, No Glitch! //////////////////////////////////////////");
            transition = new BTNode(startState, nextState, null);
        }
        
        glitchStates.insert(transition);

        for (CircuitNode s : circuit.getNodes()) {
            System.out.println(s);
            System.out.println(s.getValue());
        }

        return false;
    }
}
package app;

import java.io.File;

import app.tree.BTree;

/**
 * Detector
 */
public class Detector {

    final int MAX_TIME = 999; //TODO figure out a good max timing
    private Circuit circuit;
    //TODO Add The circuit data structure here and in the constructor

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
    public  boolean testStates(int startState, int nextState, BTree glitchStates) {
        int time = 0;
        boolean outputValue = circuit.getCircuitOutput();
        int outputChangeCount = 0;
        int stableTimeCounter = 0;





        //Set initial outputValue (from nextState)
        circuit.setInputs(nextState);


        //Loop through notable moments for the circuit, recalculating the output for each
            //Keep track of output changes and how long the output is stable for

        


        return false;
    }
}
package app;

import app.tree.BTree;

/**
 * Detector
 */
public class Detector {

    final int MAX_TIME = 999; //TODO figure out a good max timing
    //TODO Add The circuit data structure here and in the constructor

    public Detector() {

    }


    //Will calculate the circuit values at every given time, 
    public  boolean testStates(int startState, int endState, BTree glitchStates) {
        int time = 0;
        int outputValue = 0;
        int outputChangeCount = 0;
        int stableTimeCounter = 0;

        //Set initial outputValue (from StartState)


        //Loop through notable moments for the circuit, recalculating the output for each
            //Keep track of output changes and how long the output is stable for

        


        return false;
    }
}
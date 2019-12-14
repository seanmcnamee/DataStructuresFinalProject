package app;

import java.io.IOException;
import java.util.Scanner;

import app.tree.BTNode;

/**
 * 
 */
public class Menu {
    private Detector detector;
    private boolean continueLoop = true;
    private final int EXIT = -1;

    public Menu(Detector d) {
        this.detector = d;
    }

    public void loopForInput() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("---Circuit Glitch Finder---");
        System.out.println("\tDefault circuit added...");
        while (continueLoop) {
            listOptions();
            int selection = loopUntilValid(scan, 1, 7);
            switch(selection) {
                case 1://Change Circuit
                    explainCircuit();
                    System.out.print("Your circuit equation: ");
                    String equation = scan.nextLine();
                    detector.setCircuit(new Circuit(equation));
                    break;
                case 2://Test single State Change
                case 7://Search for single State Change
                    //input
                    explainStateChange();
                    System.out.print("Starting State: ");
                    String strStartState = scan.nextLine();
                    System.out.print("Next State: ");
                    String strNextState = scan.nextLine();
                    int startState = Integer.parseInt(strStartState);
                    int nextState = Integer.parseInt(strNextState);

                    if (selection == 2) {
                        //output/calculation
                        detector.testStateChange(startState, startState, null, null); //Load initial state
                        System.out.println("\tStarting output: " + detector.getCircuit().getCircuitOutput());
                        boolean isGlitch = detector.testStateChange(startState, nextState, detector.getTree(), null);
                        System.out.println("\tEnding output: " + detector.getCircuit().getCircuitOutput());
                        System.out.println("\tGlitch: " + isGlitch);
                    }   else { //7
                        BTNode node = detector.getTree().search(startState, nextState);
                        if (node != null) {
                            System.out.println("\tNode from " + strStartState + " to " + strNextState + ":");
                            if (node.getGlitch() != null) {
                                System.out.println("\t\tGlitch from t=" + node.getGlitch().getStart() + " to " + node.getGlitch().getEnd());
                            }   else {
                                System.out.println("\t\tNo Glitch");
                            }
                        }   else {
                            System.out.println("\tState change not found");
                        }
                    }
                    break;
                case 3://Test all possible State Changes
                    System.out.println("\tAll information should now be in the " + detector.getCircuit().getCircuitName() + " file...");
                    detector.testAllStateChanges();
                    break;  
                case 4://Count all tested State changes
                    System.out.println("\tNumber of State Changes: " + detector.getTree().countNodes());
                    break;
                case 5://Print all tested State changes
                    listPrintOrders();
                    int printType = loopUntilValid(scan, 1, 7);
                    System.out.println("Ignoring left side 0's");
                    System.out.print("Displaying ");
                    switch(printType) {
                        case 1://Pre-Order
                            System.out.println("Pre-Order:");
                            detector.getTree().preorder();
                            break;
                        case 2://In-Order
                            System.out.println("In-Order:");
                            detector.getTree().inorder();
                            break;
                        case 3://Post-Order
                            System.out.println("Post-Order:");
                            detector.getTree().postorder();
                            break;
                        default:
                            System.out.println("\tNone selected...");
                            break;
                    }
                    break;
                case 6://Disply and Count all tested glitch State changes
                    System.out.println("\tGlitches:");
                    int count = detector.getTree().countGlitchNodes();
                    System.out.println("Total: " + count);
                    break;
                default://Exit
                    this.continueLoop = false;
                    break;
            }
        }
        System.out.println("Thank you for using the Circuit Glitch Finder");
    }

    //Error trap for a valid number input
    private int loopUntilValid(Scanner scan, int lowValid, int highValid) {
        int ans = 0;
        do {
            String userAns = scan.nextLine();
            ans = Integer.parseInt(userAns);
        } while ( !(ans == EXIT || (ans >= lowValid && ans <= highValid)) );
        System.out.println();
        return ans;
    }

    private void listOptions() {
        System.out.println("Current Circuit: " + detector.getCircuit().getCircuitName());
        System.out.println("\nOptions:");
        System.out.println("\t-1. Exit");
        System.out.println("\t 1. Change Circuit");
        System.out.println("\t 2. Test single State Change");
        System.out.println("\t 3. Test all possible State Changes");
        System.out.println("\t 4. Count all tested State changes");
        System.out.println("\t 5. Print all tested State changes");
        System.out.println("\t 6. Display and Count all tested glitch State changes");
        System.out.println("\t 7. Search for a tested State Change");
    }

    private void explainCircuit() {
        System.out.println("\tMake sure you format the circuit to where each gate is fully surrounded by parenthasis");
        System.out.println("\tA+B is bad, while (A+B) is good. ~A is bad, while (~A) is good. You don't need spaces.");
        System.out.println("\tEach Gate can have a maximum of two inputs, however you can use the same input for multiple gates.");
        System.out.println("\tYour possible operators are...");
        System.out.println("\t\tNot: ~");
        System.out.println("\t\tAND: *");
        System.out.println("\t\tOR: +");
        
    }

    private void explainStateChange() {
        System.out.println("\tFor a single state change, we need the starting and ending state.");
        System.out.println("\tThey each have to be a N-bit String, with each bit corresponding to an input's value");
        System.out.println("\t1 is true, and 0 is false. The bit will correposnd to the inputs in alphabetical order.");
    }

    private void listPrintOrders() {
        System.out.println("\t1. Pre-Order");
        System.out.println("\t2. In-Order");
        System.out.println("\t3. Post-Order");
    }
}
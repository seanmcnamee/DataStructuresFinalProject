package app;

import java.util.Scanner;

import app.circuitNode.Data;
import app.circuitNode.Gate;
import app.linkedList.MyLinkedList;
import app.linkedList.LinkedListNode;
import app.circuitNode.CircuitNode;
import app.tree.BTree;

public class Runner {
    public static void main(String[] args) {

        /*
        //Num of elements
        int elements = 8, maxNum = 30;
        LinkedList[] list = new LinkedList[elements];

        for (int i = 0; i < elements; i++) {
            list[i] = new LinkedList();
            list[i].addToBack(new LinkedListNode((int)(Math.random()*maxNum)));
            list[i].printList();
            //list.addToBack(new LinkedListNode((int)(Math.random()*30)));
        }

        int power = 0;
        while ((elements/(Math.pow(2, power))) >= 2) {
            //System.out.println("From 0-" + (elements/(Math.pow(2, power))));
            for (int i = 0; i < elements/(Math.pow(2, power)); i+=2) {
                list[i/2] = LinkedList.sortWithOther(list[i], list[i+1]);
                list[i/2].printList();
            }
            power++;
        }
        */

        //list[0].printList();

        
        String test = "((A+B)+(B+C))";//"(((A*B)*C)+(D+E))";//"((A+B)*(A+C))";//

        Circuit c = new Circuit(test);
        System.out.println("Data inputs:");
        for (Character key : c.getInputs().keySet()) {
            System.out.println(key + " - " + c.getInputs().get(key));
        }

        System.out.println("\nCircuit Pieces: ");

        for (CircuitNode s : c.getNodes()) {
            System.out.println("Memeory Loc: " + s);
            if (s.getClass() == Data.class) {
                System.out.println("A regular data point for some reason");
                System.out.println(c.findKey(s));
            }   else if (s.getClass() == Gate.class) {
                System.out.println("Gate:");
                System.out.println("\t"+((Gate)s).getGateString(c));
                ((Gate)s).getDelays().printList();
            }
            
        }   

        for (Character key : c.getInputs().keySet()) {
            System.out.println(key + " - " + c.getInputs().get(key).getValue());
        }

        Detector d = new Detector(c);
        d.testAllStateChanges();
        System.out.println("Tree nodes: " + d.getTree().countNodes());
        System.out.println("Glitch nodes: " + d.getTree().countGlitchNodes());

        /*
        System.out.println("Output Gate: " + c.getOutputGate());

        Detector d = new Detector(c);
        BTree tree = new BTree();

        
        System.out.println("Output: " + c.getCircuitOutput() + ", Nodes: " + tree.countNodes());
        System.out.println("Glitches: " + tree.countGlitchNodes());


        //1
        d.testStateChange(00000, 11111, tree);
        System.out.println("...");
        for (Character key : c.getInputs().keySet()) {
            System.out.println(key + " - " + c.getInputs().get(key).getValue());
        }
        System.out.println("Output: " + c.getCircuitOutput() + ", Nodes: " + tree.countNodes());
        System.out.println("Glitches: " + tree.countGlitchNodes());


        //2
        d.testStateChange(11111, 10101, tree);
        System.out.println("...");
        for (Character key : c.getInputs().keySet()) {
            System.out.println(key + " - " + c.getInputs().get(key).getValue());
        }
        System.out.println("Output: " + c.getCircuitOutput() + ", Nodes: " + tree.countNodes());
        System.out.println("Glitches: " + tree.countGlitchNodes());

        //3
        d.testStateChange(10101, 00001, tree);
        System.out.println("...");
        for (Character key : c.getInputs().keySet()) {
            System.out.println(key + " - " + c.getInputs().get(key).getValue());
        }
        System.out.println("Output: " + c.getCircuitOutput() + ", Nodes: " + tree.countNodes());
        System.out.println("Glitches: " + tree.countGlitchNodes());

        */

        //byte[] test = {0, 0, 0, 0, 1};
        //System.out.println(test[4]);

        //Load in and create a circuit.


        /*

        Scanner scan = new Scanner(System.in);
        // Creating object of BT
        BTree bt = new BTree();
        // Perform tree operations
        System.out.println("Binary Tree Test\n");
        char ch;
        do {
            System.out.println("\nBinary Tree Operations\n");
            System.out.println("1. insert ");
            System.out.println("2. search");
            System.out.println("3. count nodes");
            System.out.println("4. check empty");
            int choice = scan.nextInt();
            switch (choice) {
            case 1:
                System.out.println("Enter integer element to insert");
                bt.insert(scan.nextInt(), scan.nextInt(), null);
                break;
            case 2:
                System.out.println("Enter integer element to search");
                System.out.println("Search result : " + bt.search(scan.nextInt(), scan.nextInt()));
                break;
            case 3:
                System.out.println("Nodes = " + bt.countNodes());
                break;
            case 4:
                System.out.println("Empty status = " + bt.isEmpty());
                break;
            default:
                System.out.println("Wrong Entry \n ");
                break;
            }
            // Display tree
            System.out.print("\nPost order : ");
            bt.postorder();
            System.out.print("\nPre order : ");
            bt.preorder();
            System.out.print("\nIn order : ");
            bt.inorder();
            System.out.println("\n\nDo you want to continue (Type y or n) \n");
            ch = scan.next().charAt(0);
        } while (ch == 'Y' || ch == 'y');

        scan.close();
        */
    }
}

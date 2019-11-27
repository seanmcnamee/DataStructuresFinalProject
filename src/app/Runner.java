package app;

import java.util.Scanner;

import app.circuitNode.Data;
import app.circuitNode.Gate;
import app.linkedList.LinkedList;
import app.linkedList.LinkedListNode;
import app.circuitNode.CircuitNode;
import app.tree.BTree;

public class Runner {
    public static void main(String[] args) {

        LinkedList list = new LinkedList();

        for (int i = 0; i < 10; i++) {
            list.addToBack(new LinkedListNode((int)(Math.random()*30)));
        }

        System.out.println("Unsorted:");
        list.printList();

        list.sort();

        System.out.println("Sorted:");
        list.printList();

        /*
        String test = "(((A*B)*C)+(D+E))";//"((A+B)*(A+C))";//

        Circuit c = new Circuit(test);
        System.out.println("Data inputs:");
        for (Character key : c.getInputs().keySet()) {
            System.out.println(key + " - " + c.getInputs().get(key));
        }

        System.out.println("\nCircuit Pieces: ");

        for (CircuitNode s : c.getNodes()) {
            if (s.getClass() == Data.class) {
                System.out.println("A regular data point for some reason");
                System.out.println(c.findKey(s));
            }   else if (s.getClass() == Gate.class) {
                System.out.println("Gate:");
                System.out.println("\t"+((Gate)s).getGateString(c));
            }
        }   


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

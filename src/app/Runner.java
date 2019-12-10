package app;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import app.circuitNode.Data;
import app.circuitNode.Gate;
import app.linkedList.MyLinkedList;
import app.linkedList.LinkedListNode;
import app.circuitNode.CircuitNode;
import app.tree.BTree;

public class Runner {
    public static void main(String[] args) throws IOException {

        File circuitFile = new File("circuitFile.txt");
        Circuit c = new Circuit(circuitFile);
        Detector d = new Detector(c);

        Menu menu = new Menu(d);
        menu.loopForInput();
        



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

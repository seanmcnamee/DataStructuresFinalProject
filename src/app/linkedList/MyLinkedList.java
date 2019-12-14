package app.linkedList;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * LinkedList
 */
public class MyLinkedList {
    private LinkedListNode root;

    public MyLinkedList() {
        this(null);
    }

    public MyLinkedList(LinkedListNode root) {
        this.root = root;
    }

    public LinkedListNode getRoot() {
        return root;
    }

    public LinkedListNode getLastNode() {
        LinkedListNode trav = root;
        if (trav == null) {
            return null;
        }

        while (trav.getNext() != null) {
            trav = trav.getNext();
        }
        return trav;
    }

    /**
     * Used to have a gate's delay add on to each of it's input's delays.
     */
    public void addToEach(int value) {
        LinkedListNode trav = root;
        if (trav != null) {
            while (trav != null) {
                trav.setData(trav.getData() + value);
                trav = trav.getNext();
            }
        }
    }

    /**
     * Used in accociation with the calculation of gate delays
     */
    public void RemoveDuplicates() {
        LinkedListNode trav = root;
        if (trav != null) {
            while (trav.getNext() != null) {
                if (trav.getData() == trav.getNext().getData()) {
                    trav.setNext(trav.getNext().getNext());
                    continue;
                }
                trav = trav.getNext();
            }
        }
    }

    public void printList(BufferedWriter writer) throws IOException {
        LinkedListNode trav = root;
        if(trav != null) {
            writer.append("\tInput Delays: ");
            while (trav!= null) {
                writer.append(trav.getData() + " ");
                trav = trav.getNext();
            }
        }   else {
            writer.append("\tEmpty List...");
        }
        writer.append("\n");
    }

    /**
     * Uses a version of merge sort to completely sort two linked lists together
     * @param first doesn't matter which linked List
     * @param other The other linekd list
     * @return A fully sorted array.
     */
    public static MyLinkedList sortWithOtherAdded(MyLinkedList first, int firstDelay, MyLinkedList other, int otherDelay) {
        MyLinkedList newList = new MyLinkedList();
        LinkedListNode t1 = first.root;
        LinkedListNode t2 = other.root;

        //Pick the smaller of each list until one is empty
        while (t1 != null && t2 != null) {
            if (t1.getData()+firstDelay > t2.getData()+otherDelay) {
                newList.addToBack(new LinkedListNode(t2.getData()+otherDelay));
                t2 = t2.getNext();
            }   else {
                newList.addToBack(new LinkedListNode(t1.getData()+firstDelay));
                t1 = t1.getNext();
            }
        }

        //Then add all the elements on (can't just tack on one because changes to one linkedlist will affect another)
        if (t2 != null) {
            t1 = t2;
            firstDelay = otherDelay;
        }
        while (t1 != null) {
            newList.addToBack(new LinkedListNode(t1.getData()+firstDelay));
            t1 = t1.getNext();
        }
        return newList;
    }

    private void addToBack(LinkedListNode newNode) {
        if (this.root == null) {
            this.root = newNode;
        } else {
            getLastNode().setNext(newNode);
        }
    }
}
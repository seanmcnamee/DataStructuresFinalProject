package app.linkedList;

/**
 * LinkedList
 */
public class LinkedList {
    private LinkedListNode root;

    public LinkedList() {
        this(null);
    }

    public LinkedList(LinkedListNode root) {
        this.root = root;
    }

    public LinkedListNode getLastNode() {
        LinkedListNode trav = root;
        if(trav == null) {
            return null;
        }

        while (trav.getNext() != null) {
            trav = trav.getNext();
        }
        return trav;
    }

    public void addToFront(LinkedList otherList) {
        if (otherList.root != null) {
            otherList.getLastNode().setNext(this.root);
            this.root = otherList.root;
        }
    }

    public void addToFront(LinkedListNode newNode) {
        if (newNode != null) {
            newNode.setNext(this.root);
            this.root = newNode;
        }
    }

    public void addToBack(LinkedList otherList) {
        if (this.root == null) {
            this.root = otherList.root;
        }   else {
            getLastNode().setNext(otherList.root);
        }
    }


    public void addToBack(LinkedListNode newNode) {
        if (this.root == null) {
            this.root = newNode;
        }   else {
            getLastNode().setNext(newNode);
        }
    }

    public swapNextTwo(LinkedList)

    //Bubble sort this LinkedList
    public void sort() {
        
    }
}
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

    //Bubble sort this LinkedList
    public void sort() {
        LinkedListNode previous, current, next;
        previous = null;
        current = root;
        if (current != null)    {
            next = root.getNext();
        }   else {
            next = null;
        }

        //Loop through all checks
        while (next != null) {
            System.out.println("Current nodes: " + ((previous != null)? previous.getData(): "NaN") + ", " + ((current != null)? current.getData(): "NaN") + ", " + ((next != null)? next.getData(): "NaN"));
            //Compare values
            if (current.getData() < next.getData()) {
                //Swap
                current.setNext(next.getNext());
                next.setNext(current);
                //Watch out for null previous
                if (previous == null) {
                    root = next;
                }   else {
                    previous = next;
                }
            }
            System.out.println("Current nodes: " + ((previous != null)? previous.getData(): "NaN") + ", " + ((current != null)? current.getData(): "NaN") + ", " + ((next != null)? next.getData(): "NaN"));
            previous = current;
            current = previous.getNext();
            next = previous.getNext().getNext();

        }
    }

    public void printList() {
        LinkedListNode trav = root;
        if(trav != null) {
            while (trav.getNext() != null) {
                System.out.print(trav.getData() + " ");
                trav = trav.getNext();
            }
        }
        System.out.println();
    }

}
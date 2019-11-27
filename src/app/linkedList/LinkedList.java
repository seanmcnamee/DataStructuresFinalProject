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

    /**
     * Used to have a gate's delay add on to each of it's input's delays.
     */
    public void addToEach(int value) {
        LinkedListNode trav = root;
        if(trav != null) {
            while (trav != null) {
                trav.setData(trav.getData()+value);
                trav = trav.getNext();
            }
        }
    }

    /**
     * Used in accociation with the calculation of gate delays
     */
    public void RemoveDuplicates() {
        LinkedListNode trav = root;
        if(trav != null) {
            while (trav.getNext() != null) {
                if (trav.getData()==trav.getNext().getData()) {
                    trav.setNext(trav.getNext().getNext());
                    continue;
                }
                trav = trav.getNext();
            }
        }
    }

    /**
     * Used mostly for console logging of the entire linked list
     */
    public void printList() {
        LinkedListNode trav = root;
        if(trav != null) {
            while (trav!= null) {
                System.out.print(trav.getData() + " ");
                trav = trav.getNext();
            }
        }   else {
            System.out.print("Empty");
        }
        System.out.println();
    }

    /**
     * Uses a version of merge sort to completely sort two linked lists together
     * @param first doesn't matter which linked List
     * @param other The other linekd list
     * @return A fully sorted array.
     */
    public static LinkedList sortWithOther(LinkedList first, LinkedList other) {
        LinkedList newList = new LinkedList();
        LinkedListNode t1 = first.root;
        LinkedListNode t2 = other.root;

        //Pick the smaller of each list until one is empty
        while (t1 != null && t2 != null) {
            if (t1.getData() > t2.getData()) {
                newList.addToBack(new LinkedListNode(t2.getData()));
                t2 = t2.getNext();
            }   else {
                newList.addToBack(new LinkedListNode(t1.getData()));
                t1 = t1.getNext();
            }
        }

        //Then add all the elements on (can't just tack on one because changes to one will affect another)
        if (t2 != null) {
            t1 = t2;
        }
        while (t1 != null) {
            newList.addToBack(new LinkedListNode(t1.getData()));
            t1 = t1.getNext();
        }
        return newList;
    }
}
package app.linkedList;

/**
 * LinkedListNode
 */
public class LinkedListNode {

    private int data;
    private LinkedListNode next;

    public LinkedListNode(int data, LinkedListNode next) {
        this.data = data;
        this.next = next;
    }

    public LinkedListNode() {
        this(-1, null);
    }

    public LinkedListNode(int data) {
        this(data, null);
    }

    public void setNext(LinkedListNode next) {
        this.next = next;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getData() {
        return this.data;
    }

    public LinkedListNode getNext() {
        return this.next;
    }
}
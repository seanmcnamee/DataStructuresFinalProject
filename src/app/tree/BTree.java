package app.tree;

import java.io.BufferedWriter;
import java.io.IOException;

/* Class BT */
public class BTree {
    private BTNode root;

    /* Constructor */
    public BTree() {
        root = null;
    }

    /* Function to check if tree is empty */
    public boolean isEmpty() {
        return root == null;
    }

    /* Functions to insert data */
    public void insert(BTNode nodeToInsert) {
        root = orderedInsert(root, nodeToInsert);
    }

    /* Function to insert data recursively */
    // TODO FIX this garbage
    /*
    private BTNode insert(BTNode node, BTNode toInsert) {
        if (node == null)
            node = toInsert;
        else if (node.getRight() == null) {
            node.right = insert(node.right, toInsert);
        } else {
            node.left = insert(node.left, toInsert);
        }
        return node;
    }
    */
    

    //Returns 
    private BTNode orderedInsert(BTNode node, BTNode toInsert) {
        if (node==null) {
            node = toInsert;
        }   else if(larger(toInsert, node)) { //Must go to the right
            node.right = orderedInsert(node.right, toInsert);
        }   else {
            node.left = orderedInsert(node.left, toInsert);
        }
        return node;
    }

    private boolean larger(BTNode node1, BTNode node2) {
        return node1.getStartValues()>node2.getStartValues() || (node1.getStartValues()==node2.getStartValues()&&node1.getEndValues()>node2.getEndValues());
    }

    /* Function to count number of nodes */
    public int countNodes() {
        return countNodes(root);
    }

    /* Function to count number of nodes recursively */
    private int countNodes(BTNode r) {
        if (r == null)
            return 0;
        else {
            int count = 1;
            count += countNodes(r.getLeft());
            count += countNodes(r.getRight());
            return count;
        }
    }

    public void writeGlitchNodes(BufferedWriter writer) throws IOException {
        writer.append("\nGlitches:\n");
        int count = countGlitchNodes(root, writer);
        writer.append("\tTotal: " + count+"\n");
    }

    public int countGlitchNodes() throws IOException {
        System.out.println("Counting Glitches......");
        return countGlitchNodes(root, null);
    }

    /**
     * Count in a preorder fashion
     * @param r
     * @param writer
     * @return
     * @throws IOException
     */
    private int countGlitchNodes(BTNode r, BufferedWriter writer) throws IOException {
        if (r == null) {
            return 0;
        }   else {
            int count = 0;
            //System.out.println("\t\t\t\tNode from " + r.getStartValues() + " to " + r.getEndValues());
            if (r.getGlitch() != null) {
                String output = "\t\tGlitch in " + r.getStartValues() + " to " + r.getEndValues() + " from " + r.getGlitch().getStart() + " to " + r.getGlitch().getEnd();
                if (writer!= null) {
                    writer.write(output+"\n");
                }   else {
                    System.out.println(output);
                }
                count = 1;
            }
            count += countGlitchNodes(r.getLeft(), writer);
            count += countGlitchNodes(r.getRight(), writer);
            return count;
        }
    }

    /* Function to search for an element */
    public BTNode search(int valStart, int valEnd) {
        return search(root, valStart, valEnd);
    }

    /* Function to search for an element recursively */
    private BTNode search(BTNode r, int start, int end) {
        BTNode node;
        if (r==null) {
            return null;
        }
        //System.out.println("This node is from " + );
        if (r.getStartValues() == start && r.getEndValues() == end)
            return r;
        if (r.getLeft() != null) {
            node = search(r.getLeft(), start, end);
            if (node!= null) {
                return node;
            }
        }
        if (r.getRight() != null) {
            node = search(r.getRight(), start, end);
            if (node != null) {
                return node;
            }
        }
        return null;
    }

    /* Function for inorder traversal */
    public void inorder() {
        inorder(root);
    }

    private void inorder(BTNode r) {
        if (r != null) {
            inorder(r.getLeft());
            System.out.println(r.getStartValues() + "-" + r.getEndValues());
            inorder(r.getRight());
        }
    }

    /* Function for preorder traversal */
    public void preorder() {
        preorder(root);
    }

    private void preorder(BTNode r) {
        if (r != null) {
            System.out.println(r.getStartValues() + "-" + r.getEndValues());
            preorder(r.getLeft());
            preorder(r.getRight());
        }
    }

    /* Function for postorder traversal */
    public void postorder() {
        postorder(root);
    }

    private void postorder(BTNode r) {
        if (r != null) {
            postorder(r.getLeft());
            postorder(r.getRight());
            System.out.println(r.getStartValues() + "-" + r.getEndValues());
        }
    }
}
/* Class BinaryTree */
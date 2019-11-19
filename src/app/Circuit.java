package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Circuit
 */
public class Circuit {

    public static void main(String[] args) {
        String test = "((A+B)*(A+C))";//"(((A*B)*C)+(D+E))";

        Circuit c = new Circuit(test);
        for (String s : c.connections) {
            System.out.println(s);
        }   
    }

    final char START = '(', END = ')', ADD = '+', MULT = '*', NOT = '~';
    String[] connections;


    // Take a file to produce circuit
    public Circuit(File path) {
        String equation = "";
        Scanner input;
        try {
            input = new Scanner(path);
            equation = input.next();
            equationToCircuit(equation);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("BAD");
        }
    }

    public Circuit(String equation) {
        equationToCircuit(equation);
    }

    public void equationToCircuit(String equation) {
        int equationNum = 0;
        Stack<Integer> parenthasis = new Stack<Integer>();
        Stack<String> terms = new Stack<String>();
        this.connections = new String[countOperators(equation)];
        

        //Yes some of these cases are redundant. The goal is to change them from being String to actual objects.
        //TODO make this work for NOT then AND then OR (otherwise just keep parenthasis)
        for (int i = 0; i < equation.length(); i++) {
            char index = equation.charAt(i);
            switch(index) {
                case START:
                    parenthasis.add(i);
                    break;
                case END:
                    //Create new term
                    addElement(terms, connections, equationNum);
                    equationNum++;
                    break;
                case ADD | MULT | NOT:
                    terms.add(index+"");
                    break;
                default:
                    terms.add(index+"");
            }
        }

        //addElement(terms, connections, equationNum);

    }
    
    private int countOperators(String equation) {
        int count = 0;
        for (int i = 0; i < equation.length(); i++) {
            char index = equation.charAt(i);
            if (index == ADD || index == MULT || index == NOT) {
                count++;
            }
        }
        return count;
    }

    private void addElement(Stack<String> terms, String[] connections, int equationNum) {
        String term = terms.pop();
        char operator = terms.pop().charAt(0);
        if (operator != NOT) {
            term = terms.pop() + operator + term;
        }
        terms.add("n" + equationNum);
        connections[equationNum] = ("n" + equationNum + ": " + term);
    }

    
}
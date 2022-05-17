package proj2;


// Dylan A. Cedres Rivera, 801-16-1452
// CCOM 4029-002, Semester 2 (January 2022)
// Prof. Patricia Ordonez

// MyStack.java
// Purpose: to use as a stack for Player 1 and Player 2's playing hands in
// Rummy, the card game of the century!

import java.util.*;
import java.io.*;


public class MyStack {

    Stack<Card> stack;

    // Constructor
    public MyStack() {
        stack = new Stack<Card>();
    }

    // Push to add a Card to MyStack
    public void pushMyStack(Card card) {
        stack.push(card);
    }

    // Pop to remove a Card from MyStack
    public void popMyStack() {
        System.out.println( (Card) stack.pop());
    }

    // Peek to see the first Card in MyStack
    public void peekMyStack() {
        System.out.println( (Card) stack.peek());
    }

    // Search to look if there's an specific Card in MyStack
    public boolean searchMyStack(Card card) {
        if (stack.search(card) != -1)
            return true;

        return false;
    }

    // Is Empty to check if MyStack has Cards 
    public boolean isMyStackEmpty() {
        return stack.isEmpty();
    }
}

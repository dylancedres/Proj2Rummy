Dylan A. Cedrés Rivera
801-16-1452
CCOM 4029 002 High Level Programming Languages
Prof. Patricia Ordonez
University of Puerto Rico, Rio Piedras Campus
Second Semester (January - May 2022)

README.txt file for Project 2: "Rummy, the Card Game of the Century!"


Extra Credit:
    (source file: Card.java/)

    - Used a different container of card images called "myCards/" that changed the 
    appearance of all the cards, taken from https://opengameart.org/content/playing-cards-vector-png .
    Also, changed the card Deck image to the logo of the Department of Computer Science 
    of our University of Puerto Rico, Rio Piedras Campus, taken from the 
    Department's Facebook profile picture.


    - The game now starts with the first card from the Deck added to the Stack, instead of the game
     just starting with an empty Stack.



Implemented Classes: 
    (source file MyStack.java/)

    - public class MyStack{...}
        Creates a stack to use for hand operations.
        Based on the normal operations of a stack data structure (push, pop, peek, isEmpty, search).



Implemented Functions and Methods: 
    (source file Table.java/)

    - void layCard(Card card){...}
        Lays a card in one of the available spaces for sets or runs.
        Only modified some of the output string .

    
    - public String showHand(DefaultListModel playerHand) {...}
        Prints a Player's hand on the terminal


    - public void showAddedCard(Card card) {...}
        Prints the card from the DECK that was just added to the Player's hand


    - public void showDiscardedCard(Card card) {..}
	    Prints the card from the Player's hand that was just discarded to the STACK Pile


    - public void sortPlayerHand(DefaultListModel playerHand) {...}
        Sorts the Player's hand


    - public void showPlayersPoints(DefaultListModel player1Hand, DefaultListModel player2Hand) {...}
        Displays the points of the Players' Hands when the game is over 
        and displays which Player won the game


    - public void endGameOnEmptyHand(DefaultListModel player1Hand, DefaultListModel player2Hand) {...}
        Ends game if a Player's hand is empty (EMPTY HAND) and displays who won


    - public void endGameOnEmptyDeck(DefaultListModel player1Hand, DefaultListModel player2Hand) {...}
        Ends game if the Deck runs out of cards to play with (EMPTY DECK) and displays who won



Sources:
    
    - https://opengameart.org/content/playing-cards-vector-png

        Used for new French gaming card images to implement 
        to the window of the actual Rummy game.


    - https://docs.oracle.com/javase/tutorial/java/package/createpkgs.html

        Helped to understand how package creation and execution works
        and how to create them myself for this particular project.


    - https://www.geeksforgeeks.org/stack-class-in-java/

        Helped to understand how to create Stack objets in Java, how to 
        interact with them, and how to incorporate them in the Project at hand.


    - https://docs.oracle.com/javase/7/docs/api/javax/swing/DefaultListModel.html#isEmpty()

        Helped to understand how Default List Models work and
        how they interact with other objects in Java.
        Also, it helped to see them as a bridge between
        data for the terminal and data for the window program.

/**END***/
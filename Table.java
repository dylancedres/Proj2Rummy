package proj2;


// Dylan A. Cedres Rivera, 801-16-1452
// CCOM 4029-002, Semester 2 (January 2022)
// Prof. Patricia Ordonez
// Project 2 Rummy Card Game

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
/**
*	This GUI assumes that you are using a 52 card deck and that you have 13 sets in the deck.
*	The GUI is simulating a playing table
	author Patti Ordonez
*/

public class Table extends JFrame implements ActionListener
{

	// Deals four cards to each Player. Maximum number of cards on the Table is 10
	final static int numDealtCards = 4;

	/**
	* Boolean variables to check which Player's turn it is,
	* if a card was picked from the Deck or the Stack,
	* and if a card was laid on the Stack or the Table to make melds. 
	* Player 1 starts the turn, so the default is that
	* Player 1's turn is true, while Player 2's turn is false.
	*/
	private boolean p1Turn = true;
	private boolean p2Turn = false;
	private boolean p1Picked = false;
	private boolean p2Picked = false;
	private boolean p1Laid = true;
	private boolean p2Laid = true;


	JPanel player1;
	JPanel player2;
	JPanel deckPiles;
	JLabel deck;
	JLabel stack;
	JList p1HandPile;
	JList p2HandPile;
	Deck cardDeck;
	Deck stackDeck;

	SetPanel [] setPanels = new SetPanel[13];
	JLabel topOfStack;
	JLabel deckPile;
	JButton p1Stack;
	JButton p2Stack;

	JButton p1Deck;
	JButton p2Deck;

	JButton p1Lay;
	JButton p2Lay;

	JButton p1LayOnStack;
	JButton p2LayOnStack;

	DefaultListModel p1Hand;
	DefaultListModel p2Hand;


	// Gives Players cards to form their first Hands
	private void deal(Card [] cards)
	{
		for (int i = 0; i < cards.length; i ++) 
			cards[i] = (Card)cardDeck.dealCard();
	}



	// Lays a card in one of the available spaces for sets or runs
	void layCard(Card card)
	{
		char rank = card.getRank();
		char suit = card.getSuit();
		int rankIndex =  Card.getRankIndex(rank);
		int suitIndex =  Card.getSuitIndex(suit);
		//setPanels[rankIndex].array[suitIndex].setText(card.toString());
		
		//System.out.println("laying " + card);
		
		setPanels[rankIndex].array[suitIndex].setIcon(card.getCardImage());
	}



	// Prints Player's HAND on the terminal
	public String showHand(DefaultListModel playerHand) {

		// Returns Hand, with upppercased Card letters, without square brackets
		return playerHand.toString().toUpperCase().replaceAll("\\[", "").replaceAll("\\]", "");
	}



	// Prints the card from the DECK that was just added to the Player's hand
	public void showAddedCard(Card card) {
		System.out.println("\tAdded: " + card.toString().toUpperCase());
	}



	// Prints the card from the Player's hand that was just discarded to the STACK Pile
	public void showDiscardedCard(Card card) {
		System.out.println("\tDiscarded: " + card.toString().toUpperCase());
	}



	// Sorts the Player's HAND
	public void sortPlayerHand(DefaultListModel playerHand) {

		// Creates a CARD and a CARD array to use as temporary containers
		Card tempCard;
		Card [] tempHand = new Card [playerHand.getSize()];

		// Integer variables to keep track of CARD suit and rank values
		int rank1 = 0;
		int suit1 = 0;
		int rank2 = 0;
		int suit2 = 0;


		// Copies the unsorted Player's HAND and copies it to the temporary HAND
		for (int i=0; i < playerHand.getSize(); i++) 
			tempHand[i] = (Card) playerHand.getElementAt(i);
		
		// Length of the temporary HAND
		int n = tempHand.length;
		

		// Sorting Cards by Suit
		for (int i=0; i < n-1; i++) {
			for (int j=0; j < n-1; j++) {

				// Obtains the suit of the current card and the next card
				suit1 = tempHand[j].getSuitIndex(tempHand[j].getSuit());
				suit2 = tempHand[j+1].getSuitIndex(tempHand[j+1].getSuit());

				// Switches the position of the current card with the next card
				// if the suit of the current card is bigger than suit of the next card
		 		if ( suit1 - suit2 > 0 ) {
		 			tempCard = tempHand[j];
		 			tempHand[j] = tempHand[j+1];
		 			tempHand[j+1] = tempCard;
		 		}
		 	}
		}


		// Sorting Cards by Rank
		for (int i=0; i < n-1; i++) {
			for (int j=0; j < n-1; j++) {

				// Obtains the rank and suit of the current card and the next card
				rank1 = tempHand[j].getRankIndex(tempHand[j].getRank());
				suit1 = tempHand[j].getSuitIndex(tempHand[j].getSuit());

				rank2 = tempHand[j+1].getRankIndex(tempHand[j+1].getRank());
				suit2 = tempHand[j+1].getSuitIndex(tempHand[j+1].getSuit());


				// Switches the position of the current card with the next card
				// if the current card and the next card are of the same suit and
				// if the rank of the current card is bigger than the rank of the next card
		 		if ( (suit1 - suit2 == 0) && (rank1 - rank2 > 0) ) {
		 			tempCard = tempHand[j];
		 			tempHand[j] = tempHand[j+1];
		 			tempHand[j+1] = tempCard;
		 		}
		 	}
		}

		// Empties the Player's unsorted HAND
		playerHand.removeAllElements();

		// Copies the sorted temporary HAND to the Player's HAND
		for (int i=0; i < n; i++)
			playerHand.addElement( (Card) tempHand[i] );
	}



	// Displays the points of the Players' Hands when the game is over
	public void showPlayersPoints(DefaultListModel player1Hand, DefaultListModel player2Hand) {

		Card card1;
		Card card2;
		Hand p1FinalHand = new Hand();
		Hand p2FinalHand = new Hand(); 
		int p1HandPoints = 0;
		int p2HandPoints = 0;
		int comparison = 0;


		// Makes Player 1's current hand their final hand
		for (int i = 0; i < player1Hand.getSize(); i++) {
			card1 = (Card) player1Hand.get(i);
			p1FinalHand.addCard(card1);
		}

		// Makes Player 2's current hand their final hand
		for (int i = 0; i < player2Hand.getSize(); i++) {
			card2 = (Card) player2Hand.get(i);
			p2FinalHand.addCard(card2);
		}

		// Obtains the Players' Points of their final hands
		// and checks which final hand has a smaller value
		p1HandPoints = p1FinalHand.evaluateHand();
		p2HandPoints = p2FinalHand.evaluateHand();
		comparison = p1FinalHand.compareTo(p2FinalHand);

		// Prints the Players' Final Hand Points
		System.out.println("Points: " + p1HandPoints + " to " + p2HandPoints);

		// Player 2's final hand is bigger
		if (comparison < 0)
		 	System.out.println("Player 1 Wins!");
 
		// Player 1's final hand is bigger
		else if (comparison > 0)
			System.out.println("Player 2 Wins!");

		// Both final hands have the same number of points
		else 
			System.out.println("It's a Tie!");

		// Closes game window
		System.exit(0);
	}



	// Ends game if a Player's HAND is empty (EMPTY HAND) and displays who won
	public void endGameOnEmptyHand(DefaultListModel player1Hand, DefaultListModel player2Hand) {

		if (player1Hand.isEmpty())
			System.out.println("Player 1 doesn't have any more cards in their hand...");

		else if (player2Hand.isEmpty())
			System.out.println("Player 2 doesn't have any more cards in their hand...");

		showPlayersPoints(player1Hand, player2Hand);
	}



	// Ends game if the DECK runs out of cards to play with (EMPTY DECK) and displays who won
	public void endGameOnEmptyDeck(DefaultListModel player1Hand, DefaultListModel player2Hand) {

		System.out.println("There are no more cards on the Deck...");
		
		showPlayersPoints(player1Hand, player2Hand); 
	}



	public Table() {

		// Window Title
		super("Rummy: The Card Game of the Century!");

		// Window Size and Proportions
		setLayout(new BorderLayout());
		setSize(1200,700);
		
		
		// Makes Table.java program end when the Rummy game window is closed
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


		// Creating new DECK
		cardDeck = new Deck();

		// Adding Cards to DECK
		for (int i = 0; i < Card.suit.length; i++) {
			for (int j = 0; j < Card.rank.length; j++) {
				Card card = new Card(Card.suit[i],Card.rank[j]);
				cardDeck.addCard(card);
			}
		}
		// Shuffling DECK
		cardDeck.shuffle();

		// Creating STACK
		stackDeck = new Deck();


		// Takes DECK's first Card and adds it to the STACK Deck
		Card firstCard = cardDeck.dealCard();
		stackDeck.addCard(firstCard);




		// TOP
		JPanel top = new JPanel();

		for (int i = 0; i < Card.rank.length;i++)
			setPanels[i] = new SetPanel(Card.getRankIndex(Card.rank[i]));

		top.add(setPanels[0]);
		top.add(setPanels[1]);
		top.add(setPanels[2]);
		top.add(setPanels[3]);

		// Setting Player 1 to NORTH
		player1 = new JPanel();
		player1.add(top);
		add(player1, BorderLayout.NORTH);




		// BOTTOM
		JPanel bottom = new JPanel();

		bottom.add(setPanels[4]);
		bottom.add(setPanels[5]);
		bottom.add(setPanels[6]);
		bottom.add(setPanels[7]);
		bottom.add(setPanels[8]);

		// Setting Player 2 to SOUTH
		player2 = new JPanel();
		player2.add(bottom);
		add(player2, BorderLayout.SOUTH);




		// MIDDLE
		JPanel middle = new JPanel(new GridLayout(1,3));

		// Adding Player 1's Playable Buttons
		p1Stack = new JButton("Stack");
		p1Stack.addActionListener(this);
		p1Deck = new JButton("Deck");
		p1Deck.addActionListener(this);
		p1Lay = new JButton("Lay");
		p1Lay.addActionListener(this);
		p1LayOnStack = new JButton("LayOnStack");
		p1LayOnStack.addActionListener(this);


		// Deals Player 1's cards to make their first Hand 
		Card [] cardsPlayer1 = new Card[numDealtCards];
		deal(cardsPlayer1);


		// Makes Player 1's first Hand a HandPile to be able to add it on the Table 
		p1Hand = new DefaultListModel();
		for (int i = 0; i < cardsPlayer1.length; i++)
			p1Hand.addElement(cardsPlayer1[i]);

		// Sorts Player 1's First Hand
		sortPlayerHand(p1Hand);

		// Printing Player 1's first Hand on the terminal
		System.out.println("Initial Player 1: " + showHand(p1Hand));	
	
		p1HandPile = new JList(p1Hand);


		// Makes Player 1's First Hand and Buttons part of the Table
		middle.add(new HandPanel("Player 1", p1HandPile, p1Stack, p1Deck, p1Lay, p1LayOnStack));


		// Sets how DECK and STACK Piles will be added to the Table
		deckPiles = new JPanel();
		deckPiles.setLayout(new BoxLayout(deckPiles, BoxLayout.Y_AXIS));
		deckPiles.add(Box.createGlue());




		// LEFT
		JPanel left = new JPanel();
		left.setAlignmentY(Component.CENTER_ALIGNMENT);


		// Sets STACK's position
		stack = new JLabel("Stack");
		stack.setAlignmentY(Component.CENTER_ALIGNMENT);
		left.add(stack);


		topOfStack = new JLabel();
		// Sets STACK's image icon design from blank gif to the gif of the first card of the game
		// topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
		topOfStack.setIcon(firstCard.getCardImage());
		// Sets TOP OF STACK's position
		topOfStack.setAlignmentY(Component.CENTER_ALIGNMENT);
		left.add(topOfStack);


		deckPiles.add(left);
		deckPiles.add(Box.createGlue());




		// RIGHT
		JPanel right = new JPanel();
		right.setAlignmentY(Component.CENTER_ALIGNMENT);

		// Sets DECK's position
		deck = new JLabel("Deck");
		deck.setAlignmentY(Component.CENTER_ALIGNMENT);
		right.add(deck);

		// Sets DECK PILE's position
		deckPile = new JLabel();
		// Changed DECK's icon appearance from generic design
		// to UPRRP's Department of CCOM Facebook Profile Picture
		// deckPile.setIcon(new ImageIcon(Card.directory + "b.gif"));
		deckPile.setIcon(new ImageIcon(Card.directory + "uprrpCCOM_logo.gif"));
		deckPile.setAlignmentY(Component.CENTER_ALIGNMENT);

		right.add(deckPile);
		deckPiles.add(right);
		deckPiles.add(Box.createGlue());
		middle.add(deckPiles);


		// Adding Player 2's Playable Buttons
		p2Stack = new JButton("Stack");
		p2Stack.addActionListener(this);
		p2Deck = new JButton("Deck");
		p2Deck.addActionListener(this);
		p2Lay = new JButton("Lay");
		p2Lay.addActionListener(this);
		p2LayOnStack = new JButton("LayOnStack");
		p2LayOnStack.addActionListener(this);


		// Deals Player 2's cards to make their first Hand 
		Card [] cardsPlayer2 = new Card[numDealtCards];
		deal(cardsPlayer2);


		// Makes Player 2's first Hand a HandPile to be able to add it on the Table 
		p2Hand = new DefaultListModel();
		for (int i = 0; i < cardsPlayer2.length; i++)
			p2Hand.addElement(cardsPlayer2[i]);

		// Sorts Player 2's First Hand
		sortPlayerHand(p2Hand);

		// Printing Player 2's first Hand on the terminal
		System.out.println("Initial Player 2: " + showHand(p2Hand));		

		p2HandPile = new JList(p2Hand);


		// Makes Player 2's First Hand and Buttons part of the Table
		middle.add(new HandPanel("Player 2", p2HandPile, p2Stack, p2Deck, p2Lay, p2LayOnStack));

		add(middle, BorderLayout.CENTER);


		// Sets space for boxes where laid cards will be to make sets and runs 
		JPanel leftBorder = new JPanel(new GridLayout(2,1));

		setPanels[9].setLayout(new BoxLayout(setPanels[9], BoxLayout.Y_AXIS));
		setPanels[10].setLayout(new BoxLayout(setPanels[10], BoxLayout.Y_AXIS));
		leftBorder.add(setPanels[9]);
		leftBorder.add(setPanels[10]);
		add(leftBorder, BorderLayout.WEST);


		JPanel rightBorder = new JPanel(new GridLayout(2,1));

		setPanels[11].setLayout(new BoxLayout(setPanels[11], BoxLayout.Y_AXIS));
		setPanels[12].setLayout(new BoxLayout(setPanels[12], BoxLayout.Y_AXIS));
		rightBorder.add(setPanels[11]);
		rightBorder.add(setPanels[12]);
		add(rightBorder, BorderLayout.EAST);
	}



	public void actionPerformed(ActionEvent e) {

		// Records the source of the event that was triggered
		Object src = e.getSource();


		// Player 1's or Player 2's DECK Button clicked
		if ( ((src == p1Deck) && (p1Turn == true) && (p1Hand.size() < 5)) || 
			 ((src == p2Deck) && (p2Turn == true) && (p2Hand.size() < 5)) ) {

			Card card = cardDeck.dealCard();

			if (card != null) {
				
				// When Player 1 presses Deck, it's their turn and they haven't picked a card
				if ( (src == p1Deck) && (p1Turn == true) && (p1Picked == false) && (p1Laid == true) ) {

					System.out.println("Player 1");

					// Adds a card to Player 1's hand from Deck
					p1Hand.addElement(card);

					// Player 1 picked a card, now they have to lay a card
					p1Picked = true;
					p1Laid = false;

					// Prints the card that was just added to Player 1's HAND
					showAddedCard(card);

					// Sorts Player 1's hand
					sortPlayerHand(p1Hand);
				}


				// When Player 2 presses Deck, it's their turn and they haven't picked a card
				else if ( (src == p2Deck) && (p2Turn == true) && (p2Picked == false) && (p2Laid == true)) {

					System.out.println("Player 2");

					// Adds a card to Player 2's hand from Deck
					p2Hand.addElement(card);

					// Player 2 picked a card, now they have to lay a card
					p2Picked = true;
					p2Laid = false;

					// Prints the card that was just added to Player 2's HAND
					showAddedCard(card);

					// Sorts Player 1's hand
					sortPlayerHand(p2Hand);			
				}
			}

			// If Deck runs out of cards, end game
			if (cardDeck.isEmpty()) {
				topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
				endGameOnEmptyDeck(p1Hand, p2Hand);
			}
		}



		// Player 1's or Player 2's STACK Button clicked
		if ( ((src == p1Stack) && (p1Hand.size() < 5)) || 
			 ((src == p2Stack) && (p2Hand.size() < 5)) ) {


			// When Player 1 presses Stack, it's their turn and they haven't picked a card
			if ( (src == p1Stack) && (p1Turn == true) && (p1Picked == false) && (p1Laid == true) ) {

				System.out.println("Player 1");

				// Remove a card from the stack
				Card card = stackDeck.removeCard();

				// Puts the next card from the Stack faceup if it's not empty
				if (card != null) {
					Card topCard = stackDeck.peek();

					if (topCard != null)
						topOfStack.setIcon(topCard.getCardImage());
					else
						topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));	
				}

					// Adds the card picked from the Stack to Player 1's hand
					p1Hand.addElement(card);

					// Player 1 picked a card, now they have to lay a card
					p1Picked = true;
					p1Laid = false;

					// Prints the card that was just added to Player 1's HAND
					showAddedCard(card);

					// Sorts Player 1's hand
					sortPlayerHand(p1Hand);
			}

			// When Player 2 presses Stack, it's their turn and they haven't picked a card
			else if ( (src == p2Stack) && (p2Turn == true) && (p2Picked == false) && (p2Laid == true) ) {

				System.out.println("Player 2");

				// Remove a card from the stack
				Card card = stackDeck.removeCard();

				// Puts the next card from the Stack faceup if it's not empty
				if (card != null) {
					Card topCard = stackDeck.peek();

					if (topCard != null)
						topOfStack.setIcon(topCard.getCardImage());
					else
						topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));	
				}

					// Adds the card picked from the Stack to Player 2's hand
					p2Hand.addElement(card);

					// Player 2 picked a card, now they have to lay a card
					p2Picked = true;
					p2Laid = false;

					// Prints the card that was just added to Player 2's HAND
					showAddedCard(card);

					// Sorts Player 2's hand
					sortPlayerHand(p2Hand);
			}
		}



		// Player 1's LAY Button clicked
		// When Player 1 presses Lay, it's their turn and they haven't laid a card
		if ( (src == p1Lay) && (p1Turn == true) && (p1Picked == true) && (p1Laid == false) ) {

			Object [] cards = p1HandPile.getSelectedValues();

			if (cards != null) {

				for (int i = 0; i < cards.length; i++) {
					Card card = (Card)cards[i];
					
					// Lays card on the Table
					layCard(card);
					
					// Removes card from Player 1's hand
					p1Hand.removeElement(card);

					// Prints the card that was just discarded from Player 1's HAND
					showDiscardedCard(card);

					// Sorts Player 1's Hand
					sortPlayerHand(p1Hand);

					// Displays Player 1's Hand 
					if (!p1Hand.isEmpty())
						System.out.println("\tHand now: " + showHand(p1Hand) + ", ");
				}
			}

			// Ends game if Player 1 has an EMPTY HAND
			if (p1Hand.isEmpty()) {
				System.out.println("\tHand now: EMPTY");
				endGameOnEmptyHand(p1Hand, p2Hand);
			}
		}



		// Player 2's LAY Button clicked
		// When Player 2 presses Lay, it's their turn and they haven't laid a card
		if ( (src == p2Lay) && (p2Turn == true) && (p2Picked == true) && (p2Laid == false) ) {

			Object [] cards = p2HandPile.getSelectedValues();

			if (cards != null) {

				for (int i = 0; i < cards.length; i++) {
					Card card = (Card)cards[i];

					// Lays card on the Table
					layCard(card);

					// Removes card from Player 1's hand
					p2Hand.removeElement(card);

					// Prints the card that was just discarded from Player 2's HAND
					showDiscardedCard(card);
					
					// Sorts Player 2's hand
					sortPlayerHand(p2Hand);

					// Displays Player 2's hand
					if (!p2Hand.isEmpty())
						System.out.println("\tHand now: " + showHand(p2Hand) + ", ");
				}
			}

			// Ends game if Player 2 has an EMPTY HAND
			if (p2Hand.isEmpty()) {
				System.out.println("\tHand now: EMPTY");
				endGameOnEmptyHand(p1Hand, p2Hand);
			}
		}



		// Player 1's LAY ON STACK Button clicked
		// When Player 1 presses Lay, it's their turn and they haven't laid a card
		if ( (src == p1LayOnStack) && (p1Turn == true) && (p1Picked == true) && (p1Laid == false) ) {

			int [] num  = p1HandPile.getSelectedIndices();

			if (num.length == 1) {
				Object obj = p1HandPile.getSelectedValue();

				if (obj != null) {
					
					// Removes card from Player 1's hand
					p1Hand.removeElement(obj);

					Card card = (Card)obj;
					stackDeck.addCard(card);
					topOfStack.setIcon(card.getCardImage());

					// Prints the card that was just discarded from Player 1's HAND
					showDiscardedCard(card);

					// Sorts Player 1's Hand 
					sortPlayerHand(p1Hand);

					// Player 1 laid a card on the stack, now they have to pick a card from the deck or stack
					// Ends Player 1's turn
					p1Turn = false;
					p1Picked = false;
					p1Laid = true;

					// Starts Player 2's turn
					p2Turn = true;
				}
			}

			// Ends game if Player 1 has an EMPTY HAND
			if (p1Hand.isEmpty())
				endGameOnEmptyHand(p1Hand, p2Hand);
		}



		// Player 2's LAY ON STACK Button clicked
		// When Player 2 presses Lay, it's their turn and they haven't laid a card
		if ( (src == p2LayOnStack) && (p2Turn == true) && (p2Picked == true) && (p2Laid == false) ) {

			int [] num  = p2HandPile.getSelectedIndices();

			if (num.length == 1) {
				Object obj = p2HandPile.getSelectedValue();

				if (obj != null) {

					// Removes card from Player 2's hand
					p2Hand.removeElement(obj);

					Card card = (Card)obj;
					stackDeck.addCard(card);
					topOfStack.setIcon(card.getCardImage());

					// Prints the card that was just discarded from Player 2's HAND
					showDiscardedCard(card);

					// Sorts Player 2's hand
					sortPlayerHand(p1Hand);

					// Player 2 laid a card on the stack, now they have to pick a card from the deck or stack
					// Ends Player 2's turn
					p2Turn = false;
					p2Picked = false;
					p2Laid = true;

					// Starts Player 1's turn
					p1Turn = true;
				}
			}

			// Ends game if Player 2 has an EMPTY HAND
			if (p2Hand.isEmpty())
				endGameOnEmptyHand(p1Hand, p2Hand);
		}
	}

	// main moved to Proj2.java
	// public static void main(String args[]) {
	// 	Table t = new Table();
	// 	t.setVisible(true);
	// }
}


class HandPanel extends JPanel {

	public HandPanel(String name,JList hand, JButton stack, JButton deck, JButton lay, JButton layOnStack) {

		// model = hand.createSelectionModel();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// add(Box.createGlue());
		JLabel label = new JLabel(name);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label);
		stack.setAlignmentX(Component.CENTER_ALIGNMENT);
		// add(Box.createGlue());
		add(stack);
		deck.setAlignmentX(Component.CENTER_ALIGNMENT);
		// add(Box.createGlue());
		add(deck);
		lay.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lay);
		layOnStack.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(layOnStack);
		add(Box.createGlue());
		add(hand);
		add(Box.createGlue());
	}
}


class SetPanel extends JPanel {

	private Set data;
	JButton [] array = new JButton[4];


	public SetPanel(int index) {
		super();
		data = new Set(Card.rank[index]);

		for (int i = 0; i < array.length; i++) {
			array[i] = new JButton("   ");
			add(array[i]);
		}
	}
}

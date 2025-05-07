import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * The Deck class is a manager for the shuffling and distributing of Card objects.
 * 
 * @author Wayne Bai
 * @version 1.0
 * 
 */
public class Deck {

    private ArrayList<Card> deck;
    private Random shuffler;

    private static final String[] CARD_NAMES = new String[]{
        "Ace",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "Jack",
        "Queen",
        "King",
    };

    private static final String[] SUITS = new String[] {
        "Hearts", 
        "Clubs",
        "Diamonds", 
        "Spades", 
    };
    
    private static ArrayList<Card> defaultDeck = null;

    /**
     * Builds the standard deck and returns a copy of it.
     * The first standard deck that is built is reused to save memory.
     * @return
     */
    @SuppressWarnings("unchecked")
    private static ArrayList<Card> getStandardDeck() {
        if (defaultDeck == null) {
            
            
            ArrayList<Card> newDeck = new ArrayList<Card>();
            
            for (int suitIndexNum = 0; suitIndexNum < SUITS.length; suitIndexNum += 1) {
                
                String cardSuit = SUITS[suitIndexNum];

                for (int cardIndexNum = 0; cardIndexNum < CARD_NAMES.length; cardIndexNum += 1) {
                    
                    String cardName = CARD_NAMES[cardIndexNum];
                    
                    // ENSURING CONTINUITY 
                // each complete suit takes up 12 spots
                // each card takes up 1 spot
                // number of completed suits = suitIndexNum
                // number of cards in the current suit = cardIndexNum
                // visualization: (no. of Suits, no. of Cards) -> (index)
                // (0,0) -> (0)
                // (0,1) -> (1)
                //...
                // (0,11) -> (11)
                // (1,0) -> (12)
                // ...
                // (1,11) -> (13 + 12 = 25)
                // (2,0) -> (0 + 2 * (12) = 2 * 12 = 26)
                
                int deckIndex = cardIndexNum + suitIndexNum * (CARD_NAMES.length);
                
                
                // CALCULATING A "PREDICTABLE" DEFAULT VALUE FOR CARDS
                
                // expectations:
                // (suitIndexNum, cardIndexNum) vs. (suitIndexNum, cardIndexNum)
                // (0,1) < (1,1)
                // (0,2) > (1,1)
                // cardIndexNum * SUITS.length + suitIndexNum
                
                int cardDefaultValue = (cardName.equals("Ace") ? CARD_NAMES.length : cardIndexNum) * SUITS.length + suitIndexNum;
                
                Card newCard = new Card(cardName, cardSuit, cardDefaultValue);
                
                newDeck.add(deckIndex, newCard);
                
                    
                }
                
            }
            defaultDeck = newDeck;
        }

        return (ArrayList<Card>) defaultDeck.clone();
    }


    /**
     * Creates a deck of cards from an array of cards.
     * Removes any null values in the array, shifting later elements up.
     * 
     * @param deck is an array of Card objects
     */
    public Deck(Card[] deck) {
        this.deck = new ArrayList<Card>(Arrays.asList(deck));
        this.removeNull();
    }

    /**
     * Creates a standard deck of cards.
     * In order to conserve memory, a reference of the same objects are made if this method is 
     * used more than once. This ensures that two of the same objects arent created since the
     * dependencies of the card equal() method or any relevant attribute are static
     * 
     * 
     */
    public Deck() {
        this.deck = getStandardDeck();
    }

    /**
     * Removes all occurrences of null in an ArrayList
     */
    private void removeNull() {
        this.deck.removeAll(new ArrayList<>(Arrays.asList(new Object[]{null})));
    }

    /**
     * gets the size of the current deck
     * 
     * @return the size of the deck
     */
    public int size() {
        return this.deck.size();
    }

    /**
     * Draws a card from the deck
     * 
     * @return the card at the top of the deck
     */
    public Card draw() {

        int topIndex = this.deck.size() - 1;

        if (topIndex < 0) { return null; } // if deck is empty, return nothing

        Card drawnCard = this.deck.get(topIndex);

        this.deck.remove(topIndex);

        return drawnCard;
    }

    /**
     * Implements a fisher-yates style shuffle to shuffle the deck
     * this shuffle goes from the top of the deck to the bottom of the deck
     * where at every index, the card is swapped with another card that is below it
     * 
     */
    public void shuffle() {
        this.shuffler = this.shuffler != null ? this.shuffler : new Random();

        for (int i = this.deck.size() - 1; i >= 0; i--) {
            // use i + 1 because nextInt(n) includes range of [0,n) 
            Collections.swap(this.deck, i, this.shuffler.nextInt(i + 1));
        }
    }

    /**
     * Uses selection sort to sort the deck
     */
    public void sort() {

        int iterationLength = this.deck.size() - 1;
        for (int i = 0; i < iterationLength; i++) {
            
            Card smallest = this.deck.get(i);
            int smallestInd = i;
            for (int c = i + 1; c <= iterationLength; c++) {

                Card compareTo = this.deck.get(c);

                if (smallest.getValue() > compareTo.getValue()) {
                    smallest = compareTo;
                    smallestInd = c;
                }
            }

            Collections.swap(this.deck, i,smallestInd);
        }
        
    }

    /**
     * If the user wants to create a new shuffler
     * 
     * @param seed
     */
    public void newShuffler(int seed) throws Exception {
        if (this.shuffler != null) {
            throw new Exception("You cannot alter the shuffler because it is already set.");
        }
        this.shuffler = new Random(seed);
    }

    /**
     * adds a card to the deck
     * 
     * @param newCard
     */
    public void addCard(Card newCard) {
        if (newCard == null) {return; }
        this.deck.add(newCard);
        shuffle();
    }

    /**
     * Adds some cards to the deck and reshuffles the deck
     * 
     * @param newCards
     */
    public void reshuffle(Card[] newCards) {
        for (int i = 0; i < newCards.length; i++) {
            this.addCard(newCards[i]);
        }
        this.shuffle();
    }

     /**
     * creates a representation of the deck as a string
     * @return the deck as a string
     */
    @Override
    public String toString() {
        
        String returnValue = "";
        
        int iterationLength = this.deck.size() - 1;
        for (int i = 0; i <= iterationLength; i++) {
            returnValue += this.deck.get(i).toString();
            if (i == iterationLength) {
                continue;
            }
            returnValue += ", ";
        }

        return returnValue;


    }


}

/*
 * ## The Deck class

Contains a deck of cards.</br>
It should have two constructors:</br>

The first constructor takes in an array of cards and sets it as the deck.</br>
The second Constructor is required that takes no parameters and generates an unshuffled deck of cards - Ace through King for each suit.</br>
An unshuffled deck should have the cards in the suit order of _hearts, clubs, diamonds, spades._ (This should be done in your constructor)</br>

The following methods are required:</br>

A size() method which returns the amount of cards in the deck.</br>
A draw() method that removes the top card from the deck and returns it. (The top of the deck is up to you. It should be consistent). If there are no cards left in the deck, return null.</br>
A shuffle() method that rearranges the order of the cards in the deck, use Google if you get stuck here to help you with this if needed (DO NOT COPY CODE).</br>

Here are some helpful hints to help you along the way:</br>

Remember to be consistent with which end of the deck is the "top".</br>
Make sure your shuffle method provides good randomization.</br>
Check for edge cases like empty decks or null cards.</br>

 */

 /*
### Additional Deck methods:
An addCard(Card card) method that adds the provided card into the deck. Should not add the card if it is null.</br>
A reshuffle(Card[] cards) method that adds all of the cards in the provided card array into the deck. Shuffle the deck after adding them.</br>

  */
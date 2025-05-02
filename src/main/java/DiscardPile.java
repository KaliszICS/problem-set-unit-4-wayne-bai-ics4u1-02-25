import java.util.ArrayList;
import java.util.Arrays;

public class DiscardPile {
    
    // used to represent the Card[] type when using ArrayList.toArray
    private static final Card[] CARD_ARRAY_TYPE = new Card[0];
    private ArrayList<Card> pile;

    /**
     * creates a new discard pile from an array of discarded cards.
     * @param discardedCards
     */
    public DiscardPile(Card[] discardedCards) {
        this.pile = new ArrayList<Card>(Arrays.asList(discardedCards));
    }

    /**
     * creates an empty discard pile
     */
    public DiscardPile() {
        this.pile = new ArrayList<Card>();
    }


    public int size() {
        return this.pile.size();
    }

    public void addCard(Card card) {
        this.pile.add(card);
    }

    public Card removeCard(Card card) {
        if (this.pile.remove(card)) {
            return card;
        }
        return null;
    }

    public Card[] removeAll() {

        Card[] returnValue = this.pile.toArray(CARD_ARRAY_TYPE);

        this.pile.removeAll(this.pile);

        return returnValue;
    }

    @Override
    public String toString() {
        
        String returnValue = "";
        
        int iterationLength= this.pile.size() - 1;
        for (int i = 0; i < iterationLength; i++) {
            returnValue += this.pile.get(i).toString();
            if (i == iterationLength) {
                continue;
            }
            returnValue += ", ";
        }

        return returnValue;


    }
}

/*
## The DiscardPile class

Contains a discard pile of cards.</br>
It should have two constructors:</br>
The first constructo takes in an array of cards and sets it as the discard pile.</br>
The second Constructor is required that takes no parameters and generates an empty discard pile.</br>

The DiscardPile should have the following methods:</br>
A getter that returns the discard pile as an array of cards.</br>
A size() method which returns the amount of cards in the discard pile.</br>
An addCard(Card card) method that adds the provided card into the discard pile.</br>
A removeCard(Card card) method which removes the specified card from the discard pile and returns it. If the card does not exist, return null.</br>
A removeAll() method that returns an array of cards and removes them all from your discard pile. If there are no cards in the discard pile return an empty Card array.</br>
A toString() method that returns all of the cards in the format "Ace of hearts, King of Hearts, Queen of Hearts, Jack of Hearts, 9 of Spades."</br>

*/
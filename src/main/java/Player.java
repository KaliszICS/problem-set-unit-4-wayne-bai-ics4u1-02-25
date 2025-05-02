import java.util.ArrayList;
import java.util.Arrays;
public class Player {
    
    private String name;
    private int age;
    private ArrayList<Card> hand;

    public Player(String name, int age, Card[] hand) {
        this.name = name;
        this.age = age;
        this.hand = new ArrayList<Card>(Arrays.asList(hand));
    }

    public Player(String name, int age) {
        this.name = name;
        this.age = age;
        this.hand = new ArrayList<Card>();
    }

    public int size() {
        return this.hand.size();
    }

    public void draw(Deck deck) {
        Card drawnCard = deck.draw();
        this.hand.add(drawnCard);
    }

    public boolean discardCard(Card card, DiscardPile discardPile) {

        if (this.hand.remove(card)) {
            discardPile.addCard(card);
            return true;
        }
        return false;
    }

    public boolean returnCard(Card card, Deck deck) {
        if (this.hand.remove(card)) {
            deck.addCard(card);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String returnValue = this.name + ", " + this.age;
        if (this.hand.size() > 0) {
            returnValue += ", ";

            int iterationLength = this.hand.size() - 1;

            for (int i = 0; i < iterationLength; i++) {

                returnValue += this.hand.get(i).toString();

                // add a comma only if this isn't the last element
                if (iterationLength == i) {
                    continue;
                }
                returnValue += ", ";
            }

        }

        return returnValue;
    }
}


/*
## The Player class

Should have two constructors:</br>
The first Constructor should take a _name_ and _age_ and an array of cards which should be assigned to the hand.</br>
The second Constructor should take a _name_ and _age_ while creating a default empty hand of cards.</br>

Has getters for its name, age and hand (hand should return an array of cards).</br>

The following methods are required:</br>

A size() method which returns the size of the hand.</br>
A draw(Deck deck) method that adds a card from the deck to the hand of the player.</br>
A discardCard(Card card, DiscardPile discardPile) method that discards a card from the hand to the given deck's discard pile.</br>
Return true if the card exists in the hand. False otherwise.</br>
A returnCard(Card card, Deck deck) method that returns the specified card to the deck. Return true if the card exists in the hand. False otherwise.</br>
A toString() method that will print out their name, age, and hand of cards (e.g. "Mr. Kalisz, 99, Ace of hearts, King of Hearts, Queen of Hearts, Jack of Hearts, 9 of Spades.")</br>

*/
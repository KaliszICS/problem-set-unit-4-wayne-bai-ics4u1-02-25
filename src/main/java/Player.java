import java.util.ArrayList;
import java.util.Arrays;
/**
 * A Players class which represents the framework for user data management.
 * 
 * @author Wayne Bai
 * @version 1.0
 * 
 */
public class Player {
    
    // An empty Card[] which is used to represent object type to ArrayList.toArray method
    private static final Card[] CARD_ARRAY_TYPE = new Card[0];

    private String name;
    private int age;
    private ArrayList<Card> hand;
    private int points = 0;
    private int wins = 0;
    private int losses = 0;
    private Bot bot;

    /**
     * creates a new player object with a name, age, and a given hand
     * @param name of the player
     * @param age of the player
     * @param hand of the player
     */
    public Player(String name, int age, Card[] hand) {
        this.name = name;
        this.age = age;
        this.hand = new ArrayList<Card>(Arrays.asList(hand));
    }

    /**
     * creates a new player object with an empty hand
     * @param name of the player
     * @param age of the player
     */
    public Player(String name, int age) {
        this.name = name;
        this.age = age;
        this.hand = new ArrayList<Card>();
    }

    /**
     * gets the name of the player
     * 
     * @return the name of the player
     */
    public String getName() {
        return this.name;
    }

    /**
     * gets the age of the player
     * 
     * @return the age of the player
     */
    public int getAge() {
        return this.age;
    }
    
    /**
     * gets the hand of the player
     * 
     * @return the hand of the player
     */
    public Card[] getHand() {
        return this.hand.toArray(CARD_ARRAY_TYPE);
    }

    /**
     * gets the size of the player's hand
     * 
     * @return the size of the player's hand
     */
    public int size() {
        return this.hand.size();
    }

    /**
     * adds a card from a deck to the player's hand
     * 
     * @param deck to draw from
     */
    public void draw(Deck deck) {
        Card drawnCard = deck.draw();
        if (drawnCard != null) {
            this.hand.add(drawnCard);
        }
    }

    /**
     * discards a card to a discard pile
     * 
     * @param card to discard
     * @param discardPile to discard into
     * @return whether the discard was successful
     */
    public boolean discardCard(Card card, DiscardPile discardPile) {

        if (this.hand.remove(card)) {
            discardPile.addCard(card);
            return true;
        }
        return false;
    }

    /**
     * returns a card to a deck
     * 
     * @param card to return
     * @param deck to return the card to
     * @return whether the return was successful
     */
    public boolean returnCard(Card card, Deck deck) {
        if (card == null) { return false; }
        
        if (this.hand.remove(card)) {
            deck.addCard(card);
            return true;
        }
        return false;
    }

    /**
     * removes the player's entire hand to a deck
     * 
     * @param deck
     */
    public void clearHand(Deck deck) {
        Card[] hand = this.getHand();
        for (int i = 0; i < hand.length; i++) {
            deck.addCard(hand[i]);
        }
        this.hand.removeAll(this.hand);
    }

    /**
     * get the number of points the player has
     * 
     * @return the number of points the player has
     */
    public int getPoints() {
        return this.points;
    }
    
    /**
     * adds points to the player.
     * this method should only be used on the server side
     * 
     * @param points to add
     */
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * sets the result of a game the player was in.
     * sets the players game points to 0
     * this method should only be used on the server side
     * 
     * @param wonTheGame whether the player won the game
     */
    public void setGameResult(boolean wonTheGame) {
        if (wonTheGame) {
            this.wins += 1;
        } else {
            this.losses += 1;
        }
        this.points = 0;
    }

    /**
     * gets the w/l ratio of the player
     * @return the w/l ratio
     */
    public int getWLRatio() {
        if (this.losses == 0) {
            return (int) Math.pow(2,31)-1;
        }
        return this.wins / this.losses;
    }

    public void setBot(Bot bot) throws Exception {
        if (this.bot != null) {
            throw new Exception("This player already has a bot");
        }
        this.bot = bot;
    }

    public Bot getBot() {
        return this.bot;
    }

    /**
     * creates a string to represent the player with 
     * the structure of name, age, 
     * 
     * @return the string 
     */
    @Override
    public String toString() {
        String returnValue = this.name + ", " + this.age;
        if (this.hand.size() > 0) {
            returnValue += ", ";

            int iterationLength = this.hand.size() - 1;

            for (int i = 0; i <= iterationLength; i++) {

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
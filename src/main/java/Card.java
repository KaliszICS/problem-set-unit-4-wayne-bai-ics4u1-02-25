/**
 * The Card class creates objects that are invariable after creation which are used to represent parts of a card game.
 * 
 * @author Wayne Bai
 * @version 1.0
 * 
 */
public class Card {
    private String name;
    private String suit;
    private int value;

    /**
     * creates a new card object from a name, suit and value
     * @param name of the card
     * @param suit of the card
     * @param value of the card
     */
    public Card(String name, String suit, int value) {
        this.name = name;
        this.suit = suit;
        this.value = value;
    }

    /**
     * gets the name of the card
     * 
     * @return the name of the card
     */
    public String getName() {
        return this.name;
    }

    /**
     * gets the suit of the card
     * 
     * @return the suit of the card
     */
    public String getSuit() {
        return this.suit;
    }


    /** gets the internal value of the card
     * 
     * @return the value of the card
     */
    public int getValue() {
        return this.value;
    }

    /**
     * override for toString method to return name of suit
     * 
     * @return a representation of the card in the 'name of suit' format
     */
    @Override
    public String toString() {
        return this.name + " of " + this.suit; //+ " = " + this.value;
    }

    /**
     * comparison on whether the two cards are equal by comparing their attributes
     * 
     * @return whether the two cards are equal
     */
    @Override
    public boolean equals(Object object) {
        try {
            Card comparisonCard = (Card) object;
            
            return
                this.name.equals(comparisonCard.getName()) &&
                this.suit.equals(comparisonCard.getSuit()) &&
                this.value == comparisonCard.getValue();

        } catch (Exception ClassCastException) {
            return false;
        }
        
    }

}

/*
 * ## The Card class

The card class must take in three values as parameters: _name_, _suit_ and _value_</br>
_name_ will be the name of the card (for playing cards for example Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King)</br>
_suit_ will be the suit of a card (for a playing card for example Hearts, Spades, Diamonds, Clubs)</br>
_value_ will be a number value that represents if a card is worth more or less than another. This should be represented by a whole number.</br>

It Should also have getters for its name, value, and suit (no setters)</br>
A toString() method that will return its full form, e.g. "Queen of Diamonds" if the _name_ is "Queen" and the _suit_ is "Diamonds"</br>
An equals() method which checks if the two cards have the same name, suit and value.</br>

 */
import java.util.ArrayList;
import java.util.Random;
public class Bot {

    private Player player;
    private Random mind;

    /**
     * keeps a given double between a minimum and a maximum
     * 
     * @param a the number
     * @param m0 the minimum
     * @param m1 the maximum
     * @return 
     */
    private static int clamp(int a, int m0, int m1) {
        return Math.min( Math.max(a, m0), m1);
    }

    /**
     * keeps a given double between a minimum and a maximum
     * 
     * @param a the number
     * @param m0 the minimum
     * @param m1 the maximum
     * @return 
     */
    private static double clamp(double a, double m0, double m1) {
        return Math.min( Math.max(a, m0), m1);
    }

    /**
     * creates a new bot object for a player
     * this is a bot that plays for a player, as the player is pre-existing.
     * 
     * @param player
     */
    public Bot(Player player) {
        this.player = player;
        this.mind = new Random();
    }

    /**
     * creates a new bot player.
     * this is a complete bot, because it is created from a name, age and hand
     * 
     * @param name
     * @param age
     * @param hand
     */
    @Deprecated
    public Bot(String name, int age, Card[] hand) {
        this.player = new Player(name, age, hand);
        this.mind = new Random();
    }

    /**
     * gets the biggest card in the players hand
     * 
     * @param opponentCard
     * @return the biggest card
     */
    public Card biggestCard() {

        Card[] hand = this.player.getHand();
        
        Card biggestCard = hand[0];

        for (int i = 0; i < hand.length; i++) {
            if (
                biggestCard == null ||
                hand[i].getValue() > biggestCard.getValue()
            ) {
                biggestCard = hand[i];
            }
        }

        return biggestCard;
    }

    /**
     * gets the biggest card in the hand that is smaller than or equal to a provided value
     * 
     * @param maximumValue the maximum value allowed
     * @return the biggest card that is less than or equal to a value
     */
    public Card biggestCard(int maximumValue) {

        Card[] hand = this.player.getHand();
        
        Card biggestCard = hand[0];

        for (int i = 0; i < hand.length; i++) {
            if (
                hand[i].getValue() <= maximumValue && 
               (biggestCard == null ||
                hand[i].getValue() > biggestCard.getValue())
            ) {
                biggestCard = hand[i];
            }
        }

        return biggestCard;
    }

    /**
     * gets the smallest card in the players hand
     * 
     * @return the smallest card in the players hand
     */
    public Card smallestCard() {
        Card[] hand = this.player.getHand();
        
        Card smallestCard = null;

        for (int i = 0; i < hand.length; i++) {
            if (
                smallestCard == null ||
                hand[i].getValue() < smallestCard.getValue()
            ) {
                smallestCard = hand[i];
            }
        }

        return smallestCard;
    }
    /**
     * gets the smallest card in the players hand with a given minimum
     * if the minimum size is not met then the method returns null
     * 
     * @param minimumSize the minimum size the card must be
     * @return the smallest card that is greater than or equal to a minimum
     */
    public Card smallestCard(int minimumSize) {
        Card[] hand = this.player.getHand();
        
        Card smallestCard = null;

        for (int i = 0; i < hand.length; i++) {
            if (
                hand[i].getValue() >= minimumSize && 
                (smallestCard == null ||
                hand[i].getValue() < smallestCard.getValue())
            ) {
                smallestCard = hand[i];
            }
        }

        return smallestCard;
    }

    /**
     * gets the next card that the bot will decide to play based on the information given
     * 
     * @param opponentCard is the card that the opponent played
     * @return if the bot has a valid card returns a card object, else null
     */
    public Card nextMove(Card opponentCard) {
        Card biggestCard = this.biggestCard();
        if (biggestCard.getValue() > opponentCard.getValue()) {
            return biggestCard;
        }
        return null;
    }

    /**
     * a simple math based ai used to play the president game mode
     * 
     * @param mustPlay whether the player is forced to choose a card
     * @param opponentCard is the card the player has to beat
     * @param players is the arraylist of players that the bot is playing against
     * 
     * @return the next move when playing by president rules
     */
    public Card nextMovePresident(boolean mustPlay, Card opponentCard, ArrayList<Player> players) {

        if (mustPlay || players.size() == 0) { return this.smallestCard(opponentCard.getValue()); }
        
        int smallestHandSize = 52;

        for (int i = 0; i < players.size(); i++) {
            Player thisGuy = players.get(i);
            smallestHandSize = Math.min(smallestHandSize, thisGuy.size());
        }
        
        Card mySmallestCard = this.smallestCard();
        Card myBiggestCard = this.biggestCard();


        // desmos: https://www.desmos.com/calculator/xnj0fziagc
        // alpha = 1 - otherNumber^2 / self^2
        double alpha = 1 - clamp(Math.pow(smallestHandSize - 1, 2) / Math.pow(this.player.size(),2),0,1);
        
        // using linear interpolation between the smallest card size and the biggest card size with a [0.0, 1.0] alpha
        int lerpedValue = (int) (mySmallestCard.getValue() * (1 - alpha) + myBiggestCard.getValue() * alpha);
        // System.out.println(this.player.getName() + " is thinking.. " + lerpedValue + " is the smallest card they are willing to play, which translates to " + this.smallestCard(Math.max(lerpedValue, opponentCard.getValue())) + " value " + (this.smallestCard(Math.max(lerpedValue, opponentCard.getValue())) != null ? this.smallestCard(Math.max(lerpedValue, opponentCard.getValue())).getValue() : "null"));
        
        return this.smallestCard(Math.max(lerpedValue, opponentCard.getValue()));

        
    }

    /**
     * gets the next move if no information is given
     * 
     * @return the largest card in the player's hand 
     */
    public Card nextMove() {
        return this.biggestCard();
    }
    // public Card nextMove(Player[] opponents, Card opponentCard) {}
}
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
     * 
     * @param opponentCard
     * @return
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
     * gets the biggest card in the deck that is smaller than a provided value
     * @param minimumValue
     * @return
     */
    public Card biggestCard(int minimumValue) {

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
     * gets the smallest card in the player's hand
     * 
     * @return the smallest card in the player's hand
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
     * @return
     */
    public Card smallestCard(int minimumSize) {
        Card[] hand = this.player.getHand();
        
        Card smallestCard = null;

        for (int i = 0; i < hand.length; i++) {
            if (
                hand[i].getValue() > minimumSize && 
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
     * 
     * @return
     */
    public Card nextMovePresident(boolean mustPlay, Card opponentCard, ArrayList<Player> players) {

        if (players.size() == 0) { System.out.println("Warning: Called nextMove while no players are in-game"); return this.smallestCard(opponentCard.getValue()); }
        
        int totalCardsInGame = 0;
        int smallestHandSize = 52;

        for (int i = 0; i < players.size(); i++) {
            Player thisGuy = players.get(i);
            totalCardsInGame += thisGuy.size();
            smallestHandSize = (int) Math.min(smallestHandSize, thisGuy.size());
        }
        int averageHandSize = totalCardsInGame / players.size();
        
        Card mySmallestCard = this.smallestCard();
        Card myBiggestCard = this.biggestCard();

        double myHandSizeSquared = Math.pow(smallestHandSize,2);
        
        // alpha = otherNumber^2 / self^2
        // desmos: https://www.desmos.com/calculator/dyyb1taghb
        if (mustPlay || clamp(Math.pow(smallestHandSize,2) / myHandSizeSquared, 0, 1) > this.mind.nextDouble()) {
            // using linear interpolation between the smallest card size and the biggest card size with a [0.0, 1.0] alpha

            double alpha = clamp(Math.pow(averageHandSize, 2) / myHandSizeSquared,0,1);
            int lerpedValue = (int) (mySmallestCard.getValue() * alpha + myBiggestCard.getValue() * (1 - alpha));
            


            return this.smallestCard(Math.max(lerpedValue, opponentCard.getValue()));
        }

        return null;
        

        
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
public class Bot {

    private Player player;

    /**
     * creates a new bot object for a player
     * this is a bot that plays for a player, as the player is pre-existing.
     * 
     * @param player
     */
    public Bot(Player player) {
        this.player = player;
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
    }

    /**
     * 
     * @param opponentCard
     * @return
     */
    public Card biggestCard() {

        Card[] hand = this.player.getHand();
        
        Card biggestCard = hand[1];

        for (int i = 1; i < hand.length; i++) {
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
     * gets the next move if no information is given
     * 
     * @return the largest card in the player's hand 
     */
    public Card nextMove() {
        return this.biggestCard();
    }
    // public Card nextMove(Player[] opponents, Card opponentCard) {}
}
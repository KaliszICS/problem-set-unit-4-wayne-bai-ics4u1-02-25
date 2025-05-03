import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class that runs games using ArrayLists of Player objects.
 * 
 * @author Wayne Bai
 * @version 1.0
 */
public class Game {

    private ArrayList<Player> players;

    public Game(Player[] players) {
        this.players = new ArrayList<Player>(Arrays.asList(players));
    }

    /**
     * starts a game of president
     */
    public void president() {
        
        Deck deck = new Deck();
        DiscardPile discardPile = new DiscardPile();

        for (int i = 0; i < this.players.size(); i++) {
            Player thisPlayer = this.players.get(i);
            if (thisPlayer.getBot() == null) {
                System.out.println("Could not start game because " + thisPlayer.getName() + " doesn't have a bot object to play");
            }
        }

        deck.shuffle();
        int cardsPerPerson = (deck.size() / this.players.size());
        
        System.out.println("Each player gets " + cardsPerPerson + " cards.");

        for (int i = 1; i <= cardsPerPerson; i++) {
            for (int playerNumber = 0; playerNumber < this.players.size(); playerNumber++) {

                this.players.get(playerNumber).draw(deck);
            }
        }

        final Card ZERO_CARD = new Card("null", "null", -1);

        

        @SuppressWarnings("unchecked")
        ArrayList<Player> playersInGame = (ArrayList<Player>) this.players.clone();
        ArrayList<Player> winOrder = new ArrayList<Player>();

        int round = 0;
        int indexOfStackOwner = 0;
        
        while (playersInGame.size() > 1) {
            
            round += 1;
            System.out.println("Round " + round);
            
            // these two variables are used to see when a round ends
            // a round ends when all players dont play a card
            Player stackOwner = null;
            Player stackInitialOwner = null;
            Card currentTopCard = ZERO_CARD;

            boolean moveWasMade = false;
            
            
            do {
                moveWasMade = false;
                stackInitialOwner = stackOwner;
                for (int i = indexOfStackOwner; i < playersInGame.size(); i++) {
                    Player currentPlayer = playersInGame.get(i);
                    if (currentPlayer.equals(stackOwner)) { break; }
                    Card cardToPlay = currentPlayer.getBot().nextMovePresident(!moveWasMade, currentTopCard, playersInGame);

                    if (cardToPlay != null && cardToPlay.getClass().equals(Card.class) && cardToPlay.getValue() > currentTopCard.getValue()) {

                        moveWasMade = true;

                        currentPlayer.discardCard(cardToPlay, discardPile);
                        
                        stackOwner = currentPlayer;
                        indexOfStackOwner = i;
                        currentTopCard = cardToPlay;

                        
                        System.out.println(currentPlayer.getName() + " played the " + cardToPlay + " (" + cardToPlay.getValue() + ")! They now have " + currentPlayer.size() + " card" + (currentPlayer.size() != 1 ? "s" : "") + " remaining. ");
                    } else {
                        System.out.println(currentPlayer.getName() + " skipped their turn");
                    }
                    if (currentPlayer.size() == 0) {
                        playersInGame.remove(currentPlayer);
                        winOrder.add(currentPlayer);
                    }
                }
                for (int i = 0; i < Math.min(playersInGame.size(), indexOfStackOwner); i++) {
                    Player currentPlayer = playersInGame.get(i);
                    if (currentPlayer.equals(stackOwner)) { break; }
                    Card cardToPlay = currentPlayer.getBot().nextMovePresident(!moveWasMade, currentTopCard, playersInGame);

                    if (cardToPlay != null && cardToPlay.getClass().equals(Card.class) && cardToPlay.getValue() > currentTopCard.getValue()) {

                        moveWasMade = true;

                        currentPlayer.discardCard(cardToPlay, discardPile);
                        
                        stackOwner = currentPlayer;
                        indexOfStackOwner = i;
                        currentTopCard = cardToPlay;

                        
                        System.out.println(currentPlayer.getName() + " played the " + cardToPlay + "! They now have " + currentPlayer.size() + " card" + (currentPlayer.size() != 1 ? "s" : "") + " remaining. ");
                    } else {
                        System.out.println(currentPlayer.getName() + " skipped their turn");
                    }
                    if (currentPlayer.size() == 0) {
                        playersInGame.remove(currentPlayer);
                        winOrder.add(currentPlayer);
                    }
                }

            } while (stackOwner != stackInitialOwner);
        }
        System.out.println("Standings: ");
        for (int i = 0; i < winOrder.size(); i++) {
            System.out.println((i + 1) + ". " + winOrder.get(i));
        }
        System.out.println("DNF: ");
        for (int i = 0; i < playersInGame.size(); i++) {
            playersInGame.get(i).clearHand(deck);
            System.out.println((winOrder.size() + 1 + i) + ". " + playersInGame.get(i));
        }

        System.out.println(deck.size() + " cards remaining in the deck: " + deck.toString());
        deck.reshuffle(discardPile.removeAll());
        deck.sort();
        System.out.println("inserted the discard pile: " + deck.size());
        System.out.println(deck);
        
    }

}
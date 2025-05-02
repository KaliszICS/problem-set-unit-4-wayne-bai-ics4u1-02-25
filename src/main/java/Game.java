import java.util.ArrayList;
import java.util.Arrays;
public class Game {

    private ArrayList<Player> players;

    public Game(Player[] players) {
        this.players = new ArrayList<Player>(Arrays.asList(players));
    }

    /**
     * starts a poker game
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

        final Card stackBeginCard = new Card("null", "null", -1);

        

        @SuppressWarnings("unchecked")
        ArrayList<Player> playersInGame = (ArrayList<Player>) this.players.clone();
        ArrayList<Player> winOrder = new ArrayList<Player>();

        int round = 0;
        while (playersInGame.size() > 0) {

            round += 1;
            System.out.println("Round " + round);

            Player stackOwner = null;
            Player stackInitialOwner = null;
            int stackBegin = 0;

            Card currentTopCard = stackBeginCard;
            
            
            
            do {
                boolean moveWasMade = false;
                stackInitialOwner = stackOwner;
                for (int i = stackBegin; i < playersInGame.size(); i++) {
                    Player currentPlayer = playersInGame.get(i);
                    if (currentPlayer.equals(stackOwner)) { continue; }
                    Card cardToPlay = currentPlayer.getBot().nextMovePresident(!moveWasMade, currentTopCard, playersInGame);

                    if (cardToPlay != null && cardToPlay.getClass().equals(Card.class) && cardToPlay.getValue() > currentTopCard.getValue()) {

                        moveWasMade = true;

                        currentPlayer.discardCard(cardToPlay, discardPile);
                        
                        stackOwner = currentPlayer;
                        stackBegin = i;
                        currentTopCard = cardToPlay;

                        
                        System.out.println(currentPlayer.getName() + " played the " + cardToPlay + "! They now have " + currentPlayer.size() + " card" + (currentPlayer.size() != 1 ? "s" : "") + " remaining. ");
                    }
                    if (currentPlayer.size() == 0) {
                        playersInGame.remove(currentPlayer);
                        winOrder.add(currentPlayer);
                    }
                }
                for (int i = 0; i < Math.min(playersInGame.size(), stackBegin); i++) {
                    Player currentPlayer = playersInGame.get(i);
                    Card cardToPlay = currentPlayer.getBot().nextMovePresident(!moveWasMade, currentTopCard, playersInGame);

                    if (cardToPlay != null && cardToPlay.getClass().equals(Card.class) && cardToPlay.getValue() > currentTopCard.getValue()) {

                        moveWasMade = true;

                        currentPlayer.discardCard(cardToPlay, discardPile);
                        
                        stackOwner = currentPlayer;
                        stackBegin = i;
                        currentTopCard = cardToPlay;

                        
                        System.out.println(currentPlayer.getName() + " played the " + cardToPlay + "! They now have " + currentPlayer.size() + " card" + (currentPlayer.size() != 1 ? "s" : "") + " remaining. ");
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
        
    }

}

import java.util.Scanner;

public class TestCases2 {



    public static void game() {

        Scanner recv = new Scanner(System.in);

        Deck gameDeck = new Deck();
        DiscardPile discardPile = new DiscardPile();

        String name1 = recv.nextLine();
        String name2 = recv.nextLine();

        recv.close();

        Player p1 = new Player(name1, 18);
        Player p2 = new Player(name2, 18);

        Bot p1bot = new Bot(p1);
        Bot p2bot = new Bot(p2);

        for (int round = 1; round < 5; round++) {
            System.out.println("Round " + round);
            gameDeck.shuffle();
            System.out.println("Shuffling.. ");

            for (int card = 1; card < 5; card++) {
                p1.draw(gameDeck);
                p2.draw(gameDeck);
            }

            System.out.println(p1);
            System.out.println(p2);

            Card p1move = p1bot.nextMove();
            Card p2move = p2bot.nextMove();

            p1.discardCard(p1move, discardPile);
            p2.discardCard(p2move, discardPile);

            int p1valueOf = p1move.getValue();
            int p2valueOf = p2move.getValue();

            p1.addPoints(p1valueOf > p2valueOf ? 1 : 0);
            p2.addPoints(p2valueOf > p1valueOf ? 1 : 0);

            System.out.println(p1.getName() + " move: " + p1move);
            System.out.println(p2.getName() + " move: " + p2move);

            System.out.println((p1valueOf > p2valueOf ? p1.getName() : p2.getName()) + " won the round" );

        }
        p1.setGameResult(p1.getPoints() > p2.getPoints());
        p2.setGameResult(p2.getPoints() > p1.getPoints());
        System.out.println("Discard Pile (" + discardPile.size() + ") " + discardPile);

        gameDeck.reshuffle(discardPile.removeAll());

        System.out.println(p1 + " - w/l: " + p1.getWLRatio());
        System.out.println(p2 + " - w/l: " + p2.getWLRatio());

        System.out.println(gameDeck.size());

        System.out.println((p1.getWLRatio() > p2.getWLRatio() ? p1.getName() : p2.getName()) + " wins!");

    }

    public static void main(String[] args) {
        Deck newDeck = new Deck(new Card[] {

                new Card("0", "A", 1),
                new Card("7", "B", 7),
                null,
                null,
                new Card("1000", "C", 2),
        });

        System.out.println(new Card("null", "q", 2));
        System.out.println(newDeck);
        System.out.println(
                new Card("A", "B", 3).equals(new Card("A", "B", 3)));

        Deck deck2 = new Deck();
        Deck deck3 = new Deck();

        DiscardPile deck2pile = new DiscardPile();
        DiscardPile deck3pile = new DiscardPile(new Card[] {});

        try {
            deck2.newShuffler(0);
        } catch (Exception e) {
            System.out.println("Couldn't set shuffle seed to 0: " + e.getMessage());
        }
        try {
            deck3.newShuffler(0);
        } catch (Exception e) {
            System.out.println("Couldn't set shuffle seed to 0: " + e.getMessage());
        }
        try {
            deck3.newShuffler(1);
        } catch (Exception e) {
            System.out.println("(expected result) Couldn't set shuffle seed to 1: " + e.getMessage());
        }
        deck2.shuffle();
        deck3.shuffle();

        for (int i = 0; i < 10; i++) {
            while (true) {
                Card from2 = deck2.draw();
                Card from3 = deck3.draw();

                deck2pile.addCard(from2);
                deck3pile.addCard(from3);
                if (from2 == null || from3 == null) {
                    break;
                } else {
                    Assertion.assertEquals(from2, from3);
                }
            }
            deck3.reshuffle(deck2pile.removeAll());
            deck2.reshuffle(deck3pile.removeAll());
            System.out.println(i + " passed");
        }

        for (int i = 0; i < 10; i++) {
            deck3.shuffle();

            while (true) {
                Card from2 = deck2.draw();
                Card from3 = deck3.draw();

                deck2pile.addCard(from2);
                deck3pile.addCard(from3);

                if (from2 == null || from3 == null) {
                    break;
                } else {
                    if (from2.equals(from3)) {
                        System.out.print("f");
                    } else {
                        System.out.print("t");
                    }
                }
            }

            deck3.reshuffle(deck2pile.removeAll());
            deck2.reshuffle(deck3pile.removeAll());
            System.out.println(" " + i + " passed");
        }

        for (int i = 0; i < 10; i++) {
            deck3.sort();
            deck2.sort();

            while (true) {
                Card from2 = deck2.draw();
                Card from3 = deck3.draw();

                deck2pile.addCard(from2);
                deck3pile.addCard(from3);

                if (from2 == null || from3 == null) {
                    break;
                } else {
                    Assertion.assertEquals(from2, from3);
                }
            }

            deck3.reshuffle(deck2pile.removeAll());
            deck2.reshuffle(deck3pile.removeAll());
            System.out.println(i + " passed");
        }

        System.out.println("Bulk declared test");

        Card thisCard = new Card("name", "suit", 1);
        Assertion.assertEquals(thisCard.getName(), "name");
        Assertion.assertEquals(thisCard.getSuit(), "suit");
        Assertion.assertEquals(thisCard.getValue(), 1);

        Card card1 = new Card("Ace", "Hearts", 14);
        Card card2 = new Card("Ace", "Hearts", 14);
        Card card3 = new Card("King", "Hearts", 13);

        Assertion.assertEquals("Ace", card1.getName());
        Assertion.assertEquals("Hearts", card1.getSuit());
        Assertion.assertEquals(14, card1.getValue());
        Assertion.assertEquals("Ace of Hearts", card1.toString());
        Assertion.assertTrue(card1.equals(card2), "card1 should equal card2");
        Assertion.assertFalse(card1.equals(card3), "card1 should not equal card3");

        Deck fullDeck = new Deck(); // Should generate 52 cards
        Assertion.assertEquals(52, fullDeck.size());
        Card topCard = fullDeck.draw();
        Assertion.assertEquals(51, fullDeck.size());

        Card[] sampleCards = {
                new Card("Ace", "Spades", 14),
                new Card("2", "Spades", 2)
        };
        Deck smallDeck = new Deck(sampleCards);
        Assertion.assertEquals(2, smallDeck.size());

        Deck deck = new Deck();
        deck.shuffle(); // Just test that it runs, visual inspection may be needed
        deck.addCard(new Card("Joker", "None", 0));
        Assertion.assertEquals(53, deck.size());

        Card[] reshuffleCards = {
                new Card("Joker", "Black", 0),
                new Card("Joker", "Red", 0)
        };
        deck.reshuffle(reshuffleCards);
        Assertion.assertEquals(55, deck.size());

        DiscardPile pile = new DiscardPile();
        Card discardCard = new Card("9", "Diamonds", 9);

        pile.addCard(discardCard);
        Assertion.assertEquals(1, pile.size());

        Card removed = pile.removeCard(discardCard);
        Assertion.assertEquals(discardCard, removed);
        Assertion.assertEquals(0, pile.size());

        Card[] empty = pile.removeAll();
        Assertion.assertEquals(0, empty.length);

        Player p1 = new Player("Alice", 20);
        Assertion.assertEquals("Alice", p1.getName());
        Assertion.assertEquals(20, p1.getAge());
        Assertion.assertEquals(0, p1.size());

        Deck testDeck = new Deck();
        p1.draw(testDeck);
        Assertion.assertEquals(1, p1.size());

        Card card = p1.getHand()[0];
        DiscardPile discard = new DiscardPile();

        boolean discarded = p1.discardCard(card, discard);
        Assertion.assertTrue(discarded, "Card should be discarded");
        Assertion.assertEquals(0, p1.size());
        Assertion.assertEquals(1, discard.size());

        p1.draw(testDeck);
        Card returnCard = p1.getHand()[0];
        boolean returned = p1.returnCard(returnCard, testDeck);
        Assertion.assertTrue(returned, "Card should be returned");
        Assertion.assertEquals(0, p1.size());

        Player p2 = new Player("Bob", 25, new Card[] {
                new Card("King", "Spades", 13),
                new Card("7", "Clubs", 7)
        });
        String out = p2.toString();
        Assertion.assertTrue(out.contains("Bob") && out.contains("25"), "Player string should include name and age");
        Assertion.assertTrue(out.contains("King of Spades") && out.contains("7 of Clubs"),
                "Player string should include cards");

        // override dedication
        Card cardA = new Card("Queen", "Diamonds", 12);
        Card cardB = new Card("Queen", "Diamonds", 12);
        Card cardC = new Card("Queen", "Diamonds", 10); // different value
        Card cardD = new Card("Jack", "Diamonds", 12); // different name
        Card cardE = new Card("Queen", "Hearts", 12); // different suit

        // toString
        Assertion.assertEquals("Queen of Diamonds", cardA.toString());

        // equals
        Assertion.assertTrue(cardA.equals(cardB), "Cards with same name, suit, value should be equal");
        Assertion.assertFalse(cardA.equals(cardC), "Different value should not be equal");
        Assertion.assertFalse(cardA.equals(cardD), "Different name should not be equal");
        Assertion.assertFalse(cardA.equals(cardE), "Different suit should not be equal");

        Deck deck9 = new Deck(new Card[] {
                new Card("2", "Hearts", 2),
                new Card("3", "Spades", 3)
        });

        Assertion.assertEquals("2 of Hearts, 3 of Spades", deck9.toString()); // if you implemented it this way

        DiscardPile pileq = new DiscardPile();
        pileq.addCard(new Card("Ace", "Spades", 14));
        pileq.addCard(new Card("2", "Clubs", 2));

        // toString
        Assertion.assertEquals("Ace of Spades, 2 of Clubs", pileq.toString());

        Card[] hand = new Card[] {
                new Card("King", "Clubs", 13),
                new Card("10", "Hearts", 10)
        };

        Player player = new Player("Jane", 30, hand);

        // toString
        String output = player.toString();
        Assertion.assertTrue(output.contains("Jane") && output.contains("30"),
                "Player toString should include name and age");
        Assertion.assertTrue(output.contains("King of Clubs") && output.contains("10 of Hearts"),
                "Player toString should include hand");

        // equals – not specified in instructions; skip unless implemented

        // equals – not specified or required in assignment; skip unless added manually

        System.out.println("Completed");

        game();
    }
}

/**
 * Create a simple "High Card" game that:
 * 1. Creates a deck and shuffles it
 * 2. Creates two players with names provided by user input
 * 3. Deals 5 cards to each player
 * 4. Each round, both players play their highest value card
 * 5. The player with the higher value card wins the round and gets a point
 * 6. After 5 rounds, display the winner
 */

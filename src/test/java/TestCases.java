public class TestCases {

    public static void main(String[] args) {

        Card card1 = new Card("Ace", "Spades", 14);
        Card card2 = new Card("Ace", "Spades", 14);
        Card card3 = new Card("Ace", "Spades", 13); // different value
        Card card4 = new Card("2", "Spades", 14); // different name
        Card card5 = new Card("Ace", "Hearts", 14); // different suit

        // toString
        Assertion.assertEquals("Ace of Spades", card1.toString());

        // equals
        Assertion.assertTrue(card1.equals(card2), "Exact duplicate card should equal itself.");
        Assertion.assertFalse(card1.equals(card3), "Card with different value should not be equal.");
        Assertion.assertFalse(card1.equals(card4), "Card with different name should not be equal.");
        Assertion.assertFalse(card1.equals(card5), "Card with different suit should not be equal.");

        Deck emptyDeck = new Deck(new Card[0]);
        Assertion.assertEquals(0, emptyDeck.size());
        Assertion.assertEquals(null, emptyDeck.draw());

        Card[] orderedCards = new Card[] {
                new Card("1", "A", 1),
                new Card("2", "A", 2),
                new Card("3", "A", 3),
                new Card("4", "A", 4)
        };
        Deck shuffleTest = new Deck(orderedCards.clone());
        shuffleTest.shuffle();

        boolean isShuffled = false;
        for (int i = 0; i < orderedCards.length; i++) {
            if (!shuffleTest.draw().equals(orderedCards[i])) {
                isShuffled = true;
                break;
            }
        }
        Assertion.assertTrue(isShuffled, "Deck should shuffle to a different order.");

        DiscardPile pile = new DiscardPile();

        // Add null
        pile.addCard(null); // Assume your method skips nulls
        Assertion.assertEquals(0, pile.size());

        // Add valid
        Card ace = new Card("Ace", "Hearts", 14);
        pile.addCard(ace);
        Assertion.assertEquals(1, pile.size());
        Assertion.assertEquals("Ace of Hearts", pile.toString());

        // Remove existing
        Card removed = pile.removeCard(ace);
        Assertion.assertEquals(ace, removed);
        Assertion.assertEquals(0, pile.size());

        // Remove nonexistent
        Card fake = new Card("Fake", "Clubs", 0);
        Assertion.assertEquals(null, pile.removeCard(fake));

        DiscardPile bigPile = new DiscardPile();
        Card[] all = new Card[10];
        for (int i = 0; i < 10; i++) {
            all[i] = new Card("Test" + i, "Suit", i);
            bigPile.addCard(all[i]);
        }

        Assertion.assertEquals(10, bigPile.size());

        Card[] removedAll = bigPile.removeAll();
        Assertion.assertEquals(0, bigPile.size());
        Assertion.assertEquals(10, removedAll.length);

        Deck deck = new Deck(new Card[] {
                new Card("7", "Hearts", 7)
        });

        DiscardPile pile2 = new DiscardPile();
        Player player = new Player("Bob", 40);

        player.draw(deck); // should draw the 7 of Hearts
        Assertion.assertEquals(1, player.size());

        Card card = player.getHand()[0];

        // Discard
        Assertion.assertTrue(player.discardCard(card, pile2), "Discard should succeed");
        Assertion.assertEquals(0, player.size());
        Assertion.assertEquals(1, pile2.size());

        // Return (add to deck)
        pile2.removeCard(card);
        deck.addCard(card);
        Assertion.assertEquals(card, deck.draw());

        deck.addCard(card);
        
        player.draw(deck); // re-draw it
        Assertion.assertTrue(player.returnCard(card, deck), "Return should succeed");
        Assertion.assertEquals(0, player.size());

        Player p2 = new Player("Ghost", 999);
        Card ghostCard = new Card("Invisible", "None", 0);
        Assertion.assertEquals("Invisible of None", ghostCard.toString());
        Assertion.assertFalse(p2.discardCard(ghostCard, new DiscardPile()), "Should fail to discard from empty hand");
        Assertion.assertFalse(p2.returnCard(ghostCard, new Deck()), "Should fail to return non-existent card");

        Card[] cards = new Card[] {
                new Card("4", "Spades", 4),
                new Card("5", "Spades", 5)
        };
        Player p3 = new Player("Charlie", 18, cards);
        String info = p3.toString();
        Assertion.assertTrue(info.contains("Charlie"), "Name should be in toString");
        Assertion.assertTrue(info.contains("18"), "Age should be in toString");
        Assertion.assertTrue(info.contains("4 of Spades"), "Hand should be in toString");

    }

}

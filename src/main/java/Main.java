public class Main {

    public static void main(String[] arguments) {
        Player[] friends = new Player[] {
                new Player("Wayne Bai", 17),
                // new Player("Mr. Kalisz", 24),
                new Player("Vincent Zeng", 17),
                // new Player("David Zeng", 18),
                // new Player("Crystal Li", 0),
                // new Player("Mr. Marr", 0),
                // new Player("Mr. Ng", 0),
                // new Player("Mr. Cheung", 24),
                // new Player("Andy Wei", 18),
                // new Player("Nicole Ye", 18),
                // new Player("Zhuo Lin Jiang", 18),
                // new Player("Jonathan Zhao", 18),
                // new Player("Ms. Kesner", 0),
                // new Player("Ms. Kim", 0),
                new Player("Radin Ajorlou", 18),
                // new Player("Mr. Stermole", 0),
                // new Player("Ethan Leung", 0),
                new Player("Franklin Bai", 20),
                // new Player("Ms. Scott", 0),
                new Player("Syed Muhammed Ali Hamza", 18),
                // new Player("Franklin Zhu", 0),
                new Player("Roy Edwin", 18),
                // new Player("Madilyn Tomlinson", 0),
                // new Player("Olivier Tan", 0),
                // new Player("Lily You", 0),
                // new Player("Marcus Wong", 17),
        };

        // set a bot for everyone
        for (int i = 0; i < friends.length; i++) {
            try {
                friends[i].setBot(new Bot(friends[i]));
            } catch (Exception e) {
                System.out.println(friends[i].getName() + " has a bot already");
            }
        }

        Game dataModel = new Game(friends);

        dataModel.president();
    }

}

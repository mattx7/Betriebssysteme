package bs.rps;


/**
 * Created by Neak on 12.11.2016.
 *
 */
class Judge extends Thread {

    private GameTable table;

    Judge(GameTable table) {
        this.table = table;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            //                notifyAll();
            //                wait();

            Player winner = payoff(); // Runde auswerten
            System.out.println(String.format("[ %s : %s ] Player %s wins", table.getPlayer1().getHand(), table.getPlayer2().getHand(), winner));
            // refresh hand

        }

        System.out.println("Judge has finished");
    }

    /**
     * @return Winning Player or null if draw
     */
    private Player payoff() {
        Player player1 = table.getPlayer1();
        Player player2 = table.getPlayer2();

        if (player1.getHand().equals(player2.getHand())) return null;

        if ((player1.getHand().equals(Hand.Rock) & player2.getHand().equals(Hand.Scissors)) ||
                (player1.getHand().equals(Hand.Scissors) & player2.getHand().equals(Hand.Paper)) ||
                (player1.getHand().equals(Hand.Paper) & player2.getHand().equals(Hand.Rock)))
            return player1;
        else
            return player2;
    }

    public void setTable(GameTable table) {
        this.table = table;
    }
}

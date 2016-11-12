package bs.rps;


/**
 * Created by Neak on 12.11.2016.
 */
public class Judge extends Thread {

    private GameTable table;

    @Override
    public void run() {
        while (!isInterrupted()) {
            //                notifyAll();
            //                wait();

            payoff(); // Runde auswerten
            startNextRound();// refresh hand

        }

        System.out.println("Judge has finished");
    }

    /**
     * @return Winning Player or null if draw
     */
    public Player payoff() {
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

    public void startNextRound() {
        table.getPlayer1().changeHand();
        table.getPlayer2().changeHand();
        System.out.println(String.format("Hands have been shuffled: Nr.01 has %s, Nr.02 has %s", table.getPlayer1().getHand().toString(), table.getPlayer2().getHand().toString()));
    }

    public void setTable(GameTable table) {
        this.table = table;
    }
}

package bs.rps;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Neak on 12.11.2016.
 *
 */
class Judge extends Thread {
    private final Table table;
    private int roundCounter;
    private LinkedList<Thread> winners = new LinkedList<>();
    private List<Hand> winningHands;
    /**
     * Constuctor
     *
     * @param table Monitor
     * @param name  of the Thread
     */
    Judge(Table table, String name) {
        super(name);
        this.table = table;
        roundCounter = 0;
        winners = new LinkedList<>();
        winningHands = new LinkedList<>();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            List<Hand> hands = table.getHands();
            Hand winner = payoff(hands, table.getPlayers()); // Runde auswerten
            roundCounter++;
            System.out.println(String.format("[%s:%s] %s wins with %s ", hands.get(0), hands.get(1), ((winners.getLast() == null) ? null : winners.getLast().getName()), winner));
            table.cleanTable();
        }
    }

    void printScore() {
        int th1Wins = 0;
        int th2Wins = 0;
        int draws = 0;

        for (Thread thread : winners) {
            if (thread == null) {
                draws++;
            } else if (thread.getName().equals("Hand1")) {
                th1Wins++;
            } else if (thread.getName().equals("Hand2")) {
                th2Wins++;
            }
        }

        System.out.println(" ====== SCORE ======");
        System.out.println("Played Rounds: " + roundCounter);
        System.out.println("Draws: " + draws);
        System.out.println("Hand1: " + th1Wins);
        System.out.println("Hand2: " + th2Wins);
        System.out.println(" ===================");
    }
    /**
     * @return Winning Hand or null if draw
     */
    private Hand payoff(List<Hand> hands) {
        Hand hand1 = hands.get(0);
        Hand hand2 = hands.get(1);

        if (hand1.equals(hand2)) return null;

        if ((hand1.equals(Hand.Rock) & hand2.equals(Hand.Scissors)) ||
                (hand1.equals(Hand.Scissors) & hand2.equals(Hand.Paper)) ||
                (hand1.equals(Hand.Paper) & hand2.equals(Hand.Rock)))
            return hand1;
        else
            return hand2;
    }
}

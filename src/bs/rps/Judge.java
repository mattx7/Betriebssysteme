package bs.rps;

import java.util.List;

/**
 * Created by Neak on 12.11.2016.
 *
 */
class Judge extends Thread {
    private final Table table;
    private List<Hand> hands;

    /**
     * Constuctor
     *
     * @param table Monitor
     * @param name  of the Thread
     */
    Judge(Table table, String name) {
        super(name);
        this.table = table;

    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            Hand winner = payoff(); // Runde auswerten
            System.out.println(String.format("[ %s : %s ] Player %s wins", hands.get(0), hands.get(1), winner));
            table.cleanTable();
        }
    }

    /**
     * @return Winning Hand or null if draw
     */
    private Hand payoff() {
        hands = table.getHands();
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

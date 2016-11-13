package bs.rps;

/**
 * Created by Neak on 12.11.2016.
 *
 */
class Judge extends Thread {

    private Table table;

    Judge(Table table) {
        this.table = table;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            System.out.println("Judge started");
            Hand winner = payoff(); // Runde auswerten
            System.out.println(String.format("[ %s : %s ] Player %s wins", table.getHands().get(0), table.getHands().get(0), winner));
            table.cleanTable();

        }
        System.out.println("Judge has finished");
    }

    /**
     * @return Winning Player or null if draw
     */
    private Hand payoff() {
        Hand hand1 = table.getHands().get(0);
        Hand hand2 = table.getHands().get(1);

        if (hand1.equals(hand2)) return null;

        if ((hand1.equals(Hand.Rock) & hand2.equals(Hand.Scissors)) ||
                (hand1.equals(Hand.Scissors) & hand2.equals(Hand.Paper)) ||
                (hand1.equals(Hand.Paper) & hand2.equals(Hand.Rock)))
            return hand1;
        else
            return hand2;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}

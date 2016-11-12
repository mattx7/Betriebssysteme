package bs.rps;

/**
 * Created by Neak on 12.11.2016.
 */
class Player extends Thread {

    private Hand hand;
    private int nr;
    private GameTable table;

    Player(int nr, GameTable table) {
        this.nr = nr;
        this.table = table;
        this.hand = Hand.getRandom();
    }

    public void changeHand() {
        hand = Hand.getRandom();
        System.out.println(nr + " hands changed");
    }

    Hand getHand() {
        return hand;
    }

    public void setTable(GameTable table) {
        this.table = table;
    }

    @Override
    public void run() {

        // TODO warten auf auswertung
    }

    @Override
    public String toString() {
        return nr + "";
    }
}
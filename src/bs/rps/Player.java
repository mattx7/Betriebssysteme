package bs.rps;

/**
 * Created by Neak on 12.11.2016.
 */
public class Player extends Thread {

    private Hand hand;
    private int nr;


    public Player(int nr) {
        this.nr = nr;
    }

    public void changeHand() {
        setHand(hand.getRandom());
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    @Override
    public String toString() {
        return String.format("Player No.%d ( has %s )", nr, hand);
    }
}
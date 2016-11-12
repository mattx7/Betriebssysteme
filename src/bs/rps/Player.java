package bs.rps;

/**
 * Created by Neak on 12.11.2016.
 */
public class Player extends Thread {

    private Hand hand;
    private int nr;
    private Judge judge;

    public Player(int nr, Judge judge, Hand hand) {
        this.nr = nr;
        this.judge = judge;
        this.hand = hand;
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
    public void run() { //hier ist bisher alles müll
        while (!isInterrupted()) {
            try {
                synchronized (judge) {
                    judge.wait();
                    sleep(500);
                }

            } catch (InterruptedException e) {
                interrupt();
            }
        }
        System.out.println(String.format("%s has finished.", this));
    }
    @Override
    public String toString() {
        return String.format("Player Nr.%d has %s and", nr, hand);
    }
}
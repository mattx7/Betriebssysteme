package bs.rps;

import java.util.Collection;

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
    public void run() {
        while (!isInterrupted()) {
            try {
                synchronized (judge) {
                    judge.wait();

                    Collection<Player> availablePlayers = judge.getAvailablePlayers();
                    if (!availablePlayers.contains(hand)) {
                        // consume
                        System.out.println(String.format("%s has %s", this, this.getHand().toString()));
                        sleep(500);

                        judge.notify();
                    } else {
                        System.out.println(String.format("%s has %s ", this, this.getHand().toString()));
                    }
                }

            } catch (InterruptedException e) {
                interrupt();
            }
        }

        System.out.println(String.format("%s has finished.", this));
    }

    @Override
    public String toString() {
        return String.format("Player No.%d ( has %s )", nr, hand);
    }
}
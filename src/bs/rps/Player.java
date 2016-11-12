package bs.rps;

import java.util.Collection;

/**
 * Created by Neak on 12.11.2016.
 */
public class Player extends Thread {

    private Hand hand;
    private int nr;
    private Judge judge;

    public Player(int nr, Judge judge) {
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
    public void run() {
        while (!isInterrupted()) {
            try {
                synchronized (judge) {
                    judge.wait();

                    Collection<Player> availablePlayer = judge.getAvailablePlayers();
                    if (!availablePlayer.contains(availablePlayer)) {
                        // consume
                        System.out.println(String.format("%s consumes ingredients and smokes a cigarette.", this));
                        sleep(500);

                        judge.notify();
                    } else {
                        System.out.println(String.format("%s is unable to use the available ingredients.", this));
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
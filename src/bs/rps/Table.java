package bs.rps;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neak on 12.11.2016.
 * <p>
 * MonitorClass
 */
class Table {
    private List<Hand> hands = new ArrayList<Hand>();

    synchronized void addHand(Hand hand) {
        awaitCleanTable();
        this.hands.add(hand);
        notifyAll();
    }

    synchronized void cleanTable() {
        awaitHands();
        hands.clear();
        notifyAll();
    }

    synchronized List<Hand> getHands() {
        awaitHands();
        return hands;
    }

    private synchronized void awaitHands() {
        while (hands.size() < 2) {
            try {
                System.out.println("awaitHands() waiting");
                wait();
            } catch (InterruptedException e) {
                System.out.println("awaitHands() interrupted");
            }
        }
    }

    private synchronized void awaitCleanTable() {
        while (hands.size() > 0) {
            try {
                System.out.println("awaitCleanTable() waiting");
                wait();
            } catch (InterruptedException e) {
                System.out.println("awaitCleanTable() interrupted");
            }

        }
    }
}

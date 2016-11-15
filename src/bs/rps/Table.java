package bs.rps;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Neak on 12.11.2016.
 * <p>
 * MonitorClass
 */
class Table {
    private List<Hand> hands = new ArrayList<Hand>();
    private List<Thread> player = new LinkedList<Thread>();

    /**
     * adds Hands to the table
     *
     * @param hand
     */
    synchronized void addHand(Hand hand) {
        awaitCleanTable();
        System.out.println(Thread.currentThread() + " " + hand + " addHand()");
        this.hands.add(hand);
        notifyAll();
        awaitOtherPlayer();

    }

    /**
     * Cleans the table for a new game
     */
    synchronized void cleanTable() {
        awaitHands();
        hands.clear();
        player.clear();
        System.out.println(Thread.currentThread() + " cleanTable()");
        notifyAll();
    }

    /**
     * @return List of Hands
     */
    synchronized List<Hand> getHands() {
        awaitHands();
        System.out.println(Thread.currentThread() + " getHand()");
        return hands;
    }

    /**
     * Semaphore
     */
    private synchronized void awaitHands() {
        while (hands.size() < 2 && player.size() < 2) {
            try {
                System.out.println(Thread.currentThread() + " await hands");
                wait();
            } catch (InterruptedException e) {
//                System.out.println(Thread.currentThread()+" awaitHands() interrupted");
            }
        }
        System.out.println(Thread.currentThread() + " is awake");
    }

    /**
     * Semaphore
     */
    private synchronized void awaitCleanTable() {
        while (hands.size() >= 2 && player.size() >= 2) {
            try {
                System.out.println(Thread.currentThread() + " await clean table");
                wait();
            } catch (InterruptedException e) {
//                System.out.println(Thread.currentThread()+" awaitCleanTable() interrupted");
            }
        }
        System.out.println(Thread.currentThread() + " is awake");
    }

    /**
     * Semaphore
     */
    private synchronized void awaitOtherPlayer() {
        player.add(Thread.currentThread());
        while (player.contains(Thread.currentThread())) {
            try {
                System.out.println(Thread.currentThread() + " await other player");
                wait();
            } catch (InterruptedException e) {
//                System.out.println(Thread.currentThread()+" awaitHands() interrupted");
            }
        }
        System.out.println(Thread.currentThread() + " is awake");
    }
}

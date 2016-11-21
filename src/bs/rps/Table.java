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
    private int playerOnTable = 2;

    /**
     * adds Hands to the table
     *
     * @param hand
     */
    synchronized void addHand(Hand hand) {
        awaitReadyForPlayer();
        awaitOtherPlayer();
        System.out.println(Thread.currentThread() + " " + hand + " addHand()");
        this.hands.add(hand);
        notifyAll();
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
     * Wait that Table has two Hands and two Player
     */
    private synchronized void awaitHands() {
        while (hands.size() < playerOnTable && player.size() < playerOnTable) {
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
     * Wait that Table is ready for Player
     */
    private synchronized void awaitReadyForPlayer() {
        while (hands.size() == playerOnTable && player.size() == playerOnTable) {
            try {
                System.out.println(Thread.currentThread() + " await clean table");
                wait();
            } catch (InterruptedException e) {
//                System.out.println(Thread.currentThread()+" awaitReadyForPlayer() interrupted");
            }
        }
        System.out.println(Thread.currentThread() + " is awake");
    }

    /**
     * Wait until new Round
     */
    private synchronized void awaitOtherPlayer() {
        while (player.contains(Thread.currentThread())) {
            try {
                System.out.println(Thread.currentThread() + " await other player");
                wait();
            } catch (InterruptedException e) {
//                System.out.println(Thread.currentThread()+" awaitHands() interrupted");
                player.add(Thread.currentThread());
            }
        }
        System.out.println(Thread.currentThread() + " is awake");
    }
}

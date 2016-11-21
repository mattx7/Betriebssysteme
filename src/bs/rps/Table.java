package bs.rps;

import com.sun.istack.internal.NotNull;

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
    public static final boolean DEBUG_LOG = false;
    public static final boolean LOG = true;

    /**
     * adds Hands to the table
     *
     * @param hand
     */
    synchronized void addHand(@NotNull Hand hand) throws InterruptedException {
        awaitReadyForPlayer();
        awaitOtherPlayer();
        if (LOG) System.out.println(Thread.currentThread() + " " + hand + " addHand()");
        this.hands.add(hand);
        notifyAll();
    }

    /**
     * Cleans the table for a new game
     */
    synchronized void cleanTable() throws InterruptedException {
        awaitHands();
        if (LOG) System.out.println(Thread.currentThread() + " cleanTable()");
        hands.clear();
        player.clear();
        notifyAll();
    }

    /**
     * @return List of Hands
     */
    @NotNull
    synchronized List<Hand> getHands() throws InterruptedException {
        awaitHands();
        if (LOG) System.out.println(Thread.currentThread() + " getHand()");
        return hands;
    }

    /**
     * @return List of Hands
     */
    @NotNull
    synchronized List<Thread> getPlayers() throws InterruptedException {
        awaitHands();
        if (LOG) System.out.println(Thread.currentThread() + " getPlayer()");
        return player;
    }

    /**
     * Wait that Table has two Hands and two Player
     */
    private synchronized void awaitHands() throws InterruptedException {
        while (hands.size() < playerOnTable && player.size() < playerOnTable) {
            if (LOG && DEBUG_LOG) System.out.println(Thread.currentThread() + " await hands");
            wait();
        }
        if (LOG && DEBUG_LOG) System.out.println(Thread.currentThread() + " is awake");
    }

    /**
     * Wait that Table is ready for Player
     */
    private synchronized void awaitReadyForPlayer() throws InterruptedException {
        while (hands.size() == playerOnTable && player.size() == playerOnTable) {
            if (LOG && DEBUG_LOG) System.out.println(Thread.currentThread() + " await clean table");
            wait();
        }
        if (LOG && DEBUG_LOG) System.out.println(Thread.currentThread() + " is awake");
    }

    /**
     * Wait until new Round
     */
    private synchronized void awaitOtherPlayer() throws InterruptedException {
        while (player.contains(Thread.currentThread())) {
            if (LOG && DEBUG_LOG) System.out.println(Thread.currentThread() + " await other player");
            wait();
        }
        player.add(Thread.currentThread());
        if (LOG && DEBUG_LOG) System.out.println(Thread.currentThread() + " is awake");
    }
}

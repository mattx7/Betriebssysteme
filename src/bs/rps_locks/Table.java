package bs.rps_locks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Neak on 12.11.2016.
 * <p>
 * MonitorClass
 *
 * Referenze
 * https://de.wikibooks.org/wiki/Java_Standard:_Threads
 */
class Table {
    private List<Hand> hands = new ArrayList<Hand>();
    private List<Thread> player = new LinkedList<Thread>();
    private int amountOfPlayer = 2;

    private final Lock lockAddHand = new ReentrantLock(true);
    private final Condition readyForResults = lockAddHand.newCondition();
    private final Condition readyForPlayer = lockAddHand.newCondition();
    private final Condition isOtherPlayer = lockAddHand.newCondition();

    /**
     * adds Hands to the table
     *
     * @param hand
     */
    synchronized void addHand(Hand hand) {
        lockAddHand.lock();
        try {
            while (!readyForPlayer()) {
                System.out.println(Thread.currentThread() + " await readyForPlayer");
                readyForPlayer.await();
            }
            while (!isOtherPlayer()) {
                System.out.println(Thread.currentThread() + " await otherPlayer");
                isOtherPlayer.await();
            }
            System.out.println(Thread.currentThread() + " " + hand + " addHand()");
            this.hands.add(hand);
            readyForResults.signalAll();
            isOtherPlayer.signalAll();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread() + " interrupted at addHand()");
        } finally {
            lockAddHand.unlock();
        }
    }

    /**
     * @return List of Hands
     */
    synchronized List<Hand> getHands() {
        lockAddHand.lock();
        List<Hand> hands = new ArrayList<>();
        try {
            while (!readyForResults()) {
                System.out.println(Thread.currentThread() + " await readyForResults");
                readyForResults.await();
            }
            System.out.println(Thread.currentThread() + " getHand()");
            hands = this.hands;
            cleanTable();
            readyForPlayer.signal();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread() + " interrupted at addHand()");
        } finally {
            lockAddHand.unlock();
        }
        return hands;
    }

    /**
     * Cleans the table for a new game
     */
    private synchronized void cleanTable() {
        hands.clear();
        player.clear();
        System.out.println(Thread.currentThread() + " cleanTable()");
    }

    private Boolean readyForResults() {
        return (hands.size() == amountOfPlayer && player.size() == amountOfPlayer);
    }

    private Boolean readyForPlayer() {
        return (hands.size() < amountOfPlayer && player.size() < amountOfPlayer);
    }

    private Boolean isOtherPlayer() {
        Boolean bool = (!player.contains(Thread.currentThread()));
        player.add(Thread.currentThread());
        return bool;
    }
}

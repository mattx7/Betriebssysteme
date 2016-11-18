package bs.rps;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <h1>MonitorClass</h1>
 *
 * <p><b>References</b></p>
 * <p>https://de.wikibooks.org/wiki/Java_Standard:_Threads</p>
 * <p>https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/Condition.html</p>
 */
class TableWithLocks {
    private List<Hand> hands = new ArrayList<Hand>();
    private List<Thread> player = new LinkedList<Thread>();
    private int amountOfPlayer = 2;
    public boolean activateLog = false;

    private final Lock mutex = new ReentrantLock(true); // Binäres Semaphor („Mutex“)
    private final Condition readyForResults = mutex.newCondition();
    private final Condition readyForPlayer = mutex.newCondition();
    private final Condition isOtherPlayer = mutex.newCondition();

    /**
     * adds Hands to the table
     *
     * @param hand
     */
    synchronized void addHand(Hand hand) {
        mutex.lock();
        try {
            while (!readyForPlayer()) {
                if (activateLog) System.out.println(Thread.currentThread() + " await readyForPlayer");
                readyForPlayer.await();
            }
            while (!isOtherPlayer()) {
                if (activateLog) System.out.println(Thread.currentThread() + " await otherPlayer");
                isOtherPlayer.await();
            }
            if (activateLog) System.out.println(Thread.currentThread() + " " + hand + " addHand()");
            this.hands.add(hand);
            readyForResults.signalAll();
            isOtherPlayer.signalAll();
        } catch (InterruptedException e) {
            if (activateLog) System.out.println(Thread.currentThread() + " interrupted at addHand()");
        } finally {
            mutex.unlock();
        }
    }

    /**
     * @return List of Hands
     */
    synchronized List<Hand> getHands() {
        mutex.lock();
        List<Hand> hands = new ArrayList<>();
        try {
            while (!readyForResults()) {
                if (activateLog) System.out.println(Thread.currentThread() + " await readyForResults");
                readyForResults.await();
            }
            if (activateLog) System.out.println(Thread.currentThread() + " getHand()");
            hands = this.hands;
            cleanTable();
            readyForPlayer.signal();
        } catch (InterruptedException e) {
            if (activateLog) System.out.println(Thread.currentThread() + " interrupted at addHand()");
        } finally {
            mutex.unlock();
        }
        return hands;
    }

    /**
     * Cleans the table for a new game
     */
    private synchronized void cleanTable() {
        hands.clear();
        player.clear();
        if (activateLog) System.out.println(Thread.currentThread() + " cleanTable()");
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

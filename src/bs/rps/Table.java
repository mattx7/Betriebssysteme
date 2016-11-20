package bs.rps;

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <h1>MonitorClass</h1>
 *
 * <p><b>References</b></p>
 * <p>https://de.wikibooks.org/wiki/Java_Standard:_Threads</p>
 * <p>https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/Condition.html</p>
 */
class Table {
    private List<Hand> hands = new ArrayList<Hand>();
    private List<Thread> player = new LinkedList<Thread>();
    private int amountOfPlayer = 2;
    public boolean debugLog = false;
    public boolean log = true;

    private final ReentrantLock mutex = new ReentrantLock(true); // Binäres Semaphor („Mutex“)
    private final Condition readyForResults = mutex.newCondition();
    private final Condition readyForPlayer = mutex.newCondition();
    private final Condition isOtherPlayer = mutex.newCondition();

    /**
     * adds Hands to the table
     *
     * @param hand
     */
    synchronized void addHand(Hand hand) throws InterruptedException {
        mutex.lock();
        if (log && debugLog) System.out.println(Thread.currentThread() + " entered addHand()");
        try {
            while (!readyForPlayer()) {
                if (log && debugLog) System.out.println(Thread.currentThread() + " await readyForPlayer in addHand()");
                readyForPlayer.await();
            }
            while (!isOtherPlayer()) {
                if (log && debugLog) System.out.println(Thread.currentThread() + " await otherPlayer in addHand()");
                isOtherPlayer.await();
            }
            if (log)
                System.out.println(Thread.currentThread() + " sets " + hand + " on table | Queue: " + mutex.getQueueLength());
            this.hands.add(hand);
            player.add(Thread.currentThread());
            readyForResults.signalAll();
            isOtherPlayer.signalAll();
        } catch (InterruptedException e) {
            e.fillInStackTrace();
            throw e;
        } finally {
            mutex.unlock();
        }
    }

    /**
     * @return List of Hands
     */
    @Nullable
    synchronized List<Hand> getHands() throws InterruptedException {
        mutex.lock();
        if (log && debugLog) System.out.println(Thread.currentThread() + " entered getHands()");
        List<Hand> hands = new ArrayList<>();
        try {
            while (!readyForResults()) {
                if (log && debugLog)
                    System.out.println(Thread.currentThread() + " await readyForResults in getHands()");
                readyForResults.await();
            }
            if (log) System.out.println(Thread.currentThread() + " getHand() | Queue: " + mutex.getQueueLength());
            hands = this.hands;
        } catch (InterruptedException e) {
            e.fillInStackTrace();
            throw e;
        } finally {
            mutex.unlock();
        }
        return hands;
    }

    /**
     * @return List of Hands
     */
    @Nullable
    synchronized List<Thread> getPlayers() throws InterruptedException {
        mutex.lock();
        if (log && debugLog) System.out.println(Thread.currentThread() + " entered getHands()");
        try {
            while (!readyForResults()) {
                if (log && debugLog)
                    System.out.println(Thread.currentThread() + " await readyForResults in getPlayers()");
                readyForResults.await();
            }
            if (log) System.out.println(Thread.currentThread() + " getPlayers() | Queue: " + mutex.getQueueLength());
        } catch (InterruptedException e) {
            e.fillInStackTrace();
            throw e;
        } finally {
            mutex.unlock();
        }
        return this.player;
    }

    /**
     * Cleans the table for a new game
     */
    synchronized void cleanTable() throws InterruptedException {
        mutex.lock();
        if (log && debugLog) System.out.println(Thread.currentThread() + " entered cleanTable()");
        try {
            while (!readyForResults()) {
                if (log && debugLog)
                    System.out.println(Thread.currentThread() + " await readyForResults in cleanTable()");
                readyForResults.await();
            }
            if (log) System.out.println(Thread.currentThread() + " cleanTable()");
            this.hands.clear();
            this.player.clear();
            readyForPlayer.signal();
        } catch (InterruptedException e) {
            e.fillInStackTrace();
            throw e;
        } finally {
            mutex.unlock();
        }
    }

    private Boolean readyForResults() {
        return (hands.size() == amountOfPlayer && player.size() == amountOfPlayer);
    }

    private Boolean readyForPlayer() {
        return (hands.size() < amountOfPlayer && player.size() < amountOfPlayer);
    }

    private Boolean isOtherPlayer() {
        return (!player.contains(Thread.currentThread()));
    }
}

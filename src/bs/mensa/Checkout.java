package bs.mensa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by Neak on 01.11.2016.
 */
class Checkout {
    private List<Student> waitingQueue = new ArrayList<>();
    private Semaphore semaphore = new Semaphore(1);
    public int number;

    public static final int PAYMENT_TIME = 100; // in ms

    Checkout(int number) {
        this.number = number;
    }

    /**
     * Returns the current queue size
     *
     * @return queue size
     */
    int getQueueSize() {
        return waitingQueue.size();
    }

    /**
     * Add a student to the waiting queue
     *
     * @param student adds to queue
     */
    void addToQueue(Student student) {
        waitingQueue.add(student);
    }

    /**
     * The payment at checkout
     *
     * @param student student who pays
     * @throws InterruptedException if interrupts
     */
    void pay(Student student) throws InterruptedException {
        semaphore.acquire();

        Thread.sleep((long) (Math.random() * PAYMENT_TIME)); // from 0 to PAYMENT_TIME

        System.out.println(String.format("%s payed at Checkout %d while %d students are still waiting", student, number, getQueueSize()-1));
        waitingQueue.remove(student);
        semaphore.release();
    }
}

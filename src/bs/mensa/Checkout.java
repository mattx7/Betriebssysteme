package bs.mensa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Checkout object that is used by students in the checkout process
 *
 */
class Checkout {

    private List<Student> waitingQueue = new ArrayList<>();

    private Semaphore semaphore = new Semaphore(1, true);

    private int number;

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
     * @param student
     */
    void add(Student student) {
        waitingQueue.add(student);
    }

    /**
     * The payment at checkout
     * !!Blocks if payment is blocked by other persons in the waiting queue!!
     *
     * @param student
     * @throws InterruptedException
     */
    void pay(Student student) throws InterruptedException {
        // acquire payment semaphore
        semaphore.acquire();

        try {
            // payment takes up to 100 ms
            long time = (long)(Math.random() * 100);
            Thread.currentThread().sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // print info
        System.out.println(String.format("%s payed at Checkout %d while %d students are still waiting", student, number, getQueueSize()-1));

        // remove the student from the queue after he has paid
        waitingQueue.remove(student);

        // release the blocked checkout
        semaphore.release();
    }
}

package bs.mensa;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by Neak on 01.11.2016.
 */
class Student extends Thread {
    private List<Checkout> checkouts;
    private final Semaphore semaphore = new Semaphore(1);
    private int number;

    Student(int number, List<Checkout> checkouts) throws IllegalArgumentException {
        if(checkouts.size() < 1)
            throw new IllegalArgumentException("The mensa needs to have at least one checkout.");

        this.number = number;
        this.checkouts = checkouts;
    }

    /**
     * Spin the student in the mensa checkout process
     * Every time he leaves the checkout´s queue
     */
    @Override
    public void run() {
        while(!isInterrupted()) {
            try {
                // acquire slot to check for shortest queue
                semaphore.acquire();

                // check queue for all checkouts
                Checkout CheckoutWithShortestQueue = getShortestCheckout();
                // get student into queue
                CheckoutWithShortestQueue.addToQueue(this);
                System.out.println(Thread.currentThread() + " at Checkout " + CheckoutWithShortestQueue.number);
                // release queue
                semaphore.release();
                // get in line at shortest queue and pay
                CheckoutWithShortestQueue.pay(this);

                // eating for 200 ms to 700 ms
                Thread.sleep(lunchtime(200, 700));

            } catch (InterruptedException e) {
                interrupt();
            }
        }

        System.out.println(String.format("%s is leaving", this));
    }

    private long lunchtime(int from, int to) {
        return (from + (long) (Math.random() * (to - from)));
    }

    /**
     * @return shortest Queue
     */
    private Checkout getShortestCheckout() {
        Checkout shortestQueue = checkouts.get(0);
        for (Checkout next : checkouts) {
            if (next.getQueueSize() < shortestQueue.getQueueSize()) {
                shortestQueue = next;
            }
        }
        return shortestQueue;
    }

    @Override
    public String toString() {
        return String.format("Student %d", number);
    }
}

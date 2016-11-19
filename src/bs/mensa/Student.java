package bs.mensa;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by Neak on 01.11.2016.
 */
class Student extends Thread {
    private List<Checkout> checkouts;
    private Semaphore semaphore;
    private int number;

    Student(int number, List<Checkout> checkouts, Semaphore semaphore) throws IllegalArgumentException {
        if(checkouts.size() < 1)
            throw new IllegalArgumentException("The mensa needs to have at least one checkout.");

        this.number = number;
        this.checkouts = checkouts;
        this.semaphore = semaphore;
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
                Checkout shortestQueu = checkouts.get(0);
                for (Checkout next : checkouts) {
                    if (next.getQueueSize() < shortestQueu.getQueueSize()) {
                        shortestQueu = next;
                    }
                }

                // get student into queue
                shortestQueu.add(this);
                // release queue
                semaphore.release();
                // get in line at shortest queue and pay
                shortestQueu.pay(this);

                // eating for 200 ms to 700 ms
                long time = 200 + (long)(Math.random() * 500);
                Thread.currentThread().sleep(time);

            } catch (InterruptedException e) {
                interrupt();
            }
        }

        System.out.println(String.format("%s is done", this));
    }

    @Override
    public String toString() {
        return String.format("Student %d", number);
    }
}

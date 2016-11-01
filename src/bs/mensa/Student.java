package bs.mensa;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Student
 *
 */
class Student extends Thread {

    private List<Checkout> checkouts;

    private Semaphore semaphore;

    private int number;

    /**
     * Constructs a new student
     * The thread does not start automatically.
     * At least one checkout has to be in the list. Otherwise an SimulationException will be thrown.
     *
     * @param checkouts
     * @param semaphore
     * @throws SimulationException
     */
    Student(int number, List<Checkout> checkouts, Semaphore semaphore) throws SimulationException {
        if(checkouts.size() < 1)
            throw new SimulationException("The mensa needs to have at least one checkout.");

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
                for (Checkout checkCheckout : checkouts) {
                    if(checkCheckout.getQueueSize() < shortestQueu.getQueueSize()) {
                        shortestQueu = checkCheckout;
                    }
                }

                // get student into queue
                shortestQueu.add(this);

                // release queue
                semaphore.release();

                // get in line at shortest queue
                shortestQueu.pay(this);

                // eating for 200 ms to 700 ms
                long time = 200 + (long)(Math.random() * 500);
                Thread.currentThread().sleep(time);

            } catch (InterruptedException e) {
                interrupt();
            }
        }

        System.out.println(String.format("%s ist fertig", this));
    }

    @Override
    public String toString() {
        return String.format("Student %d", number);
    }
}

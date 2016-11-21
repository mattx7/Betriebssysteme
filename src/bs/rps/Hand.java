package bs.rps;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Neak on 01.11.2016.
 *
 */
enum Hand implements Runnable {
    Rock, Paper, Scissors;

    private static final List<Hand> VALUES = Collections.unmodifiableList(Arrays.asList(values())); //Liste mit unseren Enums
    private static final int SIZE = VALUES.size(); //Ein Int der so groß ist wie die Anzahl unserer Enums
    private static final Random RANDOM = new Random(); //Random
    private Table table;

    /**
     * @return random Hand
     */
    public static Hand getRandom() {
        return VALUES.get(RANDOM.nextInt(SIZE)); //Return ein zufälligen Enum
    }

    /**
     *
     * @param table set table
     */
    void setTable(Table table) {
        this.table = table;
    }

    @Override
    public void run() {
        System.out.println("Running " + Thread.currentThread().getName());
        try {
            while (!Thread.interrupted()) {
                table.addHand(Hand.getRandom());
            }
            Thread.interrupted();
        } catch (InterruptedException e) {
            Thread.interrupted();
            System.out.println("Thread " + Thread.currentThread().getName() + " interrupted.");
        }
        System.out.println("Thread " + Thread.currentThread().getName() + " exiting.");
    }
}

package bs.rps;

/**
 * Created by Neak on 01.11.2016.
 */
public class Main {

    public static void main(String[] args) {
        Table table = new Table(); // Monitor
        Judge judge = new Judge(table);
        Hand hand1 = Hand.getRandom();
        hand1.setTable(table);
        Hand hand2 = Hand.getRandom();
        hand2.setTable(table);
        Thread thread1 = new Thread(hand1);
        Thread thread2 = new Thread(hand2);
        thread1.start();
        thread2.start();
        judge.start();

        try {
            Thread.currentThread().sleep(40);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        judge.interrupt();
        thread1.interrupt();
        thread2.interrupt();
    }
}
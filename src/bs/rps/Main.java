package bs.rps;

/**
 * Created by Neak on 01.11.2016.
 */
public class Main {

    public static void main(String[] args) {
        Table table = new Table(); // Monitor
        Table.debugLog = true;
        Judge judge = new Judge(table, "judge");
        Hand hand1 = Hand.getRandom();
        hand1.setTable(table);
        Hand hand2 = Hand.getRandom();
        hand2.setTable(table);
        Thread thread1 = new Thread(hand1, "Hand1");
        Thread thread2 = new Thread(hand2, "Hand2");
        thread1.start();
        thread2.start();
        judge.start();

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            thread1.interrupt();
            thread2.interrupt();
            try {
                judge.sleep(50);
                judge.printScore();
                judge.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.currentThread().interrupt();
        }
    }
}
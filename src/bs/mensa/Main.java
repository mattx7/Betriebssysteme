package bs.mensa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neak on 01.11.2016.
 */
public class Main {
    private static final int AMOUNT_OF_STUDENTS = 10;
    private static final int AMOUNT_OF_CHECKOUTS = 2;
    private static final int RUNTIME = 2000;     // in ms

    public static void main(String[] args) {
        List<Checkout> checkouts = new ArrayList<Checkout>(); // Our Checkouts
        for (int j = 1; j <= AMOUNT_OF_CHECKOUTS; j++) {
            checkouts.add(new Checkout(j));
        }

        List<Student> students = new ArrayList<Student>(); // Our Students
        for (int i = 1; i <= AMOUNT_OF_STUDENTS; i++) {
            Student student = new Student(i, checkouts);
            student.start();
            students.add(student);
        }

        System.out.println("==== Mensa is opening ====");

        try {
            Thread.sleep(RUNTIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // interrupt all students
            for (Student student : students) {
                student.interrupt();
            }
        }

        System.out.println("==== Mensa is closing====");
    }
}

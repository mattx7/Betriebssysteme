package bs.mensa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

class Simulation {
    private final int runtime;
    private int amountOfCheckouts = 0;
    private int AmountOfStudents = 0;

    Simulation(int amountOfCheckouts, int AmountOfStudents, int runtime) {
        this.amountOfCheckouts = amountOfCheckouts;
        this.AmountOfStudents = AmountOfStudents;
        this.runtime = runtime;
    }

    /**
     * Run simulation
     *
     * @throws SimulationException
     */
    void start() throws SimulationException {

        // initialize semaphore
        Semaphore sem = new Semaphore(1);

        // initialize checkouts
        List<Checkout> checkouts = new ArrayList<Checkout>();
        for (int j = 0; j < amountOfCheckouts; j++) {
            checkouts.add(new Checkout(j+1));
        }

        // initialize students
        List<Student> students = new ArrayList<Student>();
        for (int i = 0; i < AmountOfStudents; i++) {
            Student stud = new Student(i+1, checkouts, sem);
            stud.start();
            students.add(stud);
        }

        System.out.println("==== Checkout opens ====");

        // simulate for a given time
        try {
            Thread.currentThread().sleep(this.runtime * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // interrupt all students
            for(Student stud : students) {
                stud.interrupt();
                //System.out.println(String.format("%s interrupted", stud));
            }
        }

        System.out.println("==== Checkout closes ====");
    }
}

/**
 * @startuml
 * Simulation -- Checkout
 * Simulation -- Student
 * Simulation -- SimulationException
 *
 * Simulation:void start()
 *
 * SimulationException:
 *
 * Checkout:Semaphore semaphore
 * Checkout:List<Student> waitingLine
 * Checkout:int getQueueSize()
 * Checkout:void add(Student student)
 * Checkout:void pay(Student student)
 *
 * Student:List<Checkout> checkouts
 * Student:Semaphore semaphore
 * Student:void run()
 * @enduml
 */
package bs.mensa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Simulation
 *
 */
class Simulation {

    private final int runtime;

    private int checkoutCount = 0;

    private int studentCount = 0;

    Simulation(int checkoutCount, int studentCount, int runtime) {
        this.checkoutCount = checkoutCount;
        this.studentCount = studentCount;
        this.runtime = runtime;
    }

    /**
     * Run simulation
     * First initializes checkouts and students.
     * Every student thread will be started.
     * Simulation will stop after the given amount of seconds.
     *
     * @throws SimulationException
     */
    void start() throws SimulationException {

        // initialize semaphore
        Semaphore sem = new Semaphore(1);

        // initialize checkouts
        List<Checkout> checkouts = new ArrayList<Checkout>();
        for(int j = 0 ; j < checkoutCount ; j++) {
            checkouts.add(new Checkout(j+1));
        }

        // initialize students
        List<Student> students = new ArrayList<Student>();
        for(int i = 0 ; i < studentCount ; i++) {
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

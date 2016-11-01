package bs.mensa;

public class Main {

    /**
     * The amount of Students who want to eat at the canteen
     */
    private static final int STUDENT_COUNT = 10;

    /**
     * The amount of Checkouts in the canteen
     */
    private static final int CHECKOUT_COUNT = 2;

    /**
     * The Runtime in seconds
     */
    private static final int RUNTIME = 2;

    public static void main(String[] args) {
        // initialize simulation
        Simulation sim = new Simulation(CHECKOUT_COUNT, STUDENT_COUNT, RUNTIME);

        try {
            sim.start();
        } catch (SimulationException e) {
            e.printStackTrace();
        }
    }
}

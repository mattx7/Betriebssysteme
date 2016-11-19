package bs.mensa;

public class Main {
    private static final int AMOUNT_OF_STUDENTS = 10;
    private static final int AMOUNT_OF_CHECKOUTS = 2;
    // The Runtime in seconds
    private static final int RUNTIME = 2;

    public static void main(String[] args) {
        // initialize simulation
        Simulation sim = new Simulation(AMOUNT_OF_CHECKOUTS, AMOUNT_OF_STUDENTS, RUNTIME);

        try {
            sim.start();
        } catch (SimulationException e) {
            e.printStackTrace();
        }
    }
}

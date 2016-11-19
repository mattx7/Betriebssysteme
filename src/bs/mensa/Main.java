package bs.mensa;

/**
 * Created by Neak on 01.11.2016.
 */
public class Main {
    private static final int AMOUNT_OF_STUDENTS = 10;
    private static final int AMOUNT_OF_CHECKOUTS = 2;
    // The Runtime in seconds
    private static final int RUNTIME = 2;

    public static void main(String[] args) {
        Simulation simulation = new Simulation(AMOUNT_OF_CHECKOUTS, AMOUNT_OF_STUDENTS, RUNTIME);

        try {
            simulation.startSimulation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

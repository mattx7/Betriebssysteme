package bs.simrace;

/**
 * Created by abw286 on 31/10/16.
 */
class Accident extends Thread {
    private Thread parent;
    private int crashModifier;

    Accident(Thread parent, int crashModifier) {
        this.parent = parent;
        this.crashModifier = crashModifier;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((long) (Math.random() * crashModifier));
        } catch (InterruptedException e) {
            interrupt();
        }
        parent.interrupt();
    }
}

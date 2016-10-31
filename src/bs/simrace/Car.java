package bs.simrace;

/**
 * Created by abw286 on 31/10/16.
 */
class Car extends Thread implements Comparable<Car> {
    private int runden;
    public int nummer;
    public int gesamtFahrzeit = 0;

    Car(int nummer, int runden) {
        this.nummer = nummer;
        this.runden = runden;
    }

    public void run() {
        int i;
        for (i = 0; i < runden && !isInterrupted(); i++) {
            long zeit = (long) (Math.random() * 100);
            gesamtFahrzeit += zeit;
            try {
                Thread.sleep(zeit);
            } catch (InterruptedException e) {
                interrupt();
            }

        }
        System.out.println("Car " + this.nummer + "  beendet in runde " + i);
    }

    @Override
    public int compareTo(Car o) {
        return gesamtFahrzeit - o.gesamtFahrzeit;
    }
}

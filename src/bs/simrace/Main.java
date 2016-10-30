package bs.simrace;

import java.util.Arrays;

public class Main {
    final static int cars = 5;
    final static int rounds = 10;

    public static void main(String[] args) {

        // Cause Accident
        Accident accident = new Accident(Thread.currentThread(), rounds * 100);
        accident.start();

        // start cars
        Car[] cars = new Car[Main.cars];
        for (int i = 0; i < Main.cars; i++) {
            cars[i] = new Car(i, rounds);
            cars[i].start();
        }

        // Kommen sie vor einem Unfall ans Ziel?
        try {
            for (Car car : cars) {
                car.join();
            }
        } catch (InterruptedException e) {
            for (Car auti : cars) {
                auti.interrupt();
            }
            System.out.println("Accident!");
            return;
        }

        // Alle ankommen
        Arrays.sort(cars);
        System.out.println("**** Result ****");
        for (int i = 0; i < Main.cars; i++) {
            System.out.println((i + 1) + ". Place: Car " + cars[i].nummer + " Time: " + cars[i].gesamtFahrzeit);
        }
    }

}

class Car extends Thread implements Comparable<Car> {
    private int runden;
    public int nummer;
    public int gesamtFahrzeit = 0;

    Car(int nummer, int runden) {
        this.nummer = nummer;
        this.runden = runden;
    }

    public void run() {
        for (int i = 0; i < runden && !isInterrupted(); i++) {
            long zeit = (long)(Math.random() * 100);
            gesamtFahrzeit += zeit;
            try {
                Thread.sleep(zeit);
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }

    @Override
    public int compareTo(Car o) {
        return gesamtFahrzeit - o.gesamtFahrzeit;
    }
}

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
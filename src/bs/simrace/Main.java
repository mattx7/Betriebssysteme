package bs.simrace;

import java.util.Arrays;

public class Main {
    final static int cars = 5;
    final static int rounds = 10;

    public static void main(String[] args) {

        // Cause Accident
        Accident accident = new Accident(Thread.currentThread(), rounds * 200);
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
            for (Car car : cars) {
                car.interrupt();
            }
            System.out.println("Accident!");
            return;
        }

        // Alle ankommen
        accident.interrupt();
        Arrays.sort(cars);
        System.out.println("**** Result ****");
        for (int i = 0; i < Main.cars; i++) {
            System.out.println((i + 1) + ". Place: Car " + cars[i].nummer + " Time: " + cars[i].gesamtFahrzeit);
        }
    }

}




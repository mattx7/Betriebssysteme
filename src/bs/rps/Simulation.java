package bs.rps;


/**
 * Created by Neak on 01.11.2016.
 */
public class Simulation {

    Simulation() {
        GameTable table = new GameTable();
        Judge judge = new Judge(table);
        Player player1 = new Player(1, table);
        Player player2 = new Player(2, table);

        table.addPlayer(player1);
        table.addPlayer(player2);

        player1.start();
        player2.start();
        judge.start();

        try {
            Thread.currentThread().sleep(40);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        judge.interrupt();
        player1.interrupt();
        player2.interrupt();


    }

}



package bs.rps;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neak on 01.11.2016.
 */
public class Simulation {

    Simulation() {
        List<Player> players = new ArrayList<Player>();
        Judge judge = new Judge();
        GameTable gameTable = new GameTable();
        Player playerUno = new Player(1, gameTable);
        Player playerDos = new Player(2, gameTable);

        players.add(playerUno);
        players.add(playerDos);

        playerUno.start();
        playerDos.start();
        judge.start();

        try {
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        judge.interrupt();

        for(Player cPlayer : players) {
            cPlayer.interrupt();
        }
    }

}



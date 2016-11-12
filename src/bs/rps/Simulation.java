package bs.rps;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neak on 01.11.2016.
 */
public class Simulation {

    private List<Player> players = new ArrayList<Player>(); //zum interrupten ganz unten

    private Judge judge;

    public Simulation() {

        GameTable gameTable = new GameTable();

        // add players
        Player playerUno = new Player(1, judge, Hand.getRandom());
        // player will immediately wait for judge to notify him
        playerUno.start();
        players.add(playerUno);

        Player playerDos = new Player(2, judge, Hand.getRandom());
        playerDos.start();
        players.add(playerDos);

        // initialize judge
        judge = new Judge(playerUno, playerDos);
        judge.setName("Judge-Thread");
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



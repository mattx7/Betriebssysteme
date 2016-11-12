package bs.rps;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neak on 01.11.2016.
 */
public class Simulation {

    private List<Player> players = new ArrayList<Player>();

    private Judge judge;

    public Simulation() {
        // initialize judge
        judge = new Judge();
        judge.setName("Judge-Thread");

        // add players
        Player playerUno = new Player();
        playerUno = playerUno.changeHand(playerUno);
        // player will immediately wait for judge to notify him
        playerUno.start();
        players.add(playerUno);

        Player playerDos = new Player();
        playerDos = playerDos.changeHand(playerDos);
        playerDos.start();
        players.add(playerDos);

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



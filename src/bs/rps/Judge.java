package bs.rps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Neak on 12.11.2016.
 */
public class Judge extends Thread {

    private Player playerUno;

    private Player playerDos;

    @Override
    public void run() {
        refresh();

        while(!isInterrupted()) {
            try {
                synchronized (this) {
                    notifyAll();
                    wait();
                    payoff(playerUno,playerDos);
                    // refresh hand
                    refresh();
                }
            } catch (InterruptedException e) {
                interrupt();
            }
        }

        System.out.println("Judge has finished");
    }

    public static String payoff (Player playerUno, Player playerDos) {
        if (playerUno.getHand().equals(playerDos.getHand())) {
            return String.format("'%s' from both hands is a tie.",
                    playerUno);
        }
        if ((playerUno.getHand().equals(Hand.Rock) & playerDos.getHand().equals(Hand.Scissors)) ||
                (playerUno.getHand().equals(Hand.Scissors) & playerDos.getHand().equals(Hand.Paper)) ||
                (playerUno.getHand().equals(Hand.Paper) & playerDos.getHand().equals(Hand.Rock))) {
            return String.format("One's '%s' beats Two's '%s'.", playerUno, playerDos);
        }
        return String.format("Two's '%s' beats One's '%s'.", playerDos, playerUno);
    }


    public void refresh() {
        playerUno = playerUno.changeHand(playerUno);
        playerDos = playerDos.changeHand(playerDos);
        System.out.println(String.format("Judge has new hand : %s, %s", playerUno.getHand().toString(), playerDos.getHand().toString()));
    }
}

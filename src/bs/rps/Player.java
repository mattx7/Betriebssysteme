package bs.rps;

import java.util.Collection;

/**
 * Created by Neak on 12.11.2016.
 */
public class Player extends Thread {

    private Hand hand;


    public Player() {
    }

    public Player changeHand(Player player) {
        player.setHand(hand.getRandom());
        return player;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    @Override
    public String toString() {
        return String.format("Player No.%d ( has %s )", hand);
    }
}
package bs.rps;

import java.util.List;

/**
 * Created by Neak on 12.11.2016.
 */
public class GameTable {

    private List<Player> players;

    void addPlayer(Player player) {
        if (players.size() >= 2)
            throw new IllegalArgumentException("Not more than 2 Hands on one table");

        this.players.add(player);
    }

    public Player getPlayer1() {
        return players.get(0);
    }

    public Player getPlayer2() {
        return players.get(1);
    }
}

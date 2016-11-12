package bs.rps;

/**
 * Created by Neak on 12.11.2016.
 */
public class GameTable {

    private Hand handUno;
    private Hand handDos;

    public GameTable() {

    }

    public Hand getHandUno() {
        return handUno;
    }

    public void setHandUno(Hand handUno) {
        this.handUno = handUno;
    }

    public Hand getHandDos() {
        return handDos;
    }

    public void setHandDos(Hand handDos) {
        this.handDos = handDos;
    }
}

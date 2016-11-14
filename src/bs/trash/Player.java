//package bs.trash;
//
//import bs.rps_locks.GameTable;
//import bs.rps_locks.Hand;
//
///**
// * Created by Neak on 12.11.2016.
// */
//class Player extends Thread {
//
//    private Hand hand;
//    private int nr;
//    private GameTable table;
//
//    Player(int nr, GameTable table) {
//        this.nr = nr;
//        this.table = table;
//        this.hand = Hand.getRandom();
//    }
//
//    Hand getHand() {
//        return hand;
//    }
//
//    public void setTable(GameTable table) {
//        this.table = table;
//    }
//
//    @Override
//    public void run() {
//        while (!isInterrupted()) {
//
//            this.hand = Hand.getRandom();
//            table.addPlayer(this);
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//
//        System.out.println(String.format("%s ist fertig", this));
//    }
//
//    @Override
//    public String toString() {
//        return nr + "";
//    }
//}
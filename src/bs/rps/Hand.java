package bs.rps;

import java.util.Random;

/**
 * Created by Neak on 01.11.2016.
 */
public enum Hand {
    Rock, Paper, Scissors;

    public static Hand getRandom(Hand exclude) {
        Random rand = new Random();

        Hand returnValue;
        do {
            returnValue = values()[rand.nextInt(values().length)];
        } while(returnValue.equals(exclude));

        return returnValue;
    }

    public static Hand getRandom() {
        return getRandom(null);
    }
}
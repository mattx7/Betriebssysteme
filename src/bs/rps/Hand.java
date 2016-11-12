package bs.rps;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Neak on 01.11.2016.
 */
public enum Hand {
    Rock, Paper, Scissors;

    private static final List<Hand> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Hand randomHand()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }


    public static Hand getRandom() {
        return randomHand();
    }
}

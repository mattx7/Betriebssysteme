package bs.rps;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Neak on 01.11.2016.
 *
 */
enum Hand {
    Rock, Paper, Scissors;

    private static final List<Hand> VALUES = Collections.unmodifiableList(Arrays.asList(values())); //Liste mit unseren Enums
    private static final int SIZE = VALUES.size(); //Ein Int der so groß ist wie die Anzahl unserer Enums
    private static final Random RANDOM = new Random(); //Random

    public static Hand getRandom() {
        return VALUES.get(RANDOM.nextInt(SIZE)); //Return ein zufälligen Enum
    }

}

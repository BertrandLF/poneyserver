package com.ninja_squad.ponyserver.web.pony;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by blefoulgoc on 3/1/17.
 */
public enum PonyColor {
    ORANGE,
    PURPLE,
    YELLOW,
    GREEN,
    BLUE;

    private static final List<PonyColor> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static PonyColor randomPonyColor()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}

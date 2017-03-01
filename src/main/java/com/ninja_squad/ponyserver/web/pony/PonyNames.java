package com.ninja_squad.ponyserver.web.pony;

import java.util.*;

/**
 * Created by blefoulgoc on 3/1/17.
 */
public class PonyNames {

    private static final String[] QUALIFIER = {"Laughing", "Jumping", "Tiny", "Massive", "Light", "Angry", "Drunk", "Happy", "Corny", "Steamy", "Hardcore"};
    private static final String[] NOUN = {"Henry", "Muppet", "Grizzly", "Kookaburra", "Emu", "Roo", "Owl", "Trump", "Morty", "Hunter", "Samurai", "Worm"};

    private static final List<String> QUALIFIER_VALUES =
            Collections.unmodifiableList(Arrays.asList(QUALIFIER));
    private static final int QUALIFIER_SIZE = QUALIFIER_VALUES.size();

    private static final List<String> NOUN_VALUES =
            Collections.unmodifiableList(Arrays.asList(NOUN));
    private static final int NOUN_SIZE = NOUN_VALUES.size();

    private static final Random RANDOM = new Random();

    public static String randomName() {
        return QUALIFIER_VALUES.get(RANDOM.nextInt(QUALIFIER_SIZE))
                + " "
                + NOUN_VALUES.get(RANDOM.nextInt(NOUN_SIZE));
    }
}

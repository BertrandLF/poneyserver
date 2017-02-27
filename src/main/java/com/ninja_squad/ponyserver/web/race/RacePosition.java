package com.ninja_squad.ponyserver.web.race;

import com.ninja_squad.ponyserver.web.pony.Pony;

/**
 * The position of a pony while a race is running
 * @author JB Nizet
 */
public class RacePosition {
    private Pony pony;
    private int position;

    public RacePosition(Pony pony) {
        this.pony = pony;
    }

    public void move(int offset) {
        position += offset;
        if (position > 100) {
            position = 100;
        }
    }

    public Pony getPony() {
        return pony;
    }

    public int getPosition() {
        return position;
    }
}

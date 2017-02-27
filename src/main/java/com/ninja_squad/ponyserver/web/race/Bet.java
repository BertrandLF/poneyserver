package com.ninja_squad.ponyserver.web.race;

/**
 * A bet on a pony for a given race.
 * @author JB Nizet
 */
public class Bet {
    private long ponyId;

    private long raceId;

    public long getPonyId() {
        return ponyId;
    }

    public void setPonyId(long pony) {
        this.ponyId = pony;
    }

    public long getRaceId() {
        return raceId;
    }

    public void setRaceId(long raceId) {
        this.raceId = raceId;
    }
}

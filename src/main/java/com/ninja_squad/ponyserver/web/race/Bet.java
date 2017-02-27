package com.ninja_squad.ponyserver.web.race;

/**
 * A bet on a pony for a given race.
 * @author JB Nizet
 */
public class Bet {
    private Long raceId;
    private String pony;

    public Long getRaceId() {
        return raceId;
    }

    public void setRaceId(Long raceId) {
        this.raceId = raceId;
    }

    public String getPony() {
        return pony;
    }

    public void setPony(String pony) {
        this.pony = pony;
    }
}

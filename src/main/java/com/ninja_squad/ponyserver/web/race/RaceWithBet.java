package com.ninja_squad.ponyserver.web.race;

import com.ninja_squad.ponyserver.web.pony.Pony;

import java.util.Date;
import java.util.Set;

/**
 * A race and the bet (which can be null) of the current user.
 * @author JB Nizet
 */
public class RaceWithBet {

    // Note: we could have used JsonUnwrapped instead of copying all the Race field, but Swagger
    // doesn't understands it, and thus gives incorrect information

    private Long id;
    private String name;
    private RaceStatus status;
    private Set<Pony> ponies;
    private Long betPonyId;
    private Date startInstant;

    public RaceWithBet(Race race, Long betPonyId) {
        this.id = race.getId();
        this.name = race.getName();
        this.status = race.getStatus();
        this.ponies = race.getPonies();
        this.betPonyId = betPonyId;
        this.startInstant = race.getStartInstant();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RaceStatus getStatus() {
        return status;
    }

    public Set<Pony> getPonies() {
        return ponies;
    }

    public Long getBetPonyId() {
        return betPonyId;
    }

    public Date getStartInstant() {
        return startInstant;
    }

}

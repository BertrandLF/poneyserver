package com.ninja_squad.ponyserver.web.race;

import com.ninja_squad.ponyserver.web.pony.Pony;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A race, which can be not started, started or finished, and which has 3 ponies.
 * @author JB Nizet
 */
public class Race {
    private Long id;
    private String name;
    private RaceStatus status;
    private Set<Pony> ponies;

    public Race(Long id, String name, RaceStatus status, Set<Pony> ponies) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.ponies = Collections.unmodifiableSet(new HashSet<>(ponies));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public synchronized RaceStatus getStatus() {
        return status;
    }

    public synchronized void setStatus(RaceStatus status) {
        this.status = status;
    }

    public Set<Pony> getPonies() {
        return ponies;
    }
}

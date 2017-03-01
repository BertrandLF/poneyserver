package com.ninja_squad.ponyserver.web.race;

import com.ninja_squad.ponyserver.web.pony.Pony;

import java.util.*;

/**
 * A race, which can be not started, started or finished, and which has 3 ponies.
 * @author JB Nizet
 */
public class Race {
    private Long id;
    private String name;
    private RaceStatus status;
    private Set<Pony> ponies;
    private Date startInstant;

    public Race(Long id, String name, RaceStatus status, Set<Pony> ponies, Date startInstant) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.ponies = new HashSet<>(ponies);
        this.startInstant = startInstant;
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

    public Date getStartInstant() {
        return startInstant;
    }

    public int getMaxPosition() {
        Optional<Pony> leadingPony = ponies.stream().max(Comparator.comparing(Pony::getPosition));
        return leadingPony.isPresent()? leadingPony.get().getPosition() : 0;
    }

    @Override
    public String toString() {
        StringBuilder race = new StringBuilder();
        this.ponies.stream().map(pony -> race
                .append(pony.getName())
                .append(" ")
                .append(pony.getPosition())
                .append("\n"));
        return race.toString();
    }
}

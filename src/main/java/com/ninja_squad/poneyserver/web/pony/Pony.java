package com.ninja_squad.poneyserver.web.pony;

/**
 * Created by blefoulgoc on 2/27/17.
 */
public class Pony {

    private long id;
    private String name;
    private String color;

    public Pony(long id, String name, String color){
        this.id = id;
        this.name = name;
        this.color = color;
    }

    @SuppressWarnings("unused")
    public long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public String getColor() {
        return color;
    }

}

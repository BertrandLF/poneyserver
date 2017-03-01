package com.ninja_squad.ponyserver.web.pony;

/**
 * Created by blefoulgoc on 2/27/17.
 */
public class Pony {

    private long id;
    private String name;
    private String color;

    private int position;

    public Pony(long id, String name, String color){
        this.id = id;
        this.name = name;
        this.color = color;
        this.position = 0;
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

    @SuppressWarnings("unused")
    public int getPosition() {
        return position;
    }

    public void move(int distance){
        this.setPosition(this.getPosition() + distance);
    }

    private void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pony pony = (Pony) o;

        if (id != pony.id) return false;
        if (!name.equals(pony.name)) return false;
        return color.equals(pony.color);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }
}

package com.datastructure;

public class IPair {
    public int x;
    public int y;
    public IPair(int x , int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IPair) {
            IPair h = (IPair)obj;
            return this.x == h.x && this.y == h.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ("#" + this.x + "#" + this.y + "#").hashCode();
    }
}

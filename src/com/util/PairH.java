package com.util;

public class PairH {
    public int x;
    public int y;
    public PairH(int x , int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PairH) {
            PairH h = (PairH)obj;
            return this.x == h.x && this.y == h.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ("#" + this.x + "#" + this.y + "#").hashCode();
    }
}

package com.company;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
class PointHash{
    int x;
    int y;
    public PointHash(int x,int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PointHash)
            return this.x == ((PointHash) obj).x && this.y == ((PointHash) obj).y;
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new String("#" + this.x + "#" + this.y + "#").hashCode();
    }

    @Override
    public String toString() {
        return new String("( " + this.x + " , " + this.y +" )");
    }
}
public class UnionFind<E> {
    HashMap<E,E> fatherMap;
    HashMap<E,Integer> rankMap;
    Set<E> elementSet;
    UnionFind () {
        fatherMap = new HashMap<>();
        rankMap = new HashMap<>();
        elementSet = new HashSet<>();
    }
    public void put(E e) {
        fatherMap.put(e,e);
        rankMap.put(e,1);
        elementSet.add(e);
    }
    public E findFather(E e) {
        Stack<E> stack = new Stack<>();
        while (fatherMap.get(e) != e) {
            stack.push(e);
            e = fatherMap.get(e);
        }
        while (!stack.isEmpty()) {
            fatherMap.put(stack.pop(),e);
        }
        return e;
    }
    public void union(E e1,E e2) {
        if (elementSet.contains(e1) && elementSet.contains(e2)) {
            E aFather = findFather(e1);
            E bFather = findFather(e2);
            if (aFather != bFather) {
                int aRank = rankMap.get(aFather);
                int bRank = rankMap.get(bFather);
                E big = aRank > bRank ? aFather:bFather;
                E small = aRank > bRank ? bFather : aFather;
                fatherMap.put(small,big);
                rankMap.put(big,aRank + bRank);
                rankMap.remove(small);
            }
        }
    }
    public boolean isSameSet(E e1,E e2) {
        if (elementSet.contains(e1) && elementSet.contains(e2)) {
            return findFather(e1) == findFather(e2) ? true : false;
        }
        return false;
    }
}

package com.designer;

import java.util.HashMap;
import java.util.Map;

public class SetAllHashMap <K,V> {

    Map<K,ValueAndTimer<V>> map;

    Long time;

    ValueAndTimer defultValue;

    public SetAllHashMap() {

        this.time = new Long(0);

        defultValue = new ValueAndTimer(null,-1);

        map = new HashMap<>();
    }

    class ValueAndTimer <V> {

        V val;

        long time;

        public ValueAndTimer(V val, long time) {
            this.val = val;
            this.time = time;
        }
    }

    public void set (K key,V val) {

        ValueAndTimer valueAndTimer = map.containsKey(key) ? map.get(key) : new ValueAndTimer(val,time);

        if (!map.containsKey(key)) {
            map.put(key,valueAndTimer);
        }

        valueAndTimer.val = val;

        valueAndTimer.time = time;

        time++;
    }

    public void setAll (V val) {

        defultValue.time = time;

        defultValue.val = val;

        time++;
    }

    public V get (K key) {

        if (!map.containsKey(key)) return null;

        ValueAndTimer valueAndTimer = map.get(key).time > defultValue.time ? map.get(key) : defultValue;

        return (V) valueAndTimer.val;
    }

    public boolean contained (K key) {
        return map.containsKey(key);
    }
}

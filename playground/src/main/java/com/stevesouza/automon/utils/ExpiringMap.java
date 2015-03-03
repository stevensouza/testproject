package com.stevesouza.automon.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by stevesouza on 3/3/15.
 */
public class ExpiringMap<K, V extends TimeExpirable> extends  LinkedHashMap<K, V> {

    public ExpiringMap() {
        super(16, 0.75f, true);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return eldest.getValue().hasExpired();
    }
}
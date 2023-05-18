package com.Millennium.InMemoryCache.bl;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CacheService<K,V> implements Map<K,V>{

    private Entry<K,V>[] entries;
    private int size;
    @Value("${cache.capacity}")
    private final int capacity = 16;
    private static final double LOAD_FACTOR = 0.75;

    @PostConstruct
    public void init() {
        entries = new Entry[capacity];
        size = 0;
    }
    @Override
    public synchronized void put(K key, V value) {
        int index = getIndex(key);
        if (entries[index] == null) {
            entries[index] = new Entry<K,V>(key, value);
            size++;
            if (size > entries.length * LOAD_FACTOR) {
                resize();
            }
        } else {
            entries[index].value = value;
        }
    }

    @Override
    public synchronized V get(K key) {
        int index = getIndex(key);
        if (entries[index] != null) {
            return entries[index].value;
        }
        return null;
    }

    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % entries.length;
    }

    private void resize() {
        Entry[] newEntries = new Entry[entries.length * 2];
        for (Entry entry : entries) {
            if (entry != null) {
                int newIndex = Math.abs(entry.key.hashCode()) % newEntries.length;
                newEntries[newIndex] = entry;
            }
        }
        entries = newEntries;
    }

    // addition - thought that necessary
    public void remove(K key) {
        int index = getIndex(key);
        if (entries[index] != null) {
            entries[index] = null;
            size--;
        }
    }

    private static class Entry<K,V> {
        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

}

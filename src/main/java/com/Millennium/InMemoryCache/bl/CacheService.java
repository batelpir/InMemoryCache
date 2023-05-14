package com.Millennium.InMemoryCache.bl;

import org.springframework.stereotype.Service;

@Service
public class CacheService<K,V> implements Map<K,V>{

    @Override
    public void put(K key, V value) {

    }

    @Override
    public V get(K key) {
        return null;
    }
}

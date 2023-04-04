package tqs.airquality.cache;

import java.util.Optional;

public interface ICache<K, V> {
    public Optional<V> get(K key);
    public void put(K key, V value);
    public void pop(String key);
}

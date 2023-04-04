package tqs.airquality.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.springframework.stereotype.Service;

import tqs.airquality.utils.ConfigUtils;

@Service
public class AirCache implements ICache<String, Object> {
    private long expiracy = Long.parseLong(ConfigUtils.getPropertyFromConfig("cache.ttl"));
    private Map<String, CacheEntry> map = new HashMap<>();

    Logger logger = Logger.getLogger(this.getClass().getName());

    public AirCache() {
    }

    @Override
    public Optional<Object> get(String key) {
        logger.log(Level.INFO, "[ CACHE ] GET {0} - FETCHING", key);
        if(map.containsKey(key)) {
            CacheEntry entry = map.get(key);

            if(!isExpired(entry)) {
                logger.log(Level.INFO, "[ CACHE ] GET {0} - FOUND", key);
                return Optional.of(entry.getValue());
            }

            logger.log(Level.INFO, "[ CACHE ] GET {0} - EXPIRED", key);
            this.pop(key);
            return Optional.empty();
        }

        logger.log(Level.INFO, "[ CACHE ] GET {0} - NO MATCH", key);
        return Optional.empty();
    }
    
    @Override
    public void put(String key, Object value) {
        logger.log(Level.INFO, "[ CACHE ] PUT {0}", key);
        map.put(key, new CacheEntry(value));
    }

    @Override
    public void pop(String key) {
        logger.log(Level.INFO, "[ CACHE ] POP {0}", key);
        if(map.containsKey(key)) {
            map.remove(key);
        }
    }

    private boolean isExpired(CacheEntry ce) {
        return (ce.getTs().getTime() + this.expiracy * 1000) < new Date().getTime();
    }

    public void setExpiracy(long expiracy) {
        this.expiracy = expiracy;
    }

    @Override
    public String toString() {
        return "casche";
    }
}

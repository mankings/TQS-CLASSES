package tqs.airquality.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.airquality.adapters.AQICNAdapter;
import tqs.airquality.adapters.OpenWeatherAdapter;
import tqs.airquality.cache.CacheTracker;
import tqs.airquality.cache.ICache;
import tqs.airquality.model.AirStats;
import tqs.airquality.model.CacheStats;

@Service
public class AirService {
    @Autowired
    private ICache<String, Object> cache;

    @Autowired
    private AQICNAdapter aqicnAdapter;

    @Autowired
    private OpenWeatherAdapter openWeatherAdapter;

    private CacheTracker cacheTracker;
    
    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    public AirService() {
        this.cacheTracker = new CacheTracker();
    }
    

    public AirStats today(String location) throws URISyntaxException, ParseException, IOException {
        logger.log(Level.INFO, "[ SERVICE ] TODAY {0}", location);  
        logger.log(Level.INFO, "[ SERVICE ] TODAY {0} cache", cache);

        String key = "/today?" + location.toLowerCase();
        long ts = new Date().getTime();
        Optional<Object> cacheresult = cache.get(key);
        AirStats stats;

        if (cacheresult.isEmpty()) {
            logger.log(Level.INFO, "[ SERVICE ] TODAY {0} - FETCHING", location);
            stats = aqicnAdapter.today(location);
            cacheTracker.requestTrack(false, new Date().getTime() - ts);
            if (stats == null) return null;
            
            if (stats.missingValues()) {
                AirStats s = openWeatherAdapter.today(stats.getLat(), stats.getLon());
                stats.fill(s);
            }

            cache.put(key, stats);
            return stats;
        }
        
        logger.log(Level.INFO, "[ SERVICE ] TODAY {0} - IN CACHE", location);
        stats = (AirStats) cacheresult.get();
        cacheTracker.requestTrack(true, new Date().getTime() - ts);
        return stats;
    }

    public List<AirStats> forecast(String location) throws URISyntaxException, ParseException, IOException {
        logger.log(Level.INFO, "[ SERVICE ] WEEK {0}", location);
        AirStats s = this.today(location);
        if (s == null)
            return new ArrayList<>();

        String key = "/forecast?" + location.toLowerCase();
        long ts = new Date().getTime();
        Optional<Object> cacheresult = cache.get(key);
        List<AirStats> lst;

        if (cacheresult.isEmpty()) {
            logger.log(Level.INFO, "[ SERVICE ] WEEK {0} - FETCHING", location);
            lst = openWeatherAdapter.forecast(s.getLat(), s.getLon());
            for (AirStats as : lst) {
                as.setLocation(s.getLocation());
                as.setKey(s.getKey());
            }
            cacheTracker.requestTrack(false, new Date().getTime() - ts);
            cache.put(key, lst);
            return lst;
        }

        logger.log(Level.INFO, "[ SERVICE ] WEEK {0} - IN CACHE", location);
        lst = (ArrayList<AirStats>) cacheresult.get();
        cacheTracker.requestTrack(true, new Date().getTime() - ts);
        return lst;
    }

    public List<AirStats> history(String location) throws URISyntaxException, ParseException, IOException {
        logger.log(Level.INFO, "[ SERVICE ] HISTORY {0}", location);
        AirStats s = this.today(location);
        if (s == null)
            return new ArrayList<>();

        String key = "/history?" + location.toLowerCase();
        long ts = new Date().getTime();
        Optional<Object> cacheresult = cache.get(key);
        List<AirStats> lst;

        if (cacheresult.isEmpty()) {
            logger.log(Level.INFO, "[ SERVICE ] HISTORY {0} - FETCHING", location);
            lst = openWeatherAdapter.history(s.getLat(), s.getLon());
            for (AirStats as : lst) {
                as.setLocation(s.getLocation());
                as.setKey(s.getKey());
            }
            cacheTracker.requestTrack(false, new Date().getTime() - ts);
            cache.put(key, lst);
            return lst;
        } 

        logger.log(Level.INFO, "[ SERVICE ] HISTORY {0} - IN CACHE", location);
        lst = (ArrayList<AirStats>) cacheresult.get();
        cacheTracker.requestTrack(true, new Date().getTime() - ts);
        return lst;
    }

    public CacheStats cacheStats() {
        CacheStats stats = this.cacheTracker.getStats();
        logger.log(Level.INFO, "[ SERVICE ] CACHESTATS {0}", stats);
        return stats;
    }
}

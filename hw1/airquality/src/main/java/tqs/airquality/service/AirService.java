package tqs.airquality.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.airquality.adapters.AQICNAdapter;
import tqs.airquality.adapters.OpenWeatherAdapter;
import tqs.airquality.cache.ICache;
import tqs.airquality.model.AirStats;

@Service
public class AirService {
    @Autowired
    private ICache<String, Object> cache;
    
    @Autowired
    private AQICNAdapter aqicnAdapter;

    @Autowired
    private OpenWeatherAdapter openWeatherAdapter;


    private Logger logger = Logger.getLogger(this.getClass().getName());

    public AirStats today(String location) throws Exception {
        logger.log(Level.INFO, "[ SERVICE ] TODAY {0}", location);
        logger.log(Level.INFO, "[ SERVICE ] TODAY {0} cache", cache);

        Optional<Object> cacheresult = Optional.empty();
        try {
            cacheresult = cache.get(location);
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "[ SERVICE ] TODAY {0}", e);
        }
        AirStats stats;

        logger.log(Level.INFO, "[ SERVICE ] TODAY {0} - CACHE DONE", location);
        if(cacheresult.isEmpty()) {
            stats = aqicnAdapter.today(location);
            logger.log(Level.INFO, "[ SERVICE ] TODAY {0} - FETCHING", location);
        } else {
            stats = (AirStats) cacheresult.get();
            logger.log(Level.INFO, "[ SERVICE ] TODAY {0} - IN CACHE", location);
        }

        return stats;
    }

    public List<AirStats> week(String location) throws Exception {
        logger.log(Level.INFO, "[ SERVICE ] WEEK {0}", location);
        AirStats s = this.today(location);
        if (s == null) return null;

        return openWeatherAdapter.week(location, s.getLat(), s.getLon());
    }

    public List<AirStats> history(String location) throws Exception {
        logger.log(Level.INFO, "[ SERVICE ] HISTORY {0}", location);
        AirStats s = this.today(location);
        if (s == null) return null;

        return openWeatherAdapter.history(location, s.getLat(), s.getLon());
    }
}

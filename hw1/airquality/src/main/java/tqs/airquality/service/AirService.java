package tqs.airquality.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.airquality.adapters.AQICNAdapter;
import tqs.airquality.adapters.OpenWeatherAdapter;
import tqs.airquality.model.AirStats;

@Service
public class AirService {
    @Autowired
    private AQICNAdapter aqicnAdapter;

    @Autowired
    private OpenWeatherAdapter openWeatherAdapter;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public AirStats today(String location) throws URISyntaxException, ParseException, IOException {
        logger.log(Level.INFO, "[ SERVICE ] TODAY {0}", location);
        return aqicnAdapter.today(location);
    }

    public List<AirStats> week(String location) throws URISyntaxException, ParseException, IOException {
        logger.log(Level.INFO, "[ SERVICE ] WEEK {0}", location);
        AirStats s = this.today(location);
        if (s == null) return null;

        return openWeatherAdapter.week(s.getLat(), s.getLon());
    }

    public List<AirStats> history(String location) throws URISyntaxException, ParseException, IOException {
        logger.log(Level.INFO, "[ SERVICE ] HISTORY {0}", location);
        AirStats s = this.today(location);
        if (s == null) return null;

        return openWeatherAdapter.history(s.getLat(), s.getLon());
    }
}

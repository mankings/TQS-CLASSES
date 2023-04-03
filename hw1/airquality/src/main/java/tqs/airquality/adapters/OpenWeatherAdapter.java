package tqs.airquality.adapters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import tqs.airquality.http.IHttpClient;
import tqs.airquality.model.AirStats;

public class OpenWeatherAdapter {
    @Autowired
    private IHttpClient httpClient;

    public List<AirStats> week(double lat, double lon) {
        return null;
    }

    public List<AirStats> history(double lat, double lon) {
        return null;
    }
}

package tqs.airquality.adapters;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.airquality.http.IHttpClient;
import tqs.airquality.model.AirStats;
import tqs.airquality.utils.ConfigUtils;
import tqs.airquality.utils.JsonUtils;

@Service
public class OpenWeatherAdapter {
    @Autowired
    private IHttpClient httpClient;

    private static final String APIURL = ConfigUtils.getPropertyFromConfig("openweather.url");
    private static final String APIKEY = ConfigUtils.getPropertyFromConfig("openweather.key");

    private Map<String, Object> headers = new HashMap<>();

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public List<AirStats> week(String location, double lat, double lon) throws URISyntaxException, ParseException, IOException {
        String path = "forecast";
        URIBuilder uriBuilder = new URIBuilder(APIURL + path);
        uriBuilder.addParameter("lat", String.valueOf(lat));
        uriBuilder.addParameter("lon", String.valueOf(lon));
        uriBuilder.addParameter("appid", APIKEY);
        String uri = uriBuilder.build().toString();

        logger.log(Level.INFO, "[ OPENWEATHER_ADAPTER ] WEEK URI {0}", uri);

        String response = httpClient.doGet(uri, headers);
        JSONObject jsonresponse = JsonUtils.responseToJson(response);

        JSONArray jsonarr = (JSONArray) jsonresponse.get("list");
        if (jsonarr == null) {
            logger.log(Level.INFO, "[ OPENWEATHER_ADAPTER ] WEEK VALUES {0}", "NULL");
            return new ArrayList<>();
        }

        List<AirStats> lst = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            JSONObject jobj = (JSONObject) jsonarr.get(jsonarr.size() / 4 - 1);

            int dt = ((Long) jobj.get("dt")).intValue();

            int aqi = ((Long) ((JSONObject) jobj.get("main")).get("aqi")).intValue();

            JSONObject components = (JSONObject) jobj.get("components");
            double pm10 = getValue("pm10", components);
            double co = getValue("co", components);
            double no2 = getValue("no2", components);
            double o3 = getValue("o3", components);
            double so2 = getValue("so2", components);

            AirStats s = new AirStats(location, lat, lon, new Date(dt * (long) 1000), aqi);
            s.setValues(pm10, co, no2, o3, so2);

            lst.add(s);
        }
        
        logger.log(Level.INFO, "[ OPENWEATHER_ADAPTER ] WEEK {0}", "GOTLIST");
        return lst;
    }

    public List<AirStats> history(String location, double lat, double lon) throws URISyntaxException, ParseException, IOException{
        String path = "history";
        URIBuilder uriBuilder = new URIBuilder(APIURL + path);
        uriBuilder.addParameter("lat", String.valueOf(lat));
        uriBuilder.addParameter("lon", String.valueOf(lon));
        uriBuilder.addParameter("appid", APIKEY);

        String weekago = String.valueOf((new Date(System.currentTimeMillis() - 7*1000*60*60*24)).getTime() / 1000);
        String yesterday = String.valueOf((new Date(System.currentTimeMillis() - 1000*60*60*24)).getTime() / 1000);

        uriBuilder.addParameter("start", weekago);
        uriBuilder.addParameter("end", yesterday);
        String uri = uriBuilder.build().toString();

        logger.log(Level.INFO, "[ OPENWEATHER_ADAPTER ] HISTORY URI {0}", uri);

        String response = httpClient.doGet(uri, headers);
        JSONObject jsonresponse = JsonUtils.responseToJson(response);

        JSONArray jsonarr = (JSONArray) jsonresponse.get("list");
        if (jsonarr == null) {
            logger.log(Level.INFO, "[ OPENWEATHER_ADAPTER ] WEEK VALUES {0}", "NULL");
            return new ArrayList<>();
        }

        List<AirStats> lst = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            JSONObject jobj = (JSONObject) jsonarr.get(jsonarr.size() / 7 - 1);

            int dt = ((Long) jobj.get("dt")).intValue();

            int aqi = ((Long) ((JSONObject) jobj.get("main")).get("aqi")).intValue();

            JSONObject components = (JSONObject) jobj.get("components");
            double pm10 = getValue("pm10", components);
            double co = getValue("co", components);
            double no2 = getValue("no2", components);
            double o3 = getValue("o3", components);
            double so2 = getValue("so2", components);

            AirStats s = new AirStats(location, lat, lon, new Date(dt * (long) 1000), aqi);
            s.setValues(pm10, co, no2, o3, so2);

            lst.add(s);
        }
        
        logger.log(Level.INFO, "[ OPENWEATHER_ADAPTER ] HISTORY {0}", "GOTLIST");
        return lst;
    }

    private double getValue(String key, JSONObject obj) {
        double val = -1;

        Object v;
        try {
            v = obj.get(key);
        } catch (NullPointerException e) {
            return val;
        }

        if (v instanceof Long) {
            val = ((Long) v).doubleValue();
        } else if (v instanceof Double) {
            val = (Double) v;
        }

        return val;
    }
}

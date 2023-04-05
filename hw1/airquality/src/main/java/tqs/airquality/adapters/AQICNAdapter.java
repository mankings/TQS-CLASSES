package tqs.airquality.adapters;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
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
public class AQICNAdapter {
    
    @Autowired
    private IHttpClient httpClient;

    private static final String APIURL = ConfigUtils.getPropertyFromConfig("aqicn.url");
    private static final String APIKEY = ConfigUtils.getPropertyFromConfig("aqicn.key");

    private Map<String, Object> headers = new HashMap<>();

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public AirStats today(String location) throws URISyntaxException, ParseException, IOException {
        String path = "feed/" + location + "/";
        URIBuilder uriBuilder = new URIBuilder(APIURL + path);
        uriBuilder.addParameter("token", APIKEY);
        String uri = uriBuilder.build().toString();

        logger.log(Level.INFO, "[ AQICN_ADAPTER ] TODAY URI {0}", uri);

        String response = httpClient.doGet(uri, headers);
        JSONObject jsonresponse = JsonUtils.responseToJson(response);
        
        JSONObject data;
        try {
            data = (JSONObject) jsonresponse.get("data");
        } catch (ClassCastException e) {
            logger.log(Level.INFO, "[ AQICN_ADAPTER ] TODAY VALUES {0}", "NULL");
            return null;
        }

        int aqi = ((Long) data.get("aqi")).intValue();

        JSONObject cityData = (JSONObject) data.get("city");

        String l = (String) cityData.get("name");
        double lat = (Double) ((JSONArray) cityData.get("geo")).get(0);
        double lon = (Double) ((JSONArray) cityData.get("geo")).get(1);

        JSONObject timeData = (JSONObject) data.get("time");
        long ts = (Long) timeData.get("v");
        Date d = new Date(ts * 1000);
        
        JSONObject todayData = (JSONObject) data.get("iaqi");
        double pm10 = getValue("pm10", todayData);
        double co = getValue("co", todayData);
        double no2 = getValue("no2", todayData);
        double o3 = getValue("o3", todayData);
        double so2 = getValue("so2", todayData);

        AirStats s = new AirStats(l, lat, lon, d, aqi);
        s.setValues(pm10, co, no2, o3, so2);
        logger.log(Level.INFO, "[ AQICN_ADAPTER ] TODAY VALUES {0}", s);
        
        return s;
    }

    private double getValue(String key, JSONObject obj) {
        double val = -1;

        Object v;
        try {
            v = ((JSONObject) obj.get(key)).get("v");
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

package tqs.airquality.model;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

public class AirStats {
    private String key;
    private String location;
    private double lat;
    private double lon;

    private Date date;
    
    private int aqi;
    private HashMap<String, Double> values;

    public AirStats(String key, String location, double lat, double lon, Date date, int aqi) {
        this.key = key;
        this.location = location;
        this.date = date;
        this.lat = lat;
        this.lon = lon;
        this.aqi = aqi;

        this.values = new HashMap<>();
    }

    public String getKey() {
        return key;
    }

    public String getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public int getAqi() {
        return aqi;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public void setValues(double pm10, double co, double no2, double o3, double so2) {
        values.put("pm10", pm10);
        values.put("co", co);
        values.put("no2", no2);
        values.put("o3", o3);
        values.put("so2", so2);
    }

    public Map<String, Double> getValues() {
        return values;
    }

    public boolean missingValues() {
        for (Entry<String, Double> entry : values.entrySet()) {
            if (entry.getValue() == -1) {
                return true;
            }
        }
        return false;
    }

    public void fill(AirStats s) {
        for (Entry<String, Double> entry : this.values.entrySet()) {
            if (entry.getValue() == -1) {
                values.put(entry.getKey(), s.getValues().get(entry.getKey()));
            }
        }
    }

    @Override
    public String toString() {
        return key + "; " + location + "; " + date.toString() + "; " + aqi + "; " + values.toString();
    }
}

package tqs.airquality.model;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

public class AirStats {
    private String location;
    private double lat, lon;

    
    private Date date;
    
    private int aqi;
    private HashMap<String, Double> values;

    public AirStats(String location, double lat, double lon, Date date, int aqi) {
        this.location = location;
        this.date = date;
        this.lat = lat;
        this.lon = lon;
        this.aqi = aqi;

        this.values = new HashMap<>();
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

    @Override
    public String toString() {
        return location + "; " + date.toString() + "; " + aqi + "; " + values.toString();
    }
}

package tqs.airquality.http;

import java.io.IOException;
import java.util.Map;

import org.json.simple.parser.ParseException;

public interface IHttpClient {
    public String doGet(String url, Map<String, Object> headers) throws IOException, ParseException;
}
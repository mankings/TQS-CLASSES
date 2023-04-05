package tqs.airquality.http;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@Service
public class HttpClient implements IHttpClient {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public String doGet(String url, Map<String, Object> headers) throws IOException, ParseException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
    
            logger.log(Level.INFO, "[ HTTP ] GET {0} ", url);
    
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                request.setHeader(entry.getKey(), entry.getValue().toString());
            }
    
            try (CloseableHttpResponse response = client.execute(request)) {
                HttpEntity entity = response.getEntity();
                String resp = EntityUtils.toString(entity);
    
                logger.log(Level.INFO, "[ HTTP ] GOT {0}", resp);
    
                return resp;
            }
        }
    }
}

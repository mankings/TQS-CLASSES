
package mankings.tqs.demo;

import org.apache.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mankings.tqs.connection.TqsBasicHttpClient;
import mankings.tqs.geocoding.Address;
import mankings.tqs.geocoding.AddressResolver;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 *
 * @author ico
 */
public class GeocodeMainDemo {

    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * demo for address resolver
     * @param args the command line arguments
     */
    public static void main(String[] args) {


        try {
            AddressResolver resolver =new AddressResolver( new TqsBasicHttpClient());
            
            Optional<Address> result = resolver.findAddressForLocation( 40.633116,-8.658784);
            log.info("Result: ".concat( result.get().toString() ) );

        } catch (URISyntaxException | IOException | ParseException | org.json.simple.parser.ParseException ex) {
            log.error(String.valueOf(ex));
        }
    }
    
}

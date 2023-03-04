package mankings.tqs.integration;

import mankings.tqs.connection.*;
import mankings.tqs.geocoding.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class AddressResolverIT {
    AddressResolver addressResolver;

    @BeforeEach
    public void startup() {
        addressResolver = new AddressResolver(new TqsBasicHttpClient());
    }

    @Test
    public void findAddressForLocationTest() throws ParseException, IOException, URISyntaxException {
        
        double lat = 40.487709;
        double lon = -8.403229;
        
        Optional<Address> result = addressResolver.findAddressForLocation(lat, lon);
        Optional<Address> expected = Optional.of(new Address("Rua João de Deus Cristo", "Anadia", "", "3780-426", null));

        assertEquals(expected, result);
    }

    @Test
    public void findAddressForLocationBadCoordinatesTest() throws ParseException, IOException, URISyntaxException {

        double lat = 420;
        double lon = -420;
        
        assertThrows(IllegalArgumentException.class, () -> addressResolver.findAddressForLocation(lat, lon));
    }
}

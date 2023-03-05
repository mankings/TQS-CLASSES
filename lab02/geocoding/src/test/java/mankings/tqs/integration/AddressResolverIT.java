package mankings.tqs.integration;

import mankings.tqs.connection.TqsBasicHttpClient;
import mankings.tqs.geocoding.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import org.json.simple.parser.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddressResolverIT {

    AddressResolver resolver;

    @BeforeEach
    void startup() {
        resolver = new AddressResolver(new TqsBasicHttpClient());
    }

    @Test
    public void whenResolveSauce_returnCandieiraCity() throws ParseException, IOException, URISyntaxException {

        Optional<Address> result = resolver.findAddressForLocation(40.487714971980154, -8.403300261161956);

        Address expected = new Address( "Rua João de Deus Cristo", "Anadia", "", "3780-426", null);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }

    @Test
    public void whenBadCoordidates_throwIllegalArgumentException() throws IOException, URISyntaxException, ParseException {
        assertThrows(IllegalArgumentException.class, () -> resolver.findAddressForLocation(-420, 420));
    }
}

package mankings.tqs.geocoding;

import mankings.tqs.connection.ISimpleHttpClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AdressResolverTest {
    @InjectMocks
    AddressResolver addressResolver;

    @Mock
    ISimpleHttpClient httpClient;

    private String response = "{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"© 2022 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"© 2022 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.487709,\"lng\":-8.403229}},\"locations\":[{\"street\":\"Rua João de Deus Cristo\",\"adminArea6\":\"Candieira\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Anadia\",\"adminArea5Type\":\"City\",\"adminArea4\":\"Aveiro\",\"adminArea4Type\":\"County\",\"adminArea3\":\"\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3780-426\",\"geocodeQualityCode\":\"B1AAA\",\"geocodeQuality\":\"STREET\",\"dragPoint\":false,\"sideOfStreet\":\"L\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.48786,\"lng\":-8.40321},\"displayLatLng\":{\"lat\":40.48786,\"lng\":-8.40321},\"mapUrl\":\"\"}]}]}";

    @Test
    public void findAddressForLocationTest() throws ParseException, IOException, URISyntaxException {
        
        when(httpClient.doHttpGet(anyString())).thenReturn(response);
        
        double lat = 40.487709;
        double lon = -8.403229;
        Optional<Address> result = addressResolver.findAddressForLocation(lat, lon);

        Optional<Address> expected = Optional.of(new Address("Rua João de Deus Cristo", "Anadia", "", "3780-426", null));
        assertEquals(expected, result);
    }

    @Test
    public void findAddressForLocationBadCoordinatesTest() throws ParseException, IOException, URISyntaxException {
        when(httpClient.doHttpGet(anyString())).thenReturn(response);

        double lat = 420;
        double lon = -420;
        assertThrows(IllegalArgumentException.class, () -> addressResolver.findAddressForLocation(lat, lon));
    }
}

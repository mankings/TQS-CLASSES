package mankings.tqs.unit;

import mankings.tqs.connection.ISimpleHttpClient;
import mankings.tqs.geocoding.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import org.json.simple.parser.ParseException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {

    @InjectMocks
    AddressResolver resolver;

    @Mock
    ISimpleHttpClient httpClient;

    private String apiResponse = "{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"© 2022 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"© 2022 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.487709,\"lng\":-8.403229}},\"locations\":[{\"street\":\"Rua João de Deus Cristo\",\"adminArea6\":\"Candieira\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Anadia\",\"adminArea5Type\":\"City\",\"adminArea4\":\"Aveiro\",\"adminArea4Type\":\"County\",\"adminArea3\":\"\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3780-426\",\"geocodeQualityCode\":\"B1AAA\",\"geocodeQuality\":\"STREET\",\"dragPoint\":false,\"sideOfStreet\":\"L\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.48786,\"lng\":-8.40321},\"displayLatLng\":{\"lat\":40.48786,\"lng\":-8.40321},\"mapUrl\":\"\"}]}]}";

    @Test
    public void whenResolveSauce_returnCandieiraCity() throws ParseException, IOException, URISyntaxException {

        // SuT - AddressResolver
        // Service to Mock - HttpClient

        when(httpClient.doHttpGet(anyString())).thenReturn(apiResponse);
        Optional<Address> result = resolver.findAddressForLocation(40.633116,-8.658784);

        Address expected = new Address( "Rua João de Deus Cristo", "Anadia", "", "3780-426", null);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }

    @Test
    public void whenBadCoordidates_thenIllegalArgumentException() throws IOException, URISyntaxException, ParseException {
        assertThrows(IllegalArgumentException.class, () -> resolver.findAddressForLocation(-420, 420));
    }
}

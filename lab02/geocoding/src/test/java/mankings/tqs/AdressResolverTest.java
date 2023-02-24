package mankings.tqs;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AdressResolverTest {
    @Mock
    ISimpleHttpClient httpClient;

    @InjectMocks
    AddressResolver addressResolver;

    @Test
    public void testFindAddressForLocation() {
        double lat = 40.487709;
        double lon = -8.403229;

        when(httpClient.doHttpGet("https://www.mapquestapi.com/geocoding/v1/reverse?key=23kTyPF8SgAiOxKsVrHW6U7ZUx2v96QM&location=40.487709,-8.403229")).thenReturn("{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"© 2022 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"© 2022 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.487709,\"lng\":-8.403229}},\"locations\":[{\"street\":\"Rua João de Deus Cristo\",\"adminArea6\":\"Candieira\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Anadia\",\"adminArea5Type\":\"City\",\"adminArea4\":\"Aveiro\",\"adminArea4Type\":\"County\",\"adminArea3\":\"\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3780-426\",\"geocodeQualityCode\":\"B1AAA\",\"geocodeQuality\":\"STREET\",\"dragPoint\":false,\"sideOfStreet\":\"L\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.48786,\"lng\":-8.40321},\"displayLatLng\":{\"lat\":40.48786,\"lng\":-8.40321},\"mapUrl\":\"\"}]}]}");

        
    }
}

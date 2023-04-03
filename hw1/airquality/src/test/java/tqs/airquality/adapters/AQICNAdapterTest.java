package tqs.airquality.adapters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.airquality.http.IHttpClient;
import tqs.airquality.model.AirStats;

/*
 * again, using mockito to mock the replies to the http requests
 * so that we can isolate the adapter and properly test it
 */
@ExtendWith(MockitoExtension.class)
public class AQICNAdapterTest {
    @Mock
    private IHttpClient httpClient;

    @InjectMocks
    private AQICNAdapter adapter;
    
    private String todayLondonResponse = "{\"status\":\"ok\",\"data\":{\"aqi\":38,\"idx\":5724,\"attributions\":[{\"url\":\"http://uk-air.defra.gov.uk/\",\"name\":\"UK-AIR, air quality information resource - Defra, UK\",\"logo\":\"UK-Department-for-environment-food-and-rural-affairs.png\"},{\"url\":\"https://londonair.org.uk/\",\"name\":\"London Air Quality Network - Environmental Research Group, King's College London\",\"logo\":\"UK-London-Kings-College.png\"},{\"url\":\"https://waqi.info/\",\"name\":\"World Air Quality Index Project\"}],\"city\":{\"geo\":[51.5073509,-0.1277583],\"name\":\"London\",\"url\":\"https://aqicn.org/city/london\",\"location\":\"\"},\"dominentpol\":\"pm25\",\"iaqi\":{\"co\":{\"v\":1},\"h\":{\"v\":55.1},\"no2\":{\"v\":27},\"o3\":{\"v\":31.4},\"p\":{\"v\":1029},\"pm10\":{\"v\":18},\"pm25\":{\"v\":38},\"so2\":{\"v\":1.1},\"t\":{\"v\":11.2},\"w\":{\"v\":3.6}},\"time\":{\"s\":\"2023-04-03 15:00:00\",\"tz\":\"+01:00\",\"v\":1680534000,\"iso\":\"2023-04-03T15:00:00+01:00\"},\"forecast\":{\"daily\":{\"o3\":[{\"avg\":19,\"day\":\"2023-04-01\",\"max\":26,\"min\":14},{\"avg\":30,\"day\":\"2023-04-02\",\"max\":43,\"min\":25},{\"avg\":22,\"day\":\"2023-04-03\",\"max\":35,\"min\":11},{\"avg\":17,\"day\":\"2023-04-04\",\"max\":34,\"min\":4},{\"avg\":17,\"day\":\"2023-04-05\",\"max\":34,\"min\":2},{\"avg\":19,\"day\":\"2023-04-06\",\"max\":23,\"min\":14},{\"avg\":23,\"day\":\"2023-04-07\",\"max\":23,\"min\":22}],\"pm10\":[{\"avg\":11,\"day\":\"2023-04-01\",\"max\":14,\"min\":9},{\"avg\":11,\"day\":\"2023-04-02\",\"max\":14,\"min\":8},{\"avg\":15,\"day\":\"2023-04-03\",\"max\":18,\"min\":11},{\"avg\":21,\"day\":\"2023-04-04\",\"max\":28,\"min\":13},{\"avg\":29,\"day\":\"2023-04-05\",\"max\":37,\"min\":20},{\"avg\":15,\"day\":\"2023-04-06\",\"max\":25,\"min\":9},{\"avg\":14,\"day\":\"2023-04-07\",\"max\":14,\"min\":12}],\"pm25\":[{\"avg\":37,\"day\":\"2023-04-01\",\"max\":50,\"min\":25},{\"avg\":30,\"day\":\"2023-04-02\",\"max\":37,\"min\":21},{\"avg\":48,\"day\":\"2023-04-03\",\"max\":53,\"min\":37},{\"avg\":64,\"day\":\"2023-04-04\",\"max\":76,\"min\":48},{\"avg\":84,\"day\":\"2023-04-05\",\"max\":103,\"min\":63},{\"avg\":50,\"day\":\"2023-04-06\",\"max\":76,\"min\":35},{\"avg\":40,\"day\":\"2023-04-07\",\"max\":44,\"min\":40}],\"uvi\":[{\"avg\":0,\"day\":\"2022-10-24\",\"max\":0,\"min\":0}]}},\"debug\":{\"sync\":\"2023-04-04T00:40:06+09:00\"}}}";
    private String todayPortoResponse = "{\"status\":\"ok\",\"data\":{\"aqi\":20,\"idx\":8373,\"attributions\":[{\"url\":\"http://qualar.apambiente.pt/\",\"name\":\"Portugal -Agencia Portuguesa do Ambiente - Qualidade do Ar\",\"logo\":\"portugal-qualar.png\"},{\"url\":\"http://www.eea.europa.eu/themes/air/\",\"name\":\"European Environment Agency\",\"logo\":\"Europe-EEA.png\"},{\"url\":\"https://waqi.info/\",\"name\":\"World Air Quality Index Project\"}],\"city\":{\"geo\":[41.1475,-8.6588888888889],\"name\":\"Sobreiras-Lordelo do Ouro, Porto, Portugal\",\"url\":\"https://aqicn.org/city/portugal/porto/sobreiras-lordelo-do-ouro\",\"location\":\"\"},\"dominentpol\":\"pm25\",\"iaqi\":{\"dew\":{\"v\":12},\"h\":{\"v\":72},\"no2\":{\"v\":11.3},\"o3\":{\"v\":13.9},\"p\":{\"v\":1017},\"pm10\":{\"v\":12},\"pm25\":{\"v\":20},\"t\":{\"v\":17},\"w\":{\"v\":1.5},\"wg\":{\"v\":15.4}},\"time\":{\"s\":\"2023-04-03 10:00:00\",\"tz\":\"+01:00\",\"v\":1680516000,\"iso\":\"2023-04-03T10:00:00+01:00\"},\"forecast\":{\"daily\":{\"o3\":[{\"avg\":25,\"day\":\"2023-04-03\",\"max\":39,\"min\":13},{\"avg\":28,\"day\":\"2023-04-04\",\"max\":40,\"min\":17},{\"avg\":30,\"day\":\"2023-04-05\",\"max\":41,\"min\":26},{\"avg\":24,\"day\":\"2023-04-06\",\"max\":44,\"min\":13},{\"avg\":25,\"day\":\"2023-04-07\",\"max\":25,\"min\":19}],\"pm10\":[{\"avg\":22,\"day\":\"2023-04-03\",\"max\":27,\"min\":17},{\"avg\":17,\"day\":\"2023-04-04\",\"max\":27,\"min\":7},{\"avg\":12,\"day\":\"2023-04-05\",\"max\":21,\"min\":7},{\"avg\":20,\"day\":\"2023-04-06\",\"max\":30,\"min\":14},{\"avg\":29,\"day\":\"2023-04-07\",\"max\":30,\"min\":27}],\"pm25\":[{\"avg\":66,\"day\":\"2023-04-03\",\"max\":73,\"min\":55},{\"avg\":53,\"day\":\"2023-04-04\",\"max\":76,\"min\":23},{\"avg\":40,\"day\":\"2023-04-05\",\"max\":64,\"min\":23},{\"avg\":62,\"day\":\"2023-04-06\",\"max\":84,\"min\":48},{\"avg\":81,\"day\":\"2023-04-07\",\"max\":84,\"min\":81}]}},\"debug\":{\"sync\":\"2023-04-03T23:33:11+09:00\"}}}";
    private String unknownStationResponse = "{\"status\":\"error\",\"data\":\"Unknown station\"}";
    
    @BeforeEach
    public void setup(){
    }
    
    @AfterEach
    public void teardown() {
    }
    
    @Test
    public void today() throws Exception {
        Mockito.when(httpClient.doGet(anyString(), any())).thenReturn(todayLondonResponse);
        AirStats stats = adapter.today("london");

        assertThat(stats.getLocation()).isEqualTo("London");
        assertThat(stats.getLat()).isEqualTo(51.5073509);
        assertThat(stats.getLon()).isEqualTo(-0.1277583);
    }

    @Test
    public void todayMissingValues() throws Exception {
        Mockito.when(httpClient.doGet(anyString(), any())).thenReturn(todayPortoResponse);
        AirStats stats = adapter.today("porto");

        assertThat(stats.getLocation()).isEqualTo("Sobreiras-Lordelo do Ouro, Porto, Portugal");
        assertThat(stats.getValues().get("co")).isEqualTo(-1.0);
    }

    @Test
    public void todayBadLocation() throws Exception {
        Mockito.when(httpClient.doGet(anyString(), any())).thenReturn(unknownStationResponse);
        AirStats stats = adapter.today("qwerty");

        assertThat(stats).isEqualTo(null);
    }
}

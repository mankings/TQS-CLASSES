package tqs.airquality.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.airquality.adapters.AQICNAdapter;
import tqs.airquality.adapters.OpenWeatherAdapter;
import tqs.airquality.cache.AirCache;
import tqs.airquality.model.AirStats;

/*
 * here, we use mockito to mock return values from dependencies 
 * and assertj to assert that the return values from the subject under test are valid
 * this allows us to, once again, isolate the component under test - in this case, the service
 */
@ExtendWith(MockitoExtension.class)
class AirServiceTest {
    @Mock
    private AQICNAdapter aqicnAdapter;

    @Mock
    private OpenWeatherAdapter openWeatherAdapter;

    @Mock(lenient = true)
    private AirCache cache;

    @InjectMocks
    private AirService service;

    private AirStats dummyStats;
    private List<AirStats> dummyWeek;
    private List<AirStats> dummyHistory;

    @BeforeEach
    public void setup() {
        dummyStats = new AirStats("Coimbra", "Coimbra", 50.0, 50.0, new Date(), 12);
        dummyStats.setValues(15, 25, 35, 30, 40);

        dummyWeek = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            AirStats s = new AirStats("Coimbra", "Coimbra", 50.0, 50.0, new Date(), 12);
            s.setValues(i * 10, i * 10, i * 10, i * 10, i * 10);
            dummyWeek.add(s);
        }

        dummyHistory = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            AirStats s = new AirStats("Coimbra", "Coimbra", 50.0, 50.0, new Date(), 12);
            s.setValues(i * 10, i * 10, i * 10, i * 10, i * 10);
            dummyHistory.add(s);
        }

        Mockito.when(cache.get(anyString())).thenReturn(Optional.empty());
    }

    @AfterEach
    public void teardown() {
    }

    @Test
    void getTodayStats() throws Exception {
        Mockito.when(aqicnAdapter.today("Coimbra")).thenReturn(dummyStats);

        assertThat(service.today("Coimbra").getLat()).isEqualTo(50);
    }

    @Test
    void getTodayStatsBad() throws Exception {
        Mockito.when(aqicnAdapter.today("qwerty")).thenReturn(null);

        assertThat(service.today("qwerty")).isNull();
    }

    @Test
    void getForecastStats() throws Exception {
        Mockito.when(aqicnAdapter.today("Coimbra")).thenReturn(dummyStats);
        Mockito.when(openWeatherAdapter.forecast(anyDouble(), anyDouble())).thenReturn(dummyWeek);

        List<AirStats> lst = service.forecast("Coimbra");
        assertThat(lst).hasSize(4);
        assertThat(lst.get(2).getValues()).containsEntry("co", 30.0);
    }

    @Test
    void getForecastStatsBad() throws Exception {
        Mockito.when(aqicnAdapter.today("qwerty")).thenReturn(null);

        assertThat(service.forecast("qwerty")).isEmpty();
        Mockito.verify(openWeatherAdapter, VerificationModeFactory.times(0)).forecast(anyDouble(), anyDouble());
    }

    @Test
    void getHistoryStats() throws Exception {
        Mockito.when(aqicnAdapter.today("Coimbra")).thenReturn(dummyStats);
        Mockito.when(openWeatherAdapter.history(anyDouble(), anyDouble())).thenReturn(dummyHistory);

        List<AirStats> lst = service.history("Coimbra");
        assertThat(lst).hasSize(7);
        assertThat(lst.get(2).getValues()).containsEntry("co", 30.0);
    }

    @Test
    void getHistoryStatsBad() throws Exception {
        Mockito.when(aqicnAdapter.today("qwerty")).thenReturn(null);

        assertThat(service.history("qwerty")).isEmpty();
        Mockito.verify(openWeatherAdapter, VerificationModeFactory.times(0)).history(anyDouble(), anyDouble());
    }
}

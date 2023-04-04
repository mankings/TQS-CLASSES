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

import com.jayway.jsonpath.Option;

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
public class AirServiceTest {
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
        dummyStats = new AirStats("Coimbra", 50.0, 50.0, new Date(), 12);
        dummyStats.setValues(15, 25, 35, 30, 40);

        dummyWeek = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            AirStats s = new AirStats("Coimbra", 50.0, 50.0, new Date(), 12);
            s.setValues(i * 10, i * 10, i * 10, i * 10, i * 10);
            dummyWeek.add(s);
        }

        dummyHistory = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            AirStats s = new AirStats("Coimbra", 50.0, 50.0, new Date(), 12);
            s.setValues(i * 10, i * 10, i * 10, i * 10, i * 10);
            dummyHistory.add(s);
        }

        Mockito.when(cache.get(anyString())).thenReturn(Optional.empty());
    }

    @AfterEach
    public void teardown() {
    }

    @Test
    public void getTodayStats() throws Exception {
        Mockito.when(aqicnAdapter.today("Coimbra")).thenReturn(dummyStats);

        assertThat(service.today("Coimbra").getLat()).isEqualTo(50);
    }

    @Test
    public void getTodayStatsBad() throws Exception {
        Mockito.when(aqicnAdapter.today("qwerty")).thenReturn(null);

        assertThat(service.today("qwerty")).isEqualTo(null);
    }

    @Test
    public void getWeekStats() throws Exception {
        Mockito.when(aqicnAdapter.today("Coimbra")).thenReturn(dummyStats);
        Mockito.when(openWeatherAdapter.week(anyString(), anyDouble(), anyDouble())).thenReturn(dummyWeek);

        assertThat(service.week("Coimbra")).hasSize(7);
    }

    @Test
    public void getWeekStatsBad() throws Exception {
        Mockito.when(aqicnAdapter.today("qwerty")).thenReturn(null);

        assertThat(service.week("qwerty")).isEqualTo(null);
        Mockito.verify(openWeatherAdapter, VerificationModeFactory.times(0)).week(anyString(), anyDouble(), anyDouble());
    }

    @Test
    public void getHistoryStats() throws Exception {
        Mockito.when(aqicnAdapter.today("Coimbra")).thenReturn(dummyStats);
        Mockito.when(openWeatherAdapter.history(anyString(), anyDouble(), anyDouble())).thenReturn(dummyHistory);

        assertThat(service.history("Coimbra")).hasSize(14);
    }

    @Test
    public void getHistoryStatsBad() throws Exception {
        Mockito.when(aqicnAdapter.today("qwerty")).thenReturn(null);

        assertThat(service.history("qwerty")).isEqualTo(null);
        Mockito.verify(openWeatherAdapter, VerificationModeFactory.times(0)).history(anyString(), anyDouble(), anyDouble());
    }
}

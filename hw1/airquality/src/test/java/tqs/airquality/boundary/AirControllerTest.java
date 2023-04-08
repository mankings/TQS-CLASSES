package tqs.airquality.boundary;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import tqs.airquality.model.AirStats;
import tqs.airquality.model.CacheStats;
import tqs.airquality.service.AirService;

/*
 * using MockMvc, we isolate the controller from other components
 * so we can test the controller by itself
 */
@WebMvcTest(AirController.class)
class AirControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirService service;

    private AirStats dummyStats;
    
    @BeforeEach
    public void setup() {
        dummyStats = new AirStats("Coimbra", "Coimbra", 50, 50, new Date(), 420);
        dummyStats.setValues(10, 20, 30, 40, 50);
    }

    @AfterEach
    public void teardown() {
    }

    @Test
    void todayRequest() throws Exception {


        when(service.today(anyString())).thenReturn(dummyStats);

        mockMvc.perform(
            get("/api/candieira/today").content("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.location", is("Coimbra")))
            .andExpect(jsonPath("$.values.co", is(20.0)))
            .andExpect(jsonPath("$.aqi", is(420)));
    }

    @Test
    void todayBadRequest() throws Exception {
        when(service.today(anyString())).thenReturn(null);

        mockMvc.perform(
            get("/api/qwerty/today").content("application/json"))
            .andExpect(status().isNotFound());
    }

    @Test
     void forecastRequest() throws Exception {
        List<AirStats> lst = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            lst.add(dummyStats);
        }

        when(service.forecast(anyString())).thenReturn(lst);

        mockMvc.perform(
            get("/api/candieira/forecast").content("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(4)));
    }

    @Test
    void forecastBadRequest() throws Exception {
        when(service.forecast(anyString())).thenReturn(new ArrayList<AirStats>());

        mockMvc.perform(
            get("/api/qwerty/forecast").content("application/json"))
            .andExpect(status().isNotFound());
    }

    @Test
    void historyRequest() throws Exception {
        List<AirStats> lst = new ArrayList<>();
        for(int i = 0; i < 7; i++) {
            lst.add(dummyStats);
        }

        when(service.history(anyString())).thenReturn(lst);

        mockMvc.perform(
            get("/api/candieira/history").content("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(7)));
    }

    @Test
    void historyBadRequest() throws Exception {
        when(service.history(anyString())).thenReturn(new ArrayList<AirStats>());

        mockMvc.perform(
            get("/api/qwerty/history").content("application/json"))
            .andExpect(status().isNotFound());
    }

    @Test
    void cacheRequest() throws Exception {
        when(service.cacheStats()).thenReturn(new CacheStats(1, 2, 3, 4));

        mockMvc.perform(
            get("/api/cache").content("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.hits", is(2)));
    }
}

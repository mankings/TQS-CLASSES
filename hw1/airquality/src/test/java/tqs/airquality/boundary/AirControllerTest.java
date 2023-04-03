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
import tqs.airquality.service.AirService;

/*
 * using MockMvc, we isolate the controller from other components
 * so we can test the controller by itself
 */
@WebMvcTest(AirController.class)
public class AirControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirService service;

    private AirStats dummyStats;
    
    @BeforeEach
    public void setup() {
        dummyStats = new AirStats("coimbra", 50, 50, new Date());
        dummyStats.setValues(10, 20, 30, 40, 50);
    }

    @AfterEach
    public void teardown() {
    }

    @Test
    public void todayRequest() throws Exception {


        when(service.today(anyString())).thenReturn(dummyStats);

        mockMvc.perform(
            get("/api/candieira/today").content("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.location", is("coimbra")))
            .andExpect(jsonPath("$.values.co", is(20.0)));
    }

    @Test
    public void todayBadRequest() throws Exception {
        when(service.today(anyString())).thenReturn(null);

        mockMvc.perform(
            get("/api/qwerty/today").content("application/json"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void weekRequest() throws Exception {
        List<AirStats> lst = new ArrayList<>();
        for(int i = 0; i < 7; i++) {
            lst.add(dummyStats);
        }

        when(service.week(anyString())).thenReturn(lst);

        mockMvc.perform(
            get("/api/candieira/week").content("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(7)));
    }

    @Test
    public void weekBadRequest() throws Exception {
        when(service.week(anyString())).thenReturn(null);

        mockMvc.perform(
            get("/api/qwerty/week").content("application/json"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void historyRequest() throws Exception {
        List<AirStats> lst = new ArrayList<>();
        for(int i = 0; i < 14; i++) {
            lst.add(dummyStats);
        }

        when(service.history(anyString())).thenReturn(lst);

        mockMvc.perform(
            get("/api/candieira/history").content("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(14)));
    }

    @Test
    public void historyBadRequest() throws Exception {
        when(service.history(anyString())).thenReturn(null);

        mockMvc.perform(
            get("/api/qwerty/history").content("application/json"))
            .andExpect(status().isNotFound());
    }
}

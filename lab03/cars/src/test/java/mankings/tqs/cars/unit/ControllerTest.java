package mankings.tqs.cars.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;

import mankings.tqs.cars.*;

/*
 * in here we use a MockMvc to mock HTTP requests to our controller.
 * the behaviour of the service is mocked by Mockito
 * thus, we isolate the controller for testing
 * 
 * SuT - Controller
 * Mocked - Service
 */
@WebMvcTest(CarController.class)
public class ControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarService service;

    /*
     * in contrast with service testing, in here we set up the mocks inside the tests
     * no real difference, just experimenting
     */
    @BeforeEach
    public void startup() {
    }

    @Test
    public void whenPostValidCar_thenCreateCar() throws Exception {
        Car lupito = new Car("Wolkswagen", "Lupo");

        when(service.save(any())).thenReturn(lupito);

        mvc.perform(post("/newcar").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(lupito)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.maker", is("Wolkswagen")));

        verify(service, times(1)).save(any());
    }

    @Test
    public void whenGetAllCars_returnJsonArray() throws Exception {
        Car lupito = new Car("Wolkswagen", "Lupo");
        Car focus = new Car("Ford", "Focus");
        Car mcqueen = new Car("Lightning", "McQueen");

        List<Car> cars = Arrays.asList(lupito, focus, mcqueen);

        when(service.getAllCars()).thenReturn(cars);

        mvc.perform(get("/cars").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].maker", is("Wolkswagen")))
            .andExpect(jsonPath("$[1].maker", is("Ford")))
            .andExpect(jsonPath("$[2].maker", is("Lightning")));


        verify(service, times(1)).getAllCars();
    }

    @Test
    public void whenGetCarById_thenReturnCar() throws Exception {
        Car lupito = new Car("Wolkswagen", "Lupo");
        
        when(service.getCarDetails(anyLong())).thenReturn(lupito);
        
        mvc.perform(get("/cars/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.maker", is("Wolkswagen")));
        
        verify(service, times(1)).getCarDetails(anyLong());
    }

    @Test
    public void whenBadCarId_thenReturnNull() throws Exception {
        when(service.getCarDetails(anyLong())).thenReturn(null);
        
        mvc.perform(get("/cars/2").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(service, times(1)).getCarDetails(anyLong());
    }
}

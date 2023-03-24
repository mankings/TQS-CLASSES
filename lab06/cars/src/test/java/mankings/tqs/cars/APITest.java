package mankings.tqs.cars;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

/*
 * testing the api itself - the whole app
 * we use a TestRestTemplate to make the requests to our controller
 * no mocking is used
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "application-integrationtest.properties") 
public class APITest {

    @LocalServerPort
    int testPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository repository;

    @AfterEach
    public void reset() {
        repository.deleteAll();
    }

    @Test
    public void whenPostValidCar_thenCreateCar() {
        Car lupito = new Car("Wolkswagen", "Lupo");

        ResponseEntity<Car> response = restTemplate.postForEntity("/newcar", lupito, Car.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        List<Car> cars = repository.findAll();
        assertThat(cars).extracting(Car::getMaker).containsOnly(lupito.getMaker());
    }

    @Test
    public void whenGetAllCars_returnJsonArray() {
        Car lupito = new Car("Wolkswagen", "Lupo");
        Car focus = new Car("Ford", "Focus");
        Car mcqueen = new Car("Lightning", "McQueen");
        repository.save(lupito);
        repository.save(focus);
        repository.save(mcqueen);

        ResponseEntity<List<Car>> response = restTemplate.exchange("/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getMaker).containsExactly(lupito.getMaker(), focus.getMaker(), mcqueen.getMaker());
    }

    @Test
    public void whenGetCarById_returnCar() {
        Car lupito = new Car("Wolkswagen", "Lupo");
        repository.save(lupito);

        ResponseEntity<Car> response = restTemplate.exchange("/cars/1", HttpMethod.GET, null, Car.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Car> cars = repository.findAll();
        assertThat(cars).extracting(Car::getMaker).containsOnly(lupito.getMaker());
    }

    @Test
    public void whenGetNonExistingCarById_return404() {
        ResponseEntity<Car> response = restTemplate.exchange("/cars/420", HttpMethod.GET, null, Car.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        
        List<Car> cars = repository.findAll();
        assertThat(cars).extracting(Car::getMaker).isEmpty();
    }
}

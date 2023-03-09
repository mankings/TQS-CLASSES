package mankings.tqs.cars;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;


import mankings.tqs.cars.*;

/*
 * in here we use Mockito to mock repository responses and isolate testing to the service
 * SuT - service
 * Mock - repository
 */
@ExtendWith(MockitoExtension.class)
public class ServiceTest {
   @Mock(lenient = true)
   private CarRepository repository;
   
   @InjectMocks
   private CarService service;

   /*
    * in contrast with controller tests, we set up all tests in @BeforeEach
    * no real difference, just experimentating
    * we do need @Mock(lenient = true) for this one
    */
   @BeforeEach
   public void startup() {
      Car lupito = new Car("Wolkswagen", "Lupo");
      lupito.setCarId(420L);
      Car focus = new Car("Ford", "Focus");
      Car mcqueen = new Car("Lightning", "McQueen");

      when(repository.findByCarId(420L)).thenReturn(lupito);
      when(repository.findByCarId(-4L)).thenReturn(null);
      
      List<Car> cars = Arrays.asList(lupito, focus, mcqueen);
      when(repository.findAll()).thenReturn(cars);
   }

   @Test
   public void whenIdExists_thenReturnCar() {
      Car car = service.getCarDetails(420L);

      assertThat(car.getMaker()).isEqualTo("Wolkswagen");
   }

   @Test
   public void whenIdNotExists_thenSearchInvalid() {
      Car car = service.getCarDetails(-4L);

      assertThat(car).isNull();
   }

   @Test
   public void whenFullQuery_returnAllCars() {
      List<Car> cars = service.getAllCars();

      assertThat(cars)
         .hasSize(3)
         .extracting(Car::getMaker).contains("Wolkswagen", "Ford", "Lightning");

      verify(repository, times(1)).findAll();
   }
}

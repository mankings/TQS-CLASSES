package mankings.tqs.cars;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import mankings.tqs.cars.*;

/*
 * in here we isolate testing to the repository 
 * by using an TestEntityManager to persist and find data without involving caches
 */
@DataJpaTest
public class RepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository repository;

    @Test
    public void whenInvalidId_returnNull() {
        Car car = repository.findByCarId(-4L);
        assertThat(car).isNull();
    }

    @Test
    public void whenGivenArray_persistAndFindAll() {
        Car lupito = new Car("Wolkswagen", "Lupo");
        Car focus = new Car("Ford", "Focus");
        Car mcqueen = new Car("Lightning", "McQueen");

        
        entityManager.persist(lupito);
        entityManager.persist(focus);
        entityManager.persist(mcqueen);
        entityManager.flush();
        
        List<Car> cars = repository.findAll();
        
        assertThat(cars)
            .hasSize(3)
            .extracting(Car::getMaker).contains(lupito.getMaker(), focus.getMaker(), mcqueen.getMaker());
    }
}

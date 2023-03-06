package mankings.tqs.cars;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    public Car findByCarId(Long carId);
    public List<Car> findAll();
}

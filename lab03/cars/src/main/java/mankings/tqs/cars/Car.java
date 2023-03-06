package mankings.tqs.cars;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    private String maker;

    private String model;

    public Car() {
    }

    public Car(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        final Car other = (Car) obj;
        if (this.carId != other.carId) return false;
        if (this.maker != other.maker) return false;
        if (this.model != other.model) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return 4;
    }

    public Long getCarId() {
        return carId;
    }

    public String getMaker() {
        return maker;
    }

    public String getModel() {
        return model;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Car[" + "carId: " + this.carId + ", model: " + this.model + ", maker:" + this.maker + "]";
    }
}

package app.repository;

import app.domain.Car;

import java.util.List;

public interface CarRepository {

    List<Car> getAll();

    Car save(Car car);

    Car getCarByID  (long id);

    Car update(Car car);

    void deleteById(Long id);
}

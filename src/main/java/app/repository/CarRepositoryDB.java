package app.repository;

import app.domain.Car;

import java.util.List;

public class CarRepositoryDB implements CarRepository{
    @Override
    public List<Car> getAll() {
        return List.of();
    }

    @Override
    public Car save(Car car) {
        return null;
    }

    @Override
    public Car getCarByID(long id) {
        return null;
    }

    @Override
    public Car update(Car car) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}

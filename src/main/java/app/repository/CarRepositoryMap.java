package app.repository;

import app.domain.Car;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarRepositoryMap implements CarRepository {

    private final Map<Long, Car> database = new HashMap<>();
    private long currentId;

    public CarRepositoryMap() {
        save(new Car("Volkswagen", new BigDecimal(10000), 2010));
        save(new Car("Mazda", new BigDecimal(30000), 2015));
        save(new Car("Honda", new BigDecimal(50000), 2020));
        save(new Car("Волга", new BigDecimal(5000), 2000));
    }

    @Override
    public List<Car> getAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Car save(Car car) {
        car.setId(++currentId);
        database.put(currentId, car);
        return car;
    }

    @Override
    public Car getCarByID(long id) {
        return database.get(id);
    }

    @Override
    public Car update(Car car) {
        database.put(car.getId(), car);
        return car;
    }

    @Override
    public void deleteById(Long id) {
        database.remove(id);
    }
}

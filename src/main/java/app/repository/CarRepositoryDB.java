package app.repository;

import app.domain.Car;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static app.constants.Constants.*;

public class CarRepositoryDB implements CarRepository{
    @Override
    public List<Car> getAll() {
        try (Connection connection = getConnection()) {
            // TODO домашнее задание
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Car getCarByID(long id) {
        try (Connection connection = getConnection()) {
            // TODO домашнее задание
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Car save(Car car) {
        try (Connection connection = getConnection()) {

            String query = String.format("INSERT INTO cars (brand, price, year) VALUES ('%s', %s, %d);",
                    car.getBrand(), car.getPrice(), car.getYear());

            Statement statement = connection.createStatement();
            statement.execute(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();

            Long id = resultSet.getLong(1);
            car.setId(id);
            return car;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Car update(Car car) {
        try (Connection connection = getConnection()) {
            // TODO домашнее задание (изменению подлежит только цена)
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = getConnection()) {
            // TODO домашнее задание
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private Connection getConnection() {
        try {
            Class.forName(DB_DRIVER_PATH);

            // http://10.2.3.4:8080/cars?id=3
            // jdbc:postgresql://10.2.3.4:5432/g_33_cars?user=postgres&password=ppp77777

            String dbUrl = String.format("%s%s?user=%s&password=%s",
                    DB_ADDRESS, DB_NAME, DB_USERNAME, DB_PASSWORD);

            return DriverManager.getConnection(dbUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

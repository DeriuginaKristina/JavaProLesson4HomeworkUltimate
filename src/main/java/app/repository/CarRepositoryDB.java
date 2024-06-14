package app.repository;

import app.domain.Car;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static app.constants.Constants.*;

public class CarRepositoryDB implements CarRepository{
    @Override
    public List<Car> getAll() {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM cars";
            Statement statement = connection.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()){
                Long id = resultSet.getLong(1);
                String brand = resultSet.getString(2);
                BigDecimal price = resultSet.getBigDecimal(3);
                int year = resultSet.getInt(4);
                Car car = new Car(brand,price,year);
                car.setId(id);
                cars.add(car);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cars;
    }

    @Override
    public Car getCarByID(long findId) {
        try (Connection connection = getConnection()) {
            // TODO SELECT * FROM cars WHERE id = 3
            String query = "SELECT id, brand, price, year FROM cars WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, findId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Long id = resultSet.getLong(1);
                String brand = resultSet.getString(2);
                BigDecimal price = resultSet.getBigDecimal(3);
                int year = resultSet.getInt(4);
                Car car = new Car(brand,price,year);
                car.setId(id);

                return car;
            }
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
            // TODO change only price
            //UPDATE cars SET price=1234 WHERE id = 6
            String query = "UPDATE cars SET price = ? WHERE id = ? ";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setBigDecimal(1,car.getPrice());
            statement.setLong(2,car.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return car;
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = getConnection()) {
            // TODO delete from list Cars by id
            //DELETE FROM cars WHERE id = 6
            String query = "DELETE FROM cars WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
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

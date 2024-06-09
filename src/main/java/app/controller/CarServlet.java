package app.controller;

import app.domain.Car;
import app.repository.CarRepository;
import app.repository.CarRepositoryDB;
import app.repository.CarRepositoryMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class CarServlet extends HttpServlet {

//    private CarRepository repository = new CarRepositoryDB();
     private CarRepository repository = new CarRepositoryMap();

    // GET http://10.2.3.4:8080/cars
    // GET http://10.2.3.4:8080/cars?id=5

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Для получения из БД всех или одного автомобиля по id

        // req - объект запроса, из него мы можем извлечь всё, что прислал клиент
        // resp - объект ответа, который будет отправлен клиенту после того,
        //        как отработает наш метод. И мы можем в этот объект поместить всю
        //        информацию, которую мы хотим отправить клиенту в ответ на его запрос.

        String idParam = req.getParameter("id");
        if (idParam != null) {
            //get to find one car
            long id = Long.parseLong(idParam);//TODO insert try catch block
            Car car = repository.getCarByID(id);
            resp.getWriter().write(car.toString()); //TODO insert try catch block,NullPointerException

        }else{
            //get to find all cars
            List<Car> cars = repository.getAll();
            cars.forEach(car -> {
                try {
                    resp.getWriter().write(car.toString() + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        // TODO Домашнее задание:
        // Реализовать получение одного автомобиля по id
//        Map<String, String[]> parameterMap = req.getParameterMap();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Для сохранения нового автомобиля в БД

        ObjectMapper mapper = new ObjectMapper();
        Car car = mapper.readValue(req.getReader(), Car.class);
        repository.save(car);
        resp.getWriter().write(car.toString());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Для изменения существующего автомобиля в БД

        // TODO Домашнее задание:
        // Реализовать изменение объекта автомобиля в БД
        // (при этом меняться должна только цена)
        String id = req.getParameter("id");
        String price = req.getParameter("price");
        Car car = repository.getCarByID(Long.parseLong(id));//TODO try catch number format Exception
        car.setPrice(new BigDecimal(Long.parseLong(price)));
        repository.update(car);
        resp.getWriter().write(car.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Для удаления автомобиля из БД

        // TODO Домашнее задание:
        // Реализовать удаление автомобиля из БД по id
        String id = req.getParameter("id");
        repository.deleteById(Long.parseLong(id));//TODO try catch number format Exception
        resp.getWriter().write("Сar with id: "+ id + " is deleted ");

    }
}

package taxiservice.controllers.cars;

import taxiservice.Application;
import taxiservice.lib.Injector;
import taxiservice.models.Car;
import taxiservice.models.Manufacturer;
import taxiservice.service.CarService;
import taxiservice.service.ManufactureService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateCarController extends HttpServlet {
    private static final Injector injector =
            Injector.getInstance(Application.class.getPackageName());
    private static final CarService carService =
            (CarService) injector.getInstance(CarService.class);
    private static final ManufactureService manufactureService =
            (ManufactureService) injector.getInstance(ManufactureService.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/cars/create_car.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String model = req.getParameter("model");
        String manufactureId = req.getParameter("manufacture id");
        Manufacturer manufacturer = manufactureService.get(Long.parseLong(manufactureId));
        Car car = new Car(model, manufacturer);
        carService.create(car);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}

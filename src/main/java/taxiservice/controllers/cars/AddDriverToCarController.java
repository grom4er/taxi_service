package taxiservice.controllers.cars;

import taxiservice.Application;
import taxiservice.lib.Injector;
import taxiservice.models.Car;
import taxiservice.models.Driver;
import taxiservice.service.CarService;
import taxiservice.service.DriverService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddDriverToCarController extends HttpServlet {
    private static final Injector injector =
            Injector.getInstance(Application.class.getPackageName());
    private static final CarService carService =
            (CarService) injector.getInstance(CarService.class);
    private static final DriverService driverService =
            (DriverService) injector.getInstance(DriverService.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/cars/add_driver_to_car.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String carID = req.getParameter("carID");
        String driverID = req.getParameter("driverID");
        Car car = carService.get(Long.valueOf(carID));
        Driver driver = driverService.get(Long.valueOf(driverID));
        carService.addDriverToCar(driver, car);
        resp.sendRedirect(req.getContextPath() + "/cars/");
    }
}

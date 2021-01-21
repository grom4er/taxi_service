package taxiservice.controllers.drivers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import taxiservice.Application;
import taxiservice.lib.Injector;
import taxiservice.models.Driver;
import taxiservice.service.DriverService;

public class GetAllDriversController extends HttpServlet {
    private static final Injector injector =
            Injector.getInstance(Application.class.getPackageName());
    private static final DriverService driverService =
            (DriverService) injector.getInstance(DriverService.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Driver> drivers = driverService.getAll();
        req.setAttribute("drivers", drivers);
        req.getRequestDispatcher("/WEB-INF/drivers/all_drivers.jsp").forward(req, resp);
    }
}

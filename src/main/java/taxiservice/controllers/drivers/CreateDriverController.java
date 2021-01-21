package taxiservice.controllers.drivers;

import taxiservice.Application;
import taxiservice.lib.Injector;
import taxiservice.models.Driver;
import taxiservice.service.DriverService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateDriverController extends HttpServlet {
    private static final Injector injector =
            Injector.getInstance(Application.class.getPackageName());
    private static final DriverService driverService =
            (DriverService) injector.getInstance(DriverService.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/drivers/create_driver.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        String license = req.getParameter("license");
        Driver driver = new Driver(name, license);
        driverService.create(driver);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
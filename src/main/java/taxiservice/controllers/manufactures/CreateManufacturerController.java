package taxiservice.controllers.manufactures;

import taxiservice.Application;
import taxiservice.lib.Injector;
import taxiservice.models.Driver;
import taxiservice.models.Manufacturer;
import taxiservice.service.DriverService;
import taxiservice.service.ManufactureService;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateManufacturerController extends HttpServlet {
    private static final Injector injector =
            Injector.getInstance(Application.class.getPackageName());
    private static final ManufactureService manufactureService =
            (ManufactureService) injector.getInstance(ManufactureService.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/manufacturer/create_manufacturer.jsp\"").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        String country = req.getParameter("country");
        Manufacturer manufacturer = new Manufacturer(name, country);
        manufactureService.create(manufacturer);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}


package taxiservice.controllers.drivers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import taxiservice.Application;
import taxiservice.exception.AuthenticationException;
import taxiservice.lib.Injector;
import taxiservice.models.Driver;
import taxiservice.security.AuthenticationService;

public class LoginController extends HttpServlet {
    private static final String DRIVER_ID = "driver_id";
    private static final Injector injector =
            Injector.getInstance(Application.class.getPackageName());
    private static final AuthenticationService authenticationService =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/drivers/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String pwd = req.getParameter("pwd");
        try {
            Driver driver = authenticationService.login(login, pwd);
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute(DRIVER_ID, driver.getId());
        } catch (AuthenticationException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/drivers/login.jsp").forward(req, resp);
        }
        resp.sendRedirect(req.getContextPath() + "/index");
    }
}

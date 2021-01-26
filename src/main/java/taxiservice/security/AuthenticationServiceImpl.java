package taxiservice.security;

import java.util.Optional;
import taxiservice.exception.AuthenticationException;
import taxiservice.lib.Inject;
import taxiservice.lib.Service;
import taxiservice.models.Driver;
import taxiservice.service.DriverService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private DriverService driverService;

    @Override
    public Driver login(String login, String pwd) throws AuthenticationException {
        Optional<Driver> driverFromDB = driverService.findByLogin(login);
        if (driverFromDB.isPresent() && driverFromDB.get().getPassword().equals(pwd)) {
            return driverFromDB.get();
        }
        throw new AuthenticationException("Incorrect username or password");
    }
}

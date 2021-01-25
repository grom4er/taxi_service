package taxiservice.security;

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
        Driver driverFromBd = driverService.findByLogin(login).orElseThrow(
                () -> new AuthenticationException("Incorrect login or password"));
        if (driverFromBd.getPassword().equals(pwd)) {
            return driverFromBd;
        }
        throw new AuthenticationException("Incorrect login or password");
    }
}

package taxiservice.security;

import taxiservice.exception.AuthenticationException;
import taxiservice.models.Driver;

public interface AuthenticationService {
    Driver login(String login, String pwd) throws AuthenticationException;
}

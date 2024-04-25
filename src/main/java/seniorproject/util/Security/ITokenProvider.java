package seniorproject.util.Security;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import seniorproject.models.concretes.Role;
import seniorproject.util.Security.AccessToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface ITokenProvider {


    AccessToken createToken(String username, Set<Role> roles);
    boolean validateToken(AccessToken accessToken);
    AccessToken getTokenFromHeader(HttpServletRequest httpServletRequest);
    Authentication getAuthentication(AccessToken accessToken);
}
package seniorproject.util.Security.jwt;
import seniorproject.util.Security.AccessToken;
import seniorproject.util.Security.SecretKey;
import seniorproject.models.concretes.Role;

import java.util.Set;

public interface IJwtTokenHelper {
    String generateJwtToken(SecretKey secretKey, String username, Set<Role> roles);
    boolean validateJwtToken(SecretKey secretKey,AccessToken accessToken);
    String getUsernameFromJwtToken(SecretKey secretKey,AccessToken accessToken);
}

package seniorproject.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import seniorproject.util.Security.AccessToken;
import seniorproject.models.dto.UserRegisterDto;
import seniorproject.models.dto.UserLoginDto;

@Service
public interface AuthService {
    AccessToken register(UserRegisterDto userRegisterDto);

    AccessToken login(UserLoginDto userLoginDto);


}
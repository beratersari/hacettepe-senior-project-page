package seniorproject.service;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seniorproject.exceptions.security.CustomSecurityException;
import seniorproject.models.concretes.Role;
import seniorproject.models.concretes.User;
import seniorproject.models.dto.UserRegisterDto;
import seniorproject.repository.RoleRepository;
import seniorproject.repository.UserRepository;
import seniorproject.util.Constants.ApiMessages;
import seniorproject.util.Security.AccessToken;
import seniorproject.util.Security.ITokenProvider;
import seniorproject.models.dto.UserLoginDto;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {
    @Autowired
    private ITokenProvider tokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AccessToken register(UserRegisterDto userRegisterDto) {
        checkUserExistsWithUserName(userRegisterDto.getUsername());

        User user = new User();
        user.setEmail(userRegisterDto.getEmail());
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setRoles(getRoles(userRegisterDto.getRoles()));

        userRepository.save(user);

        String username = user.getUsername();
        Set<Role> roles = user.getRoles();

        return tokenProvider.createToken(username,roles);
    }



    @Override
    public AccessToken login(UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            Set<Role> roles = userRepository.findByUsername(username).get().getRoles();
            User user = userRepository.findByUsername(username).get();
            return tokenProvider.createToken(username,roles);

        }catch (AuthenticationException exception) {
            throw new CustomSecurityException(ApiMessages.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST);

        }

    }
    private void checkUserExistsWithUserName(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new CustomSecurityException(ApiMessages.USER_ALREADY_EXISTS,HttpStatus.BAD_REQUEST);
        }
    }
    private Set<Role> getRoles(String [] roles){
        Set<Role> userRoles = new HashSet<>();
        for(String role : roles) {
            userRoles.add(roleRepository.findByName(role).get());
        }
        return userRoles;
    }

}

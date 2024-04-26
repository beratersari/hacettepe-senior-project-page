package seniorproject.business.concretes;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import seniorproject.business.abstracts.UserService;
import seniorproject.core.utilities.results.DataResult;
import org.springframework.security.core.userdetails.User;
import seniorproject.core.utilities.results.SuccessDataResult;

@Service
public class UserManager implements UserService {

    @Override
    public DataResult<User> profile(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        return new SuccessDataResult<>(userDetails, "User profile listed.");

    }
}

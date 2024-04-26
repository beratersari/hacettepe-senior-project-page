package seniorproject.business.abstracts;

import seniorproject.core.utilities.results.DataResult;
import org.springframework.security.core.userdetails.User;

public interface UserService {

    DataResult<User> profile();
}

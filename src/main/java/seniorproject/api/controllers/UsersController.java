package seniorproject.api.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seniorproject.business.abstracts.UserService;
import seniorproject.core.utilities.results.DataResult;
import org.springframework.security.core.userdetails.User;


@RestController
@RequestMapping("/api/users")

public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        super();
        this.userService = userService;
    }


    @GetMapping("/getProfile")
    public DataResult<User> profile() {
        return this.userService.profile();

    }
}

package seniorproject.api.controllers;

import seniorproject.business.abstracts.UserService;

public class UsersController {
    int USER_TYPE_STUDENT = 1;
    int USER_TYPE_PROFESSOR = 2;
    int USER_TYPE_ADMIN = 3;

    private final UserService userService;


    public UsersController(UserService userService) {
        this.userService = userService;
    }
}

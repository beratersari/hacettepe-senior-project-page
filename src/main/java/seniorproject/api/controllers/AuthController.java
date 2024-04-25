package seniorproject.api.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seniorproject.models.dto.UserLoginDto;

import seniorproject.models.dto.UserRegisterDto;
import seniorproject.repository.UserRepository;
import seniorproject.service.AuthService;
import seniorproject.util.Security.AccessToken;
@RestController
@RequestMapping("/api/auth")

public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AccessToken> register(@RequestBody UserRegisterDto userRegisterDto) {
        AccessToken accessToken =  authService.register(userRegisterDto);
        return ResponseEntity.ok(accessToken);

    }
    @PostMapping("/login")
    public ResponseEntity<AccessToken> login(@RequestBody UserLoginDto userLoginDto) {
        AccessToken accessToken = authService.login(userLoginDto);
        return ResponseEntity.ok(accessToken);
    }




}
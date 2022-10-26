package at.ac.fhcampuswien.foodaddicts.controller;

import at.ac.fhcampuswien.foodaddicts.dto.LoginRequest;
import at.ac.fhcampuswien.foodaddicts.dto.LoginResponse;
import at.ac.fhcampuswien.foodaddicts.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(loginService.login(loginRequest));
    }
}

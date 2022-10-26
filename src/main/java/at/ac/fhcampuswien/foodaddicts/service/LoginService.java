package at.ac.fhcampuswien.foodaddicts.service;

import at.ac.fhcampuswien.foodaddicts.dto.LoginRequest;
import at.ac.fhcampuswien.foodaddicts.dto.LoginResponse;
import at.ac.fhcampuswien.foodaddicts.dto.RegistrationRequest;
import at.ac.fhcampuswien.foodaddicts.model.AppUser;
import at.ac.fhcampuswien.foodaddicts.model.AppUserRole;
import at.ac.fhcampuswien.foodaddicts.util.EmailValidator;
import at.ac.fhcampuswien.foodaddicts.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

        AppUser user = (AppUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(user);
        return new LoginResponse(user.getId(), user.getUsername(), user.getAppUserRole(), token);
    }
}
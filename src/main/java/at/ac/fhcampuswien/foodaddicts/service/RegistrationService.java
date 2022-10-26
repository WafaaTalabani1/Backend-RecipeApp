package at.ac.fhcampuswien.foodaddicts.service;

import at.ac.fhcampuswien.foodaddicts.dto.RegistrationRequest;
import at.ac.fhcampuswien.foodaddicts.model.AppUser;
import at.ac.fhcampuswien.foodaddicts.model.AppUserRole;
import at.ac.fhcampuswien.foodaddicts.util.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;


    public AppUser register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.
                test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }
}
package at.ac.fhcampuswien.foodaddicts.controller;

import at.ac.fhcampuswien.foodaddicts.dto.RegistrationRequest;
import at.ac.fhcampuswien.foodaddicts.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1/registration")
public class RegistrationController {


    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request).getId().toString();
    }
}

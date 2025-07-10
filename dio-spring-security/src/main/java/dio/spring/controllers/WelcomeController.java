package dio.spring.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping()
    public String welcome() {
        return "Welcome to the Spring Security Application!";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('MANAGERS','USERS')")
    public String users() {
        return "Authorized User";
    }

    @GetMapping("/managers")
    @PreAuthorize("hasRole('MANAGERS')")
    public String managers() {
        return "Authorized Manager";
    }
}

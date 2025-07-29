package dio.spring.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping()
    public String welcome() {
        return "Welcome to the Spring Security Application!";
    }

    @GetMapping("/users")
    public String users() {
        return "Authorized User";
    }

    @GetMapping("/managers")
    public String managers() {
        return "Authorized Manager";
    }
}

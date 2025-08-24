package dio.spring.security.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dio.spring.security.jwt.model.User;
import dio.spring.security.jwt.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    public void createUser(User user) {
        String pass = user.getPassword();
        //Cryptographing before save on db
        user.setPassword(encoder.encode(pass));
        repository.save(user);
    }
}

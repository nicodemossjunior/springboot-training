package dio.spring.security.init;

import dio.spring.security.model.User;
import dio.spring.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StartApplication implements CommandLineRunner {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        User user = repository.findByUsername("admin");
        if (user == null) {
            user = new User();
            user.setName("ADMIN");
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("master123"));
            user.getRoles().add("MANAGERS");
            repository.save(user);
        }

        user = repository.findByUsername("user");
        if (user == null) {
            user = new User();
            user.setName("USER");
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.getRoles().add("USERS");
            repository.save(user);
        }
    }

}

package dio.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {

    /*
     * The bean userDetailsService is used to create an in-memory user store.
     * It creates two users: user and admin with their respective roles.
     * The passwords are encoded using the PasswordEncoder bean.
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        var user = User.withUsername("user")
            .password(encoder.encode("user123"))
            .roles("USERS")
            .build();

        var manager = User.withUsername("admin")
            .password(encoder.encode("admin123"))
            .roles("MANAGERS")
            .build();

        return new InMemoryUserDetailsManager(user, manager);
    }

    /*
     * The bean PasswordEncoder is used to encode passwords.
     * BCryptPasswordEncoder is a strong password hashing function.
     * It is recommended to use BCryptPasswordEncoder for production applications.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/").permitAll()
                .requestMatchers("/managers/**").hasRole("MANAGERS")
                .requestMatchers("/user/**").hasRole("USERS,MANAGERS")
                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
    
}

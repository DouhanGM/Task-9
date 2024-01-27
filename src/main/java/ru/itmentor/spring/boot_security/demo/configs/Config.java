package ru.itmentor.spring.boot_security.demo.configs;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itmentor.spring.boot_security.demo.service.UserDetailsServiceImpl;

@Configuration
public class Config {

    private final UserDetailsServiceImpl usdi;
    private final PasswordEncoder passwordEncoder;

    public Config(UserDetailsServiceImpl usdi, PasswordEncoder passwordEncoder) {
        this.usdi = usdi;
        this.passwordEncoder = passwordEncoder;
    }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(usdi).passwordEncoder(passwordEncoder);
    }




}

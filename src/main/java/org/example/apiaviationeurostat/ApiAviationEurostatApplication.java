package org.example.apiaviationeurostat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ApiAviationEurostatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiAviationEurostatApplication.class, args);
    }

}

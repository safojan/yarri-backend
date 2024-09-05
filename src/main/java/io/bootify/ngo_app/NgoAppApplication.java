package io.bootify.ngo_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class NgoAppApplication {

    public static void main(final String[] args) {
        SpringApplication.run(NgoAppApplication.class, args);
    }

}

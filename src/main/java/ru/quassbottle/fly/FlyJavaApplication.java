package ru.quassbottle.fly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class FlyJavaApplication {
    public static void main(String[] args) {
        //System.out.println(Encoders.BASE64.encode(Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded()));
        var context = SpringApplication.run(FlyJavaApplication.class, args);
    }
}

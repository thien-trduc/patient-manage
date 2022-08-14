package thien;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import thien.service.SendGridEmailService;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
@EnableJpaAuditing
public class BenhVienApplication {
    public static void main(String[] args) {
        SpringApplication.run(BenhVienApplication.class, args);
    }
}

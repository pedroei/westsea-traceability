package ipvc.estg.westseatraceability;

import ipvc.estg.westseatraceability.dto.CreateUserDto;
import ipvc.estg.westseatraceability.enumeration.RoleEnum;
import ipvc.estg.westseatraceability.repository.UserRepository;
import ipvc.estg.westseatraceability.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class WestseaTraceabilityApplication {

    public static void main(String[] args) {
        SpringApplication.run(WestseaTraceabilityApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService service, UserRepository repository) {
        return args -> {
            var adminUser = new CreateUserDto("Administrator", "admin", "1234", List.of(RoleEnum.ROLE_ADMIN));
            boolean empty = repository.findByUsername(adminUser.getUsername()).isEmpty();
            if (empty) {
                service.saveUser(adminUser);
            }
        };
    }
}

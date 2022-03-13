package ipvc.estg.westseatraceability;

import ipvc.estg.westseatraceability.dto.CreateUserDto;
import ipvc.estg.westseatraceability.model.RoleEnum;
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
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveUser(new CreateUserDto("Administrator", "admin", "1234", List.of(RoleEnum.ROLE_ADMIN)));
        };
    }
}

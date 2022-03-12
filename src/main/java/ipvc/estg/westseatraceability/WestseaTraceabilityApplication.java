package ipvc.estg.westseatraceability;

import ipvc.estg.westseatraceability.model.Role;
import ipvc.estg.westseatraceability.model.User;
import ipvc.estg.westseatraceability.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.UUID;

@SpringBootApplication
public class WestseaTraceabilityApplication {

    public static void main(String[] args) {
        SpringApplication.run(WestseaTraceabilityApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, UUID.randomUUID(), "ROLE_CLIENT"));
            userService.saveRole(new Role(null, UUID.randomUUID(), "ROLE_ADMIN"));
            userService.saveRole(new Role(null, UUID.randomUUID(), "ROLE_EMPLOYEE"));

            User user1 = userService.saveUser(new User(null, UUID.randomUUID(), "John Doe", "jdoe", "1234", new ArrayList<>()));
            User user2 = userService.saveUser(new User(null, UUID.randomUUID(), "Jane Doe", "janedoe", "1234", new ArrayList<>()));
            User user3 = userService.saveUser(new User(null, UUID.randomUUID(), "Jack Sparrow", "blackpearl", "1234", new ArrayList<>()));
            User user = userService.saveUser(new User(null, UUID.randomUUID(), "Tony Stark", "irontony", "1234", new ArrayList<>()));

            userService.addRoleToUser(user1.getUuid(), "ROLE_EMPLOYEE");
            userService.addRoleToUser(user1.getUuid(), "ROLE_ADMIN");
            userService.addRoleToUser(user2.getUuid(), "ROLE_EMPLOYEE");
            userService.addRoleToUser(user3.getUuid(), "ROLE_EMPLOYEE");
            userService.addRoleToUser(user.getUuid(), "ROLE_CLIENT");
        };
    }
}

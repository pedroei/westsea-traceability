package ipvc.estg.westseatraceability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WestseaTraceabilityApplication {

    public static void main(String[] args) {
        SpringApplication.run(WestseaTraceabilityApplication.class, args);
    }

//    @Bean
//    CommandLineRunner run(UserService userService) {
//        return args -> {
//
//            User user1 = userService.saveUser(new User(null, "John Doe", "jdoe", "1234", new ArrayList<>()));
//            User user2 = userService.saveUser(new User(null, "Jane Doe", "janedoe", "1234", new ArrayList<>()));
//            User user3 = userService.saveUser(new User(null, "Jack Sparrow", "blackpearl", "1234", new ArrayList<>()));
//            User user = userService.saveUser(new User(null, "Tony Stark", "irontony", "1234", new ArrayList<>()));
//
//            userService.addRoleToUser(user1.getId(), RoleEnum.ROLE_EMPLOYEE);
//            userService.addRoleToUser(user1.getId(), RoleEnum.ROLE_ADMIN);
//            userService.addRoleToUser(user2.getId(), RoleEnum.ROLE_EMPLOYEE);
//            userService.addRoleToUser(user3.getId(), RoleEnum.ROLE_EMPLOYEE);
//            userService.addRoleToUser(user.getId(), RoleEnum.ROLE_CLIENT);
//        };
//    }
}

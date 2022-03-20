package ipvc.estg.westseatraceability.model;

import ipvc.estg.westseatraceability.enumeration.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    @NonNull
    private String name;

    @NonNull
    @Indexed(unique = true)
    private String username;

    @NonNull
    private String password;

    @NonNull
    private Set<RoleEnum> roles = new HashSet<>();
}

package ipvc.estg.westseatraceability.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import javax.persistence.Id;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDesignation {

    @Id
    private String id;

    @NonNull
    @Indexed(unique = true)
    private String designation;
}

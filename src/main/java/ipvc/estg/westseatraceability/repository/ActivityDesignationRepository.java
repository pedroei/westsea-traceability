package ipvc.estg.westseatraceability.repository;

import ipvc.estg.westseatraceability.model.ActivityDesignation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ActivityDesignationRepository extends MongoRepository<ActivityDesignation, String> {
    Optional<ActivityDesignation> findByDesignation(String designation);
}

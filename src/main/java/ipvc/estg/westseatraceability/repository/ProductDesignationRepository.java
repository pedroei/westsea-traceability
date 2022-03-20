package ipvc.estg.westseatraceability.repository;

import ipvc.estg.westseatraceability.model.ProductDesignation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductDesignationRepository extends MongoRepository<ProductDesignation, String> {
    Optional<ProductDesignation> findByDesignation(String designation);
}

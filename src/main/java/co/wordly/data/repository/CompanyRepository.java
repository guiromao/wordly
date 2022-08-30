package co.wordly.data.repository;

import co.wordly.data.entity.CompanyEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CompanyRepository extends MongoRepository<CompanyEntity, String>, CompanyRepositoryCustom {

    Optional<CompanyEntity> findByName(String name);

}

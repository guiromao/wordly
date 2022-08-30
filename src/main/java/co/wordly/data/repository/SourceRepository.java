package co.wordly.data.repository;

import co.wordly.data.entity.SourceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SourceRepository extends MongoRepository<SourceEntity, String>, SourceRepositoryCustom {

    Optional<SourceEntity> findByName(String name);

}

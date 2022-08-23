package co.wordly.data.repository;

import co.wordly.data.entity.SourceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SourceRepository extends MongoRepository<SourceEntity, String>, SourceRepositoryCustom {

}

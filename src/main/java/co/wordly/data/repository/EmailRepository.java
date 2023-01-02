package co.wordly.data.repository;

import co.wordly.data.entity.EmailEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailRepository extends MongoRepository<EmailEntity, String>, EmailRepositoryCustom {

}

package co.wordly.data.repository;

import co.wordly.data.entity.JobEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<JobEntity, String>, JobRepositoryCustom {

}

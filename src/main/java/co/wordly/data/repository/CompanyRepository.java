package co.wordly.data.repository;

import co.wordly.data.entity.CompanyEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<CompanyEntity, String> {

}

package co.wordly.data.repository;

import co.wordly.data.entity.CompanyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Set;
import java.util.stream.Collectors;

public class CompanyRepositoryImpl implements CompanyRepositoryCustom {

    private static final String KEY_NAME = "name";

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CompanyRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Set<String> findExisting(Set<String> companyNames) {
        Criteria nameCriteria = Criteria.where(KEY_NAME).in(companyNames);
        Query query = new Query(nameCriteria);

        return mongoTemplate.find(query, CompanyEntity.class).stream()
                .map(CompanyEntity::getName)
                .collect(Collectors.toSet());
    }
}

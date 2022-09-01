package co.wordly.data.repository;

import co.wordly.data.entity.CompanyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CompanyRepositoryImpl implements CompanyRepositoryCustom {

    private static final String KEY_NAME = "name";
    private static final String KEY_SOURCE_RELATIONS = "sourceRelations";

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CompanyRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<CompanyEntity> findByNames(Set<String> companyNames) {
        Criteria namesCriteria = Criteria.where(KEY_NAME).in(companyNames);
        Query query = new Query(namesCriteria);

        return mongoTemplate.find(query, CompanyEntity.class);
    }

    @Override
    public List<CompanyEntity> findCompaniesToAddId(Set<String> companyNames, String sourceId) {
        Criteria criteria = Criteria.where(KEY_NAME).in(companyNames)
                .and(KEY_SOURCE_RELATIONS + "." + sourceId).exists(false);
        Query query = new Query(criteria);

        return mongoTemplate.find(query, CompanyEntity.class);
    }

    @Override
    public List<CompanyEntity> findBySource(String sourceId, Set<String> sourceCompanyIds) {
        Criteria sourceCriteria = Criteria.where(KEY_SOURCE_RELATIONS + "." + sourceId).exists(true);
        Query query = new Query(sourceCriteria);

        return mongoTemplate.find(query, CompanyEntity.class);
    }

}

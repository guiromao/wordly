package co.wordly.data.repository;

import co.wordly.data.entity.SourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class SourceRepositoryImpl implements SourceRepositoryCustom {

    private static final String KEY_NAME = "name";

    private final MongoTemplate mongoTemplate;

    @Autowired
    public SourceRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public List<SourceEntity> findByNames(Set<String> names) {
        Criteria namesCriteria = Criteria.where(KEY_NAME).in(names);
        Query query = new Query(namesCriteria);

        return mongoTemplate.find(query, SourceEntity.class);
    }

}

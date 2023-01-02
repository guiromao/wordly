package co.wordly.data.repository;

import co.wordly.data.entity.EmailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmailRepositoryImpl implements EmailRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public EmailRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<EmailEntity> findByOffsetAndLimit(int offset, int limit) {
        final Query query = new Query().skip(offset).limit(limit);

        return mongoTemplate.find(query, EmailEntity.class);
    }
}

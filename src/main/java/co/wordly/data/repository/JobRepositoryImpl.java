package co.wordly.data.repository;

import co.wordly.data.entity.JobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class JobRepositoryImpl implements JobRepositoryCustom {

    private static final String KEY_SOURCE_ID = "sourceId";
    private static final String KEY_SOURCE_JOB_ID = "sourceJobId";

    private final MongoTemplate mongoTemplate;

    @Autowired
    public JobRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean exists(JobEntity jobEntity) {
        Criteria existsCriteria = Criteria.where(KEY_SOURCE_ID).is(jobEntity.getSourceId())
                .and(KEY_SOURCE_JOB_ID).is(jobEntity.getSourceJobId());
        Query query = new Query(existsCriteria);

        return mongoTemplate.exists(query, JobEntity.class);
    }

}

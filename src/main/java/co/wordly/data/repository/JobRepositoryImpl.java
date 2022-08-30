package co.wordly.data.repository;

import co.wordly.data.entity.JobEntity;
import co.wordly.data.model.JobSnippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public Set<JobSnippet> getSourceJobIdsDetails() {
        return mongoTemplate.findAll(JobEntity.class).stream()
                .map(job -> new JobSnippet(job.getId(), job.getSourceId(), job.getSourceJobId(),
                        job.getCreationDate()))
                .collect(Collectors.toSet());
    }

}

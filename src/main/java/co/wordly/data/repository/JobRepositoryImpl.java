package co.wordly.data.repository;

import co.wordly.data.entity.JobEntity;
import co.wordly.data.model.JobSnippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class JobRepositoryImpl implements JobRepositoryCustom {

    private static final String KEY_SOURCE_ID = "sourceId";
    private static final String KEY_SOURCE_JOB_ID = "sourceJobId";
    private static final String KEY_PUBLISH_DATE = "publishDate";

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

    @Override
    public List<JobEntity> fetchJobs(String searchText, LocalDateTime fromDate,
                                     LocalDateTime toDate, int offset, int limit) {
        Criteria datesCriteria;
        Query query = new Query();

        if (Objects.nonNull(fromDate) || Objects.nonNull(toDate)) {
            datesCriteria = generateDatesCriteria(fromDate, toDate);
            query.addCriteria(datesCriteria);
        }

        if (StringUtils.hasText(searchText)) {
            TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(searchText);
            query.addCriteria(textCriteria);
        }

        query = query.with(Sort.by(KEY_PUBLISH_DATE).descending())
                .skip(offset)
                .limit(limit);

        return mongoTemplate.find(query, JobEntity.class);
    }

    private Criteria generateDatesCriteria(LocalDateTime fromDate, LocalDateTime toDate) {
        Set<Criteria> datesCriteria = new HashSet<>();

        if (Objects.nonNull(fromDate)) {
            datesCriteria.add(Criteria.where(KEY_PUBLISH_DATE).gte(fromDate));
        }

        if (Objects.nonNull(toDate)) {
            datesCriteria.add(Criteria.where(KEY_PUBLISH_DATE).lte(toDate));
        }

        return new Criteria().andOperator(datesCriteria.toArray(new Criteria[0]));
    }

}

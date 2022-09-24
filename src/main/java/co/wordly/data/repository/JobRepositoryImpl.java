package co.wordly.data.repository;

import co.wordly.data.entity.JobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Repository
public class JobRepositoryImpl implements JobRepositoryCustom {

    private static final String KEY_PUBLISH_DATE = "publishDate";
    private static final String KEY_CREATION_DATE = "creationDate";

    private final MongoTemplate mongoTemplate;

    @Autowired
    public JobRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<JobEntity> fetchJobs(Set<String> keywords, LocalDateTime fromDate,
                                     LocalDateTime toDate, int offset, int limit) {
        Query query = new Query();

        if (Objects.nonNull(fromDate) || Objects.nonNull(toDate)) {
            Criteria datesCriteria = generateDatesCriteria(fromDate, toDate);
            query.addCriteria(datesCriteria);
        }

        if (!CollectionUtils.isEmpty(keywords)) {
            String[] keywordsArr = new String[keywords.size()];
            TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingAny(keywords.toArray(keywordsArr));
            query.addCriteria(textCriteria);
        }

        query = query.with(Sort.by(KEY_PUBLISH_DATE).descending())
                .skip(offset)
                .limit(limit);

        return mongoTemplate.find(query, JobEntity.class);
    }

    @Override
    public List<JobEntity> fetchTodayJobs() {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        Criteria todayCriteria = Criteria.where(KEY_CREATION_DATE).gte(today);
        Query query = new Query(todayCriteria);

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

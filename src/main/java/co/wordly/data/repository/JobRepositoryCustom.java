package co.wordly.data.repository;

import co.wordly.data.entity.JobEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface JobRepositoryCustom {

    List<JobEntity> fetchJobs(Set<String> keywords, LocalDateTime fromDate,
                              LocalDateTime toDate, int offset, int limit);

    List<JobEntity> fetchTodayJobs();

}

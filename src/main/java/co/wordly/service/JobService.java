package co.wordly.service;

import co.wordly.data.dto.api.PlatformJobDto;
import co.wordly.data.entity.JobEntity;

import java.time.LocalDateTime;
import java.util.Set;

public interface JobService {

    void handleJobs(Set<JobEntity> jobs);

    Set<PlatformJobDto> fetchJobs(Set<String> keywords, LocalDateTime fromDate,
                                  LocalDateTime toDate, int offset, int limit);

}

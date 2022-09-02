package co.wordly.service;

import co.wordly.data.converter.PlatformJobConverter;
import co.wordly.data.dto.api.PlatformJobDto;
import co.wordly.data.entity.JobEntity;
import co.wordly.data.model.JobSnippet;
import co.wordly.data.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public void handleJobs(Set<JobEntity> jobEntities) {
        Set<JobSnippet> existingJobDetails = jobRepository.getSourceJobIdsDetails();
        Set<JobEntity> jobsToSave = jobEntities.stream()
                .map(job -> updateJob(existingJobDetails, job))
                .collect(Collectors.toSet());

        jobRepository.saveAll(jobsToSave);
    }

    @Override
    public Set<PlatformJobDto> fetchJobs(String searchText, LocalDateTime fromDate,
                                         LocalDateTime toDate, int offset, int limit) {
        List<JobEntity> jobs = jobRepository.fetchJobs(searchText, fromDate, toDate, offset, limit);

        return PlatformJobConverter.convert(jobs);
    }

    private JobEntity updateJob(Set<JobSnippet> jobIds, JobEntity job) {
        JobSnippet jobSnippet = new JobSnippet(job.getId(), job.getSourceId(), job.getSourceJobId(),
                job.getCreationDate());
        JobSnippet existingSnippet = jobIds.stream()
                .filter(snippet -> snippet.isSameSnippet(jobSnippet))
                .findAny()
                .orElse(null);

        if (Objects.isNull(existingSnippet)) {
            return job;
        }

        return job.withId(existingSnippet.getJobId())
                .withCreationDate(existingSnippet.getCreationDate());
    }

}

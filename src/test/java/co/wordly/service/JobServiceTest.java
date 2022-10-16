package co.wordly.service;

import co.wordly.data.dto.api.PlatformJobDto;
import co.wordly.data.entity.JobEntity;
import co.wordly.data.repository.JobRepository;
import co.wordly.util.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JobServiceTest {

    JobRepository jobRepository;

    JobService jobService;

    Set<JobEntity> jobs;

    @BeforeEach
    void setup() {
        jobs = TestUtils.jobEntities();
        jobRepository = mock(JobRepository.class);

        jobService = new JobServiceImpl(jobRepository);
    }

    @Test
    void testHandleJobs() {
        when(jobRepository.findAll()).thenReturn(TestUtils.jobEntities().stream().toList());

        jobService.handleJobs(TestUtils.jobEntities());

        verify(jobRepository, times(1)).saveAll(any(Set.class));
    }

    @Test
    void testFetchJobs() {
        when(jobRepository.fetchJobs(any(Set.class), any(LocalDateTime.class),
                any(LocalDateTime.class), anyInt(), anyInt()))
                .thenReturn(jobs.stream().toList());

        Set<PlatformJobDto> test = jobService.fetchJobs(Set.of("Java", "Kotlin", "Spring"),
                LocalDateTime.MIN, LocalDateTime.MAX, 0, 10);

        Assertions.assertTrue(
                test.stream()
                        .allMatch(platformJob -> platformJobDtos().stream()
                                .anyMatch(dto -> dto.id().equals(platformJob.id()) &&
                                        dto.title().equals(platformJob.title()) &&
                                        dto.description().equals(platformJob.description()) &&
                                        dto.salary().equals(platformJob.salary()) &&
                                        dto.companyLogoUrl().equals(platformJob.companyLogoUrl()) &&
                                        dto.publishDate().equals(platformJob.publishDate())))
        );
    }

    private Set<PlatformJobDto> platformJobDtos() {
        return jobs.stream()
                .map(job -> new PlatformJobDto.Builder()
                        .id(job.getId())
                        .title(job.getTitle())
                        .description(job.getDescription())
                        .salary(job.getSalary())
                        .companyLogoUrl(job.getCompanyLogoUrl())
                        .publishDate(job.getPublishDate())
                        .build())
                .collect(Collectors.toSet());
    }

}

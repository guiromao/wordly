package co.wordly.scheduler;

import co.wordly.data.converter.JobConverter;
import co.wordly.data.dto.JobDto;
import co.wordly.data.entity.JobEntity;
import co.wordly.service.JobService;
import co.wordly.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JobSearchScheduler {

    private final Map<String, String> apisMap;
    private final Map<String, String> apisNames;
    private final RestTemplate restTemplate;
    private final SourceService sourceService;
    private final JobService jobService;
    private final Map<String, JobConverter> jobConverterMap;
    private final Map<String, String> apis;

    @Autowired
    public JobSearchScheduler(@Value("#{${jobs.api.map}}") Map<String, String> apisMap,
                              @Value("#{${jobs.api.names}}") Map<String, String> apisNames,
                              RestTemplate restTemplate,
                              SourceService sourceService,
                              JobService jobService,
                              Map<String, JobConverter> jobConverterMap,
                              @Qualifier("apis") Map<String, String> apis) {
        this.apisMap = apisMap;
        this.apisNames = apisNames;
        this.restTemplate = restTemplate;
        this.sourceService = sourceService;
        this.jobService = jobService;
        this.jobConverterMap = jobConverterMap;
        this.apis = apis;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void fetchJobs() {
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(this::searchTask);
    }

    private void searchTask() {
        sourceService.handle(new HashSet<>(apisNames.values()));

        Set<JobEntity> jobEntities = apis.keySet().stream()
                .map(this::getJobs)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        // Save or update Jobs in Database
        jobEntities.forEach(jobService::handleJob);
    }

    private Set<JobEntity> getJobs(String apiName) {
        final String apiUrl = apis.get(apiName);

        ResponseEntity<JobDto[]> jobsResponse = restTemplate.exchange(apiUrl, HttpMethod.GET, null, JobDto[].class);

        if (Objects.isNull(jobsResponse.getBody())) {
            return Collections.emptySet();
        }

        Set<JobDto> jobDtos = Stream.of(jobsResponse.getBody()).collect(Collectors.toSet());
        JobConverter converter = jobConverterMap.get(apiName);

        return jobDtos.stream()
                .map(converter::convert)
                .collect(Collectors.toSet());
    }

}

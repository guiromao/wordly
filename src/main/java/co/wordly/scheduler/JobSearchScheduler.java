package co.wordly.scheduler;

import co.wordly.configuration.JobsConfigurations;
import co.wordly.data.converter.JobConverter;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.entity.JobEntity;
import co.wordly.service.CompanyService;
import co.wordly.service.JobService;
import co.wordly.service.SourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
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

@Component
public class JobSearchScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(JobSearchScheduler.class);

    private final RestTemplate restTemplate;
    private final SourceService sourceService;
    private final JobService jobService;
    private final CompanyService companyService;
    private final Map<String, JobConverter> jobConverterMap;
    private final Map<String, String> apis;
    private final Map<String, Class<? extends ApiResponse>> returnedTypes;

    @Autowired
    public JobSearchScheduler(RestTemplate restTemplate,
                              SourceService sourceService,
                              JobService jobService,
                              CompanyService companyService,
                              @Qualifier(JobsConfigurations.CONVERTERS) Map<String, JobConverter> jobConverterMap,
                              @Qualifier(JobsConfigurations.APIS) Map<String, String> apis,
                              @Qualifier(JobsConfigurations.RETURNED_TYPES)
                                  Map<String, Class<? extends ApiResponse>> returnedTypes) {
        this.restTemplate = restTemplate;
        this.sourceService = sourceService;
        this.jobService = jobService;
        this.companyService = companyService;
        this.jobConverterMap = jobConverterMap;
        this.apis = apis;
        this.returnedTypes = returnedTypes;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void fetchJobs() {
        LOG.info("Updating existing Job sources...");
        sourceService.handle(new HashSet<>(apis.keySet()));
        LOG.info("Existing job sources update: done.");

        LOG.info("Going to start a search Jobs task...");
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(this::searchTask);
    }

    private void searchTask() {
        Set<JobEntity> jobEntities = apis.keySet().stream()
                .map(this::getJobs)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        LOG.info("Going to save jobs in Database...");

        // Save or update Jobs in Database
        jobService.handleJobs(jobEntities);

        LOG.info("Jobs saved.");
    }

    private Set<JobEntity> getJobs(String apiName) {
        final String apiUrl = apis.get(apiName);
        final Class<? extends ApiResponse> apiResponseClass = returnedTypes.get(apiName);

        LOG.info("Searching for jobs in: {}", apiName);

        ResponseEntity<? extends ApiResponse> jobsResponse =
                restTemplate.exchange(apiUrl, HttpMethod.GET, null, apiResponseClass);

        if (Objects.isNull(jobsResponse.getBody()) || CollectionUtils.isEmpty(jobsResponse.getBody().getJobs())) {
            return Collections.emptySet();
        }

        LOG.info("Found {} jobs in {}", jobsResponse.getBody().getJobs().size(), apiName);

        LOG.info("Handling companies found in fetched jobs...");
        companyService.handleCompaniesOf(jobsResponse.getBody());
        LOG.info("Handling found companies: done.");

        JobConverter converter = jobConverterMap.get(apiName);

        return converter.convert(jobsResponse.getBody());
    }

}

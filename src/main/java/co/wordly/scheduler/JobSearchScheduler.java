package co.wordly.scheduler;

import co.wordly.component.SourceComponent;
import co.wordly.data.converter.JobConverter;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.entity.JobEntity;
import co.wordly.service.CompanyManager;
import co.wordly.service.CompanyService;
import co.wordly.service.JobService;
import co.wordly.service.SourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
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
    private final CompanyManager companyManager;
    private final Map<String, SourceComponent> sourceComponents;

    @Autowired
    public JobSearchScheduler(RestTemplate restTemplate,
                              SourceService sourceService,
                              JobService jobService,
                              CompanyService companyService,
                              CompanyManager companyManager,
                              Map<String, SourceComponent> sourceComponents) {
        this.restTemplate = restTemplate;
        this.sourceService = sourceService;
        this.jobService = jobService;
        this.companyService = companyService;
        this.companyManager = companyManager;
        this.sourceComponents = sourceComponents;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void fetchJobs() {
        LOG.info("Fetching list of companies...");
        companyManager.startupCompanies();
        LOG.info("List of companies: fetched.");

        LOG.info("Updating existing Job sources...");
        sourceService.handle(sourceComponents.keySet());
        LOG.info("Existing job sources update: done.");

        LOG.info("Going to start a search Jobs task...");
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(this::searchTask);
    }

    private void searchTask() {
        Set<JobEntity> jobEntities = sourceComponents.entrySet().stream()
                .map(this::getJobs)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        LOG.info("Going to save jobs in Database...");

        // Save or update Jobs in Database
        jobService.handleJobs(jobEntities);

        LOG.info("Jobs saved.");
    }

    private Set<JobEntity> getJobs(Map.Entry<String, SourceComponent> sourceComponentEntry) {
        final SourceComponent sourceComponent = sourceComponentEntry.getValue();
        final String apiUrl = sourceComponent.getApiUrl();
        final Class<? extends ApiResponse> apiResponseClass = sourceComponent.getReturnedObjects();
        final String apiName = sourceComponentEntry.getKey();

        LOG.info("Searching for jobs in: {}", apiName);

        ResponseEntity<? extends ApiResponse> jobsResponse =
                restTemplate.exchange(apiUrl, HttpMethod.GET, null, apiResponseClass);

        if (Objects.isNull(jobsResponse.getBody()) || CollectionUtils.isEmpty(jobsResponse.getBody().getJobs())) {
            return Collections.emptySet();
        }

        LOG.info("Found {} jobs in {}", jobsResponse.getBody().getJobs().size(), apiName);

        LOG.info("Handling companies found in fetched jobs...");
        companyService.handleCompaniesOf(jobsResponse.getBody().getJobs(), sourceService.getIdFromName(apiName));
        LOG.info("Handling found companies: done.");

        JobConverter converter = sourceComponent.getConverter();

        return converter.convert(jobsResponse.getBody());
    }

}

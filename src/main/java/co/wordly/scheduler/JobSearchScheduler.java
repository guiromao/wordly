package co.wordly.scheduler;

import co.wordly.component.SourceComponent;
import co.wordly.data.converter.JobConverter;
import co.wordly.data.dto.JobDto;
import co.wordly.data.entity.JobEntity;
import co.wordly.service.CompanyManager;
import co.wordly.service.CompanyService;
import co.wordly.service.JobService;
import co.wordly.service.SourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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

    private final SourceService sourceService;
    private final JobService jobService;
    private final CompanyService companyService;
    private final CompanyManager companyManager;
    private final Map<String, SourceComponent> sourceComponents;

    @Autowired
    public JobSearchScheduler(SourceService sourceService,
                              JobService jobService,
                              CompanyService companyService,
                              CompanyManager companyManager,
                              Map<String, SourceComponent> sourceComponents) {
        this.sourceService = sourceService;
        this.jobService = jobService;
        this.companyService = companyService;
        this.companyManager = companyManager;
        this.sourceComponents = sourceComponents;
    }

    // Fetching jobs once per hour
    @Scheduled(fixedRate = 1000 * 60 * 60)
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
        final String apiName = sourceComponentEntry.getKey();

        Set<JobDto> jobDtos = sourceComponent.fetchJobs();

        if (CollectionUtils.isEmpty(jobDtos)) {
            return Collections.emptySet();
        }

        LOG.info("Handling companies found in fetched jobs...");
        companyService.handleCompaniesOf(jobDtos, sourceService.getIdFromName(apiName));
        LOG.info("Handling found companies: done.");

        JobConverter converter = sourceComponent.getConverter();

        return converter.convert(jobDtos);
    }

}

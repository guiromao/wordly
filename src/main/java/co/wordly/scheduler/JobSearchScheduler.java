package co.wordly.scheduler;

import co.wordly.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class JobSearchScheduler {

    private final Map<String, String> apisMap;
    private final Map<String, String> apisNames;
    private final RestTemplate restTemplate;
    private final SourceService sourceService;

    @Autowired
    public JobSearchScheduler(@Value("#{${jobs.api.map}}") Map<String, String> apisMap,
                              @Value("#{${jobs.api.names}}") Map<String, String> apisNames,
                              RestTemplate restTemplate,
                              SourceService sourceService) {
        this.apisMap = apisMap;
        this.apisNames = apisNames;
        this.restTemplate = restTemplate;
        this.sourceService = sourceService;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void fetchJobs() {
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(this::searchTask);
    }

    private void searchTask() {
        sourceService.handle(new HashSet<>(apisNames.values()));
    }

}

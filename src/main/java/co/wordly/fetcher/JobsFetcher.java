package co.wordly.fetcher;

import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.apiresponse.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public abstract class JobsFetcher {

    private static final Logger LOG = LoggerFactory.getLogger(JobsFetcher.class);

    protected final RestTemplate restTemplate;
    protected final String apiUrl;
    protected final Class<? extends ApiResponse> apiResponse;

    @Autowired
    protected JobsFetcher(RestTemplate restTemplate, String apiUrl,
                          Class<? extends ApiResponse> apiResponse) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.apiResponse = apiResponse;
    }

    public Set<JobDto> fetchJobs() {
        ResponseEntity<? extends ApiResponse> jobsResponse =
                restTemplate.exchange(apiUrl, HttpMethod.GET, null, apiResponse);

        if (Objects.isNull(jobsResponse.getBody()) || CollectionUtils.isEmpty(jobsResponse.getBody().getJobs())) {
            return Collections.emptySet();
        }

        LOG.info("Found {} jobs in API: {}", jobsResponse.getBody().getJobs().size(), apiUrl);

        return jobsResponse.getBody().getJobs();
    }

}

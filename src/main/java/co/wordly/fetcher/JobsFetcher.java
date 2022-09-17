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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class JobsFetcher {

    private static final Logger LOG = LoggerFactory.getLogger(JobsFetcher.class);

    protected final RestTemplate restTemplate;
    protected final String apiUrl;
    protected final Class<? extends ApiResponse> apiResponse;
    protected final Integer maxResults;

    @Autowired
    protected JobsFetcher(RestTemplate restTemplate, String apiUrl,
                          Class<? extends ApiResponse> apiResponse, Integer maxResults) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.apiResponse = apiResponse;
        this.maxResults = maxResults;
    }

    public Set<JobDto> fetchJobs() {
        int offset = 0;
        Set<JobDto> jobs = new HashSet<>();
        ResponseEntity<? extends ApiResponse> responseDto;
        String url = apiUrl;
        boolean hasAllJobs = false;

        while (!hasAllJobs) {
            responseDto = restTemplate.exchange(url, HttpMethod.GET, null, apiResponse);

            if (Objects.nonNull(responseDto.getBody()) && !CollectionUtils.isEmpty(responseDto.getBody().getJobs())) {
                jobs.addAll(responseDto.getBody().getJobs());
                offset += maxResults;
                url = apiUrl + "&offset=" + offset + "&limit=" + maxResults;
            } else {
                hasAllJobs = true;
            }
        }

        LOG.info("Found {} jobs in API: {}", jobs.size(), apiUrl);

        return jobs;
    }

}

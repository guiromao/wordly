package co.wordly.finder;

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
import java.util.Set;

public abstract class JobsFinder {

    private static final Logger LOG = LoggerFactory.getLogger(JobsFinder.class);

    protected final RestTemplate restTemplate;
    protected final String apiUrl;
    protected final String apiName;
    protected final Class<? extends ApiResponse> apiResponse;
    protected final Integer maxResults;

    @Autowired
    protected JobsFinder(RestTemplate restTemplate, String apiUrl,
                         String apiName,
                         Class<? extends ApiResponse> apiResponse,
                         Integer maxResults) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.apiName = apiName;
        this.apiResponse = apiResponse;
        this.maxResults = maxResults;
    }

    public Set<JobDto> findJobs() {
        int offset = 0;
        Set<JobDto> jobs = new HashSet<>();
        ResponseEntity<? extends ApiResponse> responseDto;
        String url = apiUrl;
        boolean hasAllJobs = false;

        while (!hasAllJobs) {
            responseDto = restTemplate.exchange(url, HttpMethod.GET, null, apiResponse);

            if (responseDto.hasBody() && !CollectionUtils.isEmpty(responseDto.getBody().getJobs())) {
                jobs.addAll(responseDto.getBody().getJobs());
                offset += maxResults;
                url = apiUrl + "&offset=" + offset + "&limit=" + maxResults;
            } else {
                hasAllJobs = true;
            }
        }

        LOG.info("Found {} jobs in API of {}", jobs.size(), apiName);

        return jobs;
    }

}

package co.wordly.fetcher;

import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.dto.apiresponse.HymalaiasAppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class HymalaiasAppJobsFetcher extends JobsFetcher {

    private static final Logger LOG = LoggerFactory.getLogger(HymalaiasAppJobsFetcher.class);
    private static final Integer MAX_RESULTS = 200;

    public HymalaiasAppJobsFetcher(RestTemplate restTemplate,
                                   @Value("${source.api.url.hymalaiasapp}") String apiUrl) {
        super(restTemplate, apiUrl, HymalaiasAppResponse.class, MAX_RESULTS);
    }

    @Override
    public Set<JobDto> fetchJobs() {
        LOG.info("Searching for jobs in: Hymalaias App");

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
                url = apiUrl + "?offset=" + offset + "&limit=" + maxResults;
            } else {
                hasAllJobs = true;
            }
        }

        LOG.info("Found {} jobs in API: {}", jobs.size(), apiUrl);

        return jobs;
    }
}

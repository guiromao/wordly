package co.wordly.fetcher;

import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.dto.apiresponse.LandingJobsResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@Component
public class LandingJobsJobsFetcher extends JobsFetcher {

    private static final Logger LOG = LoggerFactory.getLogger(LandingJobsJobsFetcher.class);
    private static final Integer MAX_RESULTS = 50;

    public LandingJobsJobsFetcher(RestTemplate restTemplate,
                                  @Value("${source.api.url.landingjobs}") String apiUrl) {
        super(restTemplate, apiUrl, "Landing Jobs", LandingJobsResponseDto.class, MAX_RESULTS);
    }

    @Override
    public Set<JobDto> fetchJobs() {
        LOG.info("Searching for jobs in: Landing Jobs");
        return super.fetchJobs();
    }

}

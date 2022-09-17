package co.wordly.fetcher;

import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.apiresponse.RemotiveJobsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@Component
public class RemotiveJobsFetcher extends JobsFetcher {

    private static final Logger LOG = LoggerFactory.getLogger(RemotiveJobsFetcher.class);

    // Remotive will return ALL active Job postings in one request
    private static final Integer MAX_RESULTS = Integer.MAX_VALUE;

    public RemotiveJobsFetcher(RestTemplate restTemplate,
                               @Value("${source.api.url.remotive}") String apiUrl) {
        super(restTemplate, apiUrl, "Remotive", RemotiveJobsDto.class, MAX_RESULTS);
    }

    @Override
    public Set<JobDto> fetchJobs() {
        LOG.info("Searching for jobs in: Remotive");
        return super.fetchJobs();
    }

}

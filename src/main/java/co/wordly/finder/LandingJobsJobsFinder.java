package co.wordly.finder;

import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.apiresponse.LandingJobsResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@Component
public class LandingJobsJobsFinder extends JobsFinder {

    private static final Logger LOG = LoggerFactory.getLogger(LandingJobsJobsFinder.class);
    private static final Integer MAX_RESULTS = 50;

    public LandingJobsJobsFinder(RestTemplate restTemplate,
                                 @Value("${source.api.url.landingjobs}") String apiUrl) {
        super(restTemplate, apiUrl, "Landing Jobs", LandingJobsResponseDto.class, MAX_RESULTS);
    }

    @Override
    public Set<JobDto> findJobs() {
        LOG.info("Searching for jobs in: Landing Jobs");
        return super.findJobs();
    }

}

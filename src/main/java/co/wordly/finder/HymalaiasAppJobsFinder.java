package co.wordly.finder;

import co.wordly.configuration.HymalaiasAppConfig;
import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.dto.apiresponse.HymalaiasAppResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HymalaiasAppJobsFinder extends JobsFinder {

    private static final Logger LOG = LoggerFactory.getLogger(HymalaiasAppJobsFinder.class);
    private static final Integer MAX_RESULTS = 200;
    private static final Set<String> SUPPORTED_CATEGORIES = HymalaiasAppConfig.SUPPORTED_CATEGORIES;

    public HymalaiasAppJobsFinder(RestTemplate restTemplate,
                                  @Value("${source.api.url.hymalaiasapp}") String apiUrl) {
        super(restTemplate, apiUrl, "Hymalaias App", HymalaiasAppResponseDto.class, MAX_RESULTS);
    }

    @Override
    public Set<JobDto> findJobs() {
        LOG.info("Searching for jobs in: Hymalaias App");

        int offset = 0;
        Set<JobDto> jobs = new HashSet<>();
        ResponseEntity<? extends ApiResponse> responseDto;
        String url = apiUrl;
        boolean hasAllJobs = false;

        while (!hasAllJobs) {
            responseDto = restTemplate.exchange(url, HttpMethod.GET, null, apiResponse);

            if (responseDto.hasBody() && !CollectionUtils.isEmpty(responseDto.getBody().getJobs())) {
                jobs.addAll(responseDto.getBody().getJobs());
                offset += MAX_RESULTS;
                url = apiUrl + "?offset=" + offset + "&limit=" + MAX_RESULTS;
            } else {
                hasAllJobs = true;
            }
        }

        // Filter the jobs directly related to Software Development
        jobs = jobs.stream()
                .filter(job -> job.getCategories().stream()
                        .anyMatch(SUPPORTED_CATEGORIES::contains))
                .collect(Collectors.toSet());

        LOG.info("Found {} jobs in API of {}", jobs.size(), apiName);

        return jobs;
    }
}

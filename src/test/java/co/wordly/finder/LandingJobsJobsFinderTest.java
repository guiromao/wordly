package co.wordly.data.repository.finder;

import co.wordly.data.dto.apiresponse.LandingJobsResponseDto;
import co.wordly.finder.LandingJobsJobsFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LandingJobsJobsFinderTest {

    RestTemplate restTemplate;

    LandingJobsJobsFinder jobsFinder;

    @BeforeEach
    void setup() {
        restTemplate = mock(RestTemplate.class);

        jobsFinder = new LandingJobsJobsFinder(restTemplate, "https://landingjobs.com/api");
    }

    @Test
    void testFindJobs() {
        when(restTemplate.exchange(anyString(), any(HttpMethod.class),
                nullable(HttpEntity.class), any(Class.class)))
                .thenReturn(landingJobsJobs());

        jobsFinder.findJobs();

        verify(restTemplate, times(1)).exchange(anyString(), any(HttpMethod.class),
                nullable(HttpEntity.class), any(Class.class));
    }

    // Simulating Landing Jobs response
    private ResponseEntity<LandingJobsResponseDto> landingJobsJobs() {
        return ResponseEntity.of(Optional.empty());
    }

}

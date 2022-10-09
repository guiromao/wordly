package co.wordly.data.repository.finder;

import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.RemotiveDto;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.dto.apiresponse.RemotiveJobsDto;
import co.wordly.finder.RemotiveJobsFinder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RemotiveJobsFinderTest {

    RestTemplate restTemplate;

    RemotiveJobsFinder jobsFinder;

    @BeforeEach
    void setup() {
        restTemplate = mock(RestTemplate.class);

        jobsFinder = new RemotiveJobsFinder(restTemplate, "https:/api-remotive.com/");
    }

    @Test
    void testFindJobs() {
        when(restTemplate.exchange(anyString(), any(HttpMethod.class),
                nullable(HttpEntity.class), any(Class.class)))
                .thenReturn(remotiveJobs());

        jobsFinder.findJobs();

        verify(restTemplate, times(1)).exchange(anyString(), any(HttpMethod.class),
                nullable(HttpEntity.class), any(Class.class));
    }

    private ResponseEntity<RemotiveJobsDto> remotiveJobs() {
        return ResponseEntity.of(
                Optional.empty()
        );
    }

}

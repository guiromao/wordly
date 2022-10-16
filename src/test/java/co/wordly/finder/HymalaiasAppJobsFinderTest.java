package co.wordly.finder;

import co.wordly.data.dto.apiresponse.HymalaiasAppResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

class HymalaiasAppJobsFinderTest {

    RestTemplate restTemplate;

    HymalaiasAppJobsFinder jobsFinder;

    @BeforeEach
    void setup() {
        restTemplate = Mockito.mock(RestTemplate.class);

        jobsFinder = new HymalaiasAppJobsFinder(restTemplate, "https://hymalaias-app.com/api");
    }

    @Test
    void testFindJobs() {
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class),
                Mockito.nullable(HttpEntity.class), Mockito.any(Class.class)))
                .thenReturn(hymalaiasJobs());

        jobsFinder.findJobs();

        Mockito.verify(restTemplate, Mockito.times(1)).exchange(Mockito.anyString(), Mockito.any(HttpMethod.class),
                Mockito.nullable(HttpEntity.class), Mockito.any(Class.class));
    }

    // Just to simulate a response from the API
    private ResponseEntity<HymalaiasAppResponseDto> hymalaiasJobs() {
        return ResponseEntity.of(Optional.empty());
    }

}

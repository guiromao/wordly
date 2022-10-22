package co.wordly.service;

import co.wordly.component.LandingJobsSourceComponent;
import co.wordly.component.SourceComponent;
import co.wordly.data.converter.LandingJobsJobConverter;
import co.wordly.data.dto.apiresponse.company.ApiCompanyResponseLandingJobs;
import co.wordly.data.dto.company.LandingJobsCompanyDto;
import co.wordly.finder.LandingJobsJobsFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CompanyManagerTest {

    RestTemplate restTemplate;
    CompanyService companyService;
    SourceService sourceService;

    CompanyManager companyManager;

    @BeforeEach
    void setup() {
        restTemplate = mock(RestTemplate.class);
        companyService = mock(CompanyService.class);
        sourceService = mock(SourceService.class);

        companyManager = new CompanyManagerImpl(restTemplate, companyService, sourceService, sourceComponentsMap());
    }

    @Test
    void testStartupCompanies() {
        when(sourceService.getIdFromName(anyString())).thenReturn("landingjobs-id");
        when(restTemplate.exchange(anyString(), any(HttpMethod.class),
                nullable(HttpEntity.class), any(Class.class))).thenReturn(ResponseEntity.of(Optional.of(companiesDto())));

        companyManager.startupCompanies();

        verify(restTemplate, times(1)).exchange(anyString(), any(HttpMethod.class),
                nullable(HttpEntity.class), any(Class.class));
        verify(companyService, times(1)).handleCompanies(any(Set.class), anyString());
    }

    private ApiCompanyResponseLandingJobs companiesDto() {
        return new ApiCompanyResponseLandingJobs(
                Set.of(
                        new LandingJobsCompanyDto("company-id", "company-name")
                )
        );
    }

    private Map<String, SourceComponent> sourceComponentsMap() {
        SourceComponent sourceComponent = new LandingJobsSourceComponent(mock(LandingJobsJobsFinder.class),
                mock(LandingJobsJobConverter.class), "https://landingjobs-companies.com/api");

        return Map.of("LandingJobs", sourceComponent);
    }

}

package co.wordly.scheduler;

import co.wordly.component.RemotiveSourceComponent;
import co.wordly.component.SourceComponent;
import co.wordly.data.converter.RemotiveJobConverter;
import co.wordly.data.dto.JobDto;
import co.wordly.finder.RemotiveJobsFinder;
import co.wordly.service.CompanyManager;
import co.wordly.service.CompanyService;
import co.wordly.service.JobService;
import co.wordly.service.SourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JobSearchSchedulerTest {

    SourceService sourceService;
    JobService jobService;
    CompanyService companyService;
    CompanyManager companyManager;

    JobSearchScheduler scheduler;

    @BeforeEach
    void setup() {
        sourceService = mock(SourceService.class);
        jobService = mock(JobService.class);
        companyService = mock(CompanyService.class);
        companyManager = mock(CompanyManager.class);

        scheduler = new JobSearchScheduler(sourceService, jobService, companyService, companyManager, sourceComponents());
    }

    @Test
    void testFetchJobs() {
        when(sourceService.getIdFromName(anyString())).thenReturn("remotive-id");

        scheduler.fetchJobs();

        verify(companyManager, times(1)).startupCompanies();
        verify(sourceService, times(1)).handle(any(Set.class));
        verify(companyService, times(sourceComponents().size())).handleCompaniesOf(any(Set.class), anyString());
        verify(jobService, times(1)).handleJobs(any(Set.class));
    }

    private Map<String, SourceComponent> sourceComponents() {
        RemotiveJobsFinder jobsFinder = mock(RemotiveJobsFinder.class);
        when(jobsFinder.findJobs()).thenReturn(Set.of(mock(JobDto.class)));

        return Map.of("remotive-id",
                new RemotiveSourceComponent(jobsFinder, mock(RemotiveJobConverter.class)));
    }

}

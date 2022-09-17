package co.wordly.component;

import co.wordly.data.converter.JobConverter;
import co.wordly.data.converter.RemotiveJobConverter;
import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.dto.apiresponse.RemotiveJobsDto;
import co.wordly.data.dto.apiresponse.company.ApiCompanyResponse;
import co.wordly.fetcher.RemotiveJobsFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RemotiveSourceComponent implements SourceComponent {

    private final RemotiveJobsFetcher remotiveJobsFetcher;
    private final RemotiveJobConverter remotiveJobConverter;

    @Autowired
    public RemotiveSourceComponent(RemotiveJobsFetcher remotiveJobsFetcher,
                                   RemotiveJobConverter remotiveJobConverter) {
        this.remotiveJobsFetcher = remotiveJobsFetcher;
        this.remotiveJobConverter = remotiveJobConverter;
    }

    @Override
    public JobConverter getConverter() {
        return remotiveJobConverter;
    }

    @Override
    public String getCompanyApiUrl() {
        return null;
    }

    @Override
    public Class<? extends ApiResponse> getReturnedObjects() {
        return RemotiveJobsDto.class;
    }

    @Override
    public Class<? extends ApiCompanyResponse> getCompaniesResponse() {
        return null;
    }

    @Override
    public Set<JobDto> fetchJobs() {
        return remotiveJobsFetcher.fetchJobs();
    }

}

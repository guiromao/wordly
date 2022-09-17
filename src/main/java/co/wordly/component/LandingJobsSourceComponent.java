package co.wordly.component;

import co.wordly.data.converter.JobConverter;
import co.wordly.data.converter.LandingJobsJobConverter;
import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.dto.apiresponse.LandingJobsResponseDto;
import co.wordly.data.dto.apiresponse.company.ApiCompanyResponse;
import co.wordly.data.dto.apiresponse.company.ApiCompanyResponseLandingJobs;
import co.wordly.fetcher.LandingJobsJobsFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class LandingJobsSourceComponent implements SourceComponent {

    private final LandingJobsJobsFetcher landingJobsJobsFetcher;
    private final LandingJobsJobConverter landingJobsJobConverter;
    private final String companyApiUrl;

    @Autowired
    public LandingJobsSourceComponent(LandingJobsJobsFetcher landingJobsJobsFetcher,
                                      LandingJobsJobConverter landingJobsJobConverter,
                                      @Value("${source.api.url.companies.landingjobs}") String companyApiUrl) {
        this.landingJobsJobsFetcher = landingJobsJobsFetcher;
        this.landingJobsJobConverter = landingJobsJobConverter;
        this.companyApiUrl = companyApiUrl;
    }

    @Override
    public JobConverter getConverter() {
        return landingJobsJobConverter;
    }

    @Override
    public String getCompanyApiUrl() {
        return companyApiUrl;
    }

    @Override
    public Class<? extends ApiResponse> getReturnedObjects() {
        return LandingJobsResponseDto.class;
    }

    @Override
    public Class<? extends ApiCompanyResponse> getCompaniesResponse() {
        return ApiCompanyResponseLandingJobs.class;
    }

    @Override
    public Set<JobDto> fetchJobs() {
        return landingJobsJobsFetcher.fetchJobs();
    }

}

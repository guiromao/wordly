package co.wordly.component;

import co.wordly.data.converter.JobConverter;
import co.wordly.data.converter.LandingJobsJobConverter;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.dto.apiresponse.LandingJobsResponseDto;
import co.wordly.data.dto.apiresponse.company.ApiCompanyResponse;
import co.wordly.data.dto.apiresponse.company.ApiCompanyResponseLandingJobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LandingJobsSourceComponent implements SourceComponent {

    private final LandingJobsJobConverter landingJobsJobConverter;
    private final String apiUrl;
    private final String companyApiUrl;

    @Autowired
    public LandingJobsSourceComponent(LandingJobsJobConverter landingJobsJobConverter,
                                      @Value("${source.api.url.landingjobs}") String apiUrl,
                                      @Value("${source.api.url.companies.landingjobs}") String companyApiUrl) {
        this.landingJobsJobConverter = landingJobsJobConverter;
        this.apiUrl = apiUrl;
        this.companyApiUrl = companyApiUrl;
    }

    @Override
    public String getApiUrl() {
        return apiUrl;
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

}

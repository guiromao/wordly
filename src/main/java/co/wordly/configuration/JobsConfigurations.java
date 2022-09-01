package co.wordly.configuration;

import co.wordly.data.converter.JobConverter;
import co.wordly.data.converter.LandingJobsJobConverter;
import co.wordly.data.converter.RemotiveJobConverter;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.dto.apiresponse.LandingJobsResponseDto;
import co.wordly.data.dto.apiresponse.RemotiveJobsDto;
import co.wordly.data.dto.apiresponse.company.ApiCompanyResponse;
import co.wordly.data.dto.apiresponse.company.ApiCompanyResponseLandingJobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;

@Configuration
public class JobsConfigurations {

    public static final String REMOTIVE = "Remotive";
    public static final String LANDING_JOBS = "Landing Jobs";
    public static final String REMOTE_OK = "Remote Ok";

    public static final String JOB_SITES = "jobSites";
    public static final String APIS = "apis";
    public static final String RETURNED_TYPES = "returnedTypes";
    public static final String RETURNED_TYPES_COMPANIES = "returnedTypesCompanies";
    public static final String CONVERTERS = "converters";
    public static final String COMPANIES_APIS = "companiesApis";

    private final RemotiveJobConverter remotiveJobConverter;
    private final LandingJobsJobConverter landingsJobsConverter;

    @Autowired
    public JobsConfigurations(RemotiveJobConverter remotiveJobConverter,
                              LandingJobsJobConverter landingsJobsConverter) {
        this.remotiveJobConverter = remotiveJobConverter;
        this.landingsJobsConverter = landingsJobsConverter;
    }

    @Bean(name = CONVERTERS)
    public Map<String, JobConverter> convertersMap() {
        return Map.of(
                REMOTIVE, remotiveJobConverter,
                LANDING_JOBS, landingsJobsConverter
        );
    }

    @Bean(name = JOB_SITES)
    public Set<String> jobSites() {
        return Set.of(REMOTIVE, LANDING_JOBS, REMOTE_OK);
    }

    @Bean(name = APIS)
    public Map<String, String> jobApiUrls() {
        return Map.of(
                REMOTIVE, "https://remotive.com/api/remote-jobs?category=software-dev",
                LANDING_JOBS, "https://landing.jobs/api/v1/jobs?remote=true",
                REMOTE_OK, "https://remoteok.com/api"
        );
    }

    @Bean(name = RETURNED_TYPES)
    public Map<String, Class<? extends ApiResponse>> sourceReturnedObjects() {
        return Map.of(
                REMOTIVE, RemotiveJobsDto.class,
                LANDING_JOBS, LandingJobsResponseDto.class
        );
    }

    @Bean(name = RETURNED_TYPES_COMPANIES)
    public Map<String, Class<? extends ApiCompanyResponse>> companyReturnedObjects() {
        return Map.of(
                LANDING_JOBS, ApiCompanyResponseLandingJobs.class
        );
    }

    @Bean(name = COMPANIES_APIS)
    public Map<String, String> companiesApis() {
        return Map.of(
            LANDING_JOBS, "https://landing.jobs/api/v1/companies"
        );
    }

}

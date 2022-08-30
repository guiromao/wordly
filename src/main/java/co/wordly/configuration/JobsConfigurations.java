package co.wordly.configuration;

import co.wordly.data.converter.JobConverter;
import co.wordly.data.converter.RemotiveJobConverter;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.dto.apiresponse.RemotiveJobsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;

@Configuration
public class JobsConfigurations {

    public static final String REMOTIVE = "remotive";

    public static final String JOB_SITES = "jobSites";
    public static final String APIS = "apis";
    public static final String RETURNED_TYPES = "returnedTypes";
    public static final String CONVERTERS = "converters";

    private final RemotiveJobConverter remotiveJobConverter;

    @Autowired
    public JobsConfigurations(RemotiveJobConverter remotiveJobConverter) {
        this.remotiveJobConverter = remotiveJobConverter;
    }

    @Bean(name = CONVERTERS)
    public Map<String, JobConverter> convertersMap() {
        return Map.of(
                REMOTIVE, remotiveJobConverter
        );
    }

    @Bean(name = JOB_SITES)
    public Set<String> jobSites() {
        return Set.of(REMOTIVE);
    }

    @Bean(name = APIS)
    public Map<String, String> jobApiUrls() {
        return Map.of(
                REMOTIVE, "https://remotive.com/api/remote-jobs?category=software-dev"
        );
    }

    @Bean(name = RETURNED_TYPES)
    public Map<String, Class<? extends ApiResponse>> sourceReturnedObjects() {
        return Map.of(
                REMOTIVE, RemotiveJobsDto.class
        );
    }

}

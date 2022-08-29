package co.wordly.configuration;

import co.wordly.data.converter.JobConverter;
import co.wordly.data.converter.RemotiveJobConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;

@Configuration
public class JobConverterConfig {

    private static final String REMOTIVE = "remotive";

    private final RemotiveJobConverter remotiveJobConverter;

    @Autowired
    public JobConverterConfig(RemotiveJobConverter remotiveJobConverter) {
        this.remotiveJobConverter = remotiveJobConverter;
    }

    @Bean
    public Map<String, JobConverter> convertersMap() {
        return Map.of(
                REMOTIVE, remotiveJobConverter
        );
    }

    @Bean(name = "jobSites")
    public Set<String> jobSites() {
        return Set.of(REMOTIVE);
    }

    @Bean(name = "apis")
    public Map<String, String> jobApiUrls() {
        return Map.of(
                REMOTIVE, "https://remotive.com/api/remote-jobs?category=software-dev"
        );
    }

}

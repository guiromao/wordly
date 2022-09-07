package co.wordly.configuration;

import co.wordly.component.HymalaiasAppSourceComponent;
import co.wordly.component.LandingJobsSourceComponent;
import co.wordly.component.RemotiveSourceComponent;
import co.wordly.component.SourceComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class JobsConfigurations {

    public static final String REMOTIVE = "Remotive";
    public static final String LANDING_JOBS = "Landing Jobs";
    public static final String HYMALAIAS_APP = "Hymalaias App";
    public static final String SOURCE_COMPONENTS = "sourceComponents";

    private final RemotiveSourceComponent remotiveSourceComponent;
    private final LandingJobsSourceComponent landingJobsSourceComponent;
    private final HymalaiasAppSourceComponent hymalaiasAppSourceComponent;

    @Autowired
    public JobsConfigurations(RemotiveSourceComponent remotiveSourceComponent,
                              LandingJobsSourceComponent landingJobsSourceComponent,
                              HymalaiasAppSourceComponent hymalaiasAppSourceComponent) {
        this.remotiveSourceComponent = remotiveSourceComponent;
        this.landingJobsSourceComponent = landingJobsSourceComponent;
        this.hymalaiasAppSourceComponent = hymalaiasAppSourceComponent;
    }

    @Bean(name = SOURCE_COMPONENTS)
    public Map<String, SourceComponent> sourceComponents() {
        return Map.of(
                REMOTIVE, remotiveSourceComponent,
                LANDING_JOBS, landingJobsSourceComponent,
                HYMALAIAS_APP, hymalaiasAppSourceComponent
        );
    }

}

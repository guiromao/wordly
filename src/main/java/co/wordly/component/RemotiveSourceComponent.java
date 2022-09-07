package co.wordly.component;

import co.wordly.data.converter.JobConverter;
import co.wordly.data.converter.RemotiveJobConverter;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.dto.apiresponse.RemotiveJobsDto;
import co.wordly.data.dto.apiresponse.company.ApiCompanyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RemotiveSourceComponent implements SourceComponent {

    private final RemotiveJobConverter remotiveJobConverter;
    private final String apiUrl;

    @Autowired
    public RemotiveSourceComponent(RemotiveJobConverter remotiveJobConverter,
                                   @Value("${source.api.url.remotive}") String apiUrl) {
        this.remotiveJobConverter = remotiveJobConverter;
        this.apiUrl = apiUrl;
    }

    @Override
    public String getApiUrl() {
        return apiUrl;
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

}

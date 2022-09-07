package co.wordly.component;

import co.wordly.data.converter.HymalaiasJobConverter;
import co.wordly.data.converter.JobConverter;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.dto.apiresponse.HymalaiasAppResponse;
import co.wordly.data.dto.apiresponse.company.ApiCompanyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HymalaiasAppSourceComponent implements SourceComponent {

    private final HymalaiasJobConverter hymalaiasJobConverter;
    private final String apiUrl;

    @Autowired
    public HymalaiasAppSourceComponent(HymalaiasJobConverter hymalaiasJobConverter,
                                       @Value("${source.api.url.hymalaiasapp}") String apiUrl) {
        this.hymalaiasJobConverter = hymalaiasJobConverter;
        this.apiUrl = apiUrl;
    }

    @Override
    public String getApiUrl() {
        return apiUrl;
    }

    @Override
    public JobConverter getConverter() {
        return hymalaiasJobConverter;
    }

    @Override
    public String getCompanyApiUrl() {
        return null;
    }

    @Override
    public Class<? extends ApiResponse> getReturnedObjects() {
        return HymalaiasAppResponse.class;
    }

    @Override
    public Class<? extends ApiCompanyResponse> getCompaniesResponse() {
        return null;
    }

}

package co.wordly.component;

import co.wordly.data.converter.HymalaiasJobConverter;
import co.wordly.data.converter.JobConverter;
import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.dto.apiresponse.HymalaiasAppResponse;
import co.wordly.data.dto.apiresponse.company.ApiCompanyResponse;
import co.wordly.finder.HymalaiasAppJobsFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class HymalaiasAppSourceComponent implements SourceComponent {

    private final HymalaiasAppJobsFinder hymalaiasAppJobsFetcher;
    private final HymalaiasJobConverter hymalaiasJobConverter;

    @Autowired
    public HymalaiasAppSourceComponent(HymalaiasAppJobsFinder hymalaiasAppJobsFetcher,
                                       HymalaiasJobConverter hymalaiasJobConverter) {
        this.hymalaiasAppJobsFetcher = hymalaiasAppJobsFetcher;
        this.hymalaiasJobConverter = hymalaiasJobConverter;
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

    @Override
    public Set<JobDto> findJobs() {
        return hymalaiasAppJobsFetcher.findJobs();
    }

}

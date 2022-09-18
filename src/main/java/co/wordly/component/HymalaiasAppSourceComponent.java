package co.wordly.component;

import co.wordly.data.converter.HymalaiasJobConverter;
import co.wordly.data.converter.JobConverter;
import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.apiresponse.company.ApiCompanyResponse;
import co.wordly.finder.HymalaiasAppJobsFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class HymalaiasAppSourceComponent implements SourceComponent {

    private final HymalaiasAppJobsFinder hymalaiasAppJobsFinder;
    private final HymalaiasJobConverter hymalaiasJobConverter;

    @Autowired
    public HymalaiasAppSourceComponent(HymalaiasAppJobsFinder hymalaiasAppJobsFinder,
                                       HymalaiasJobConverter hymalaiasJobConverter) {
        this.hymalaiasAppJobsFinder = hymalaiasAppJobsFinder;
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
    public Class<? extends ApiCompanyResponse> getCompaniesResponse() {
        return null;
    }

    @Override
    public Set<JobDto> findJobs() {
        return hymalaiasAppJobsFinder.findJobs();
    }

}

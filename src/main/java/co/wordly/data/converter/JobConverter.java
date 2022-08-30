package co.wordly.data.converter;

import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.entity.CompanyEntity;
import co.wordly.data.entity.JobEntity;
import co.wordly.data.entity.SourceEntity;
import co.wordly.data.repository.CompanyRepository;
import co.wordly.data.repository.SourceRepository;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public abstract class JobConverter {

    protected final SourceRepository sourceRepository;
    protected final CompanyRepository companyRepository;

    public JobConverter(SourceRepository sourceRepository,
                        CompanyRepository companyRepository) {
        this.sourceRepository = sourceRepository;
        this.companyRepository = companyRepository;
    }

    public abstract Set<JobEntity> convert(ApiResponse apiResponse);

}

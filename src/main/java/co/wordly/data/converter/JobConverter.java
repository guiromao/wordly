package co.wordly.data.converter;

import co.wordly.data.dto.JobDto;
import co.wordly.data.entity.JobEntity;
import co.wordly.data.repository.CompanyRepository;
import co.wordly.data.repository.SourceRepository;

import java.util.Set;

public abstract class JobConverter {

    protected final SourceRepository sourceRepository;
    protected final CompanyRepository companyRepository;

    protected JobConverter(SourceRepository sourceRepository,
                           CompanyRepository companyRepository) {
        this.sourceRepository = sourceRepository;
        this.companyRepository = companyRepository;
    }

    public abstract Set<JobEntity> convert(Set<JobDto> jobDtos);

}

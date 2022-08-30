package co.wordly.service;

import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.entity.CompanyEntity;
import co.wordly.data.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public void handleCompaniesOf(ApiResponse apiResponse) {
        Set<String> uniqueCompanies = apiResponse.getJobs().stream()
                .map(JobDto::getCompanyName)
                .collect(Collectors.toSet());

        Set<String> existingCompanies = companyRepository.findExisting(uniqueCompanies);

        Set<CompanyEntity> newCompanies = uniqueCompanies.stream()
                .filter(companyName -> !existingCompanies.contains(companyName))
                .map(CompanyEntity::create)
                .collect(Collectors.toSet());

        companyRepository.saveAll(newCompanies);
    }
}

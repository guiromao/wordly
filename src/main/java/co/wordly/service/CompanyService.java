package co.wordly.service;

import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.company.CompanyDto;

import java.util.Set;

public interface CompanyService {

    void handleCompaniesOf(Set<JobDto> jobs, String sourceId);

    void handleCompanies(Set<CompanyDto> companies, String sourceId);

}

package co.wordly.data.dto.apiresponse.company;

import co.wordly.data.dto.company.CompanyDto;

import java.util.Set;

public interface ApiCompanyResponse {

    Set<CompanyDto> getCompanyDtos();

}

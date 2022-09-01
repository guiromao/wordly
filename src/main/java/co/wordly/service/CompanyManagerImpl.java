package co.wordly.service;

import co.wordly.configuration.JobsConfigurations;
import co.wordly.data.dto.apiresponse.company.ApiCompanyResponse;
import co.wordly.data.dto.company.ApiCompanyDto;
import co.wordly.data.dto.company.CompanyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
public class CompanyManagerImpl implements CompanyManager {

    private final RestTemplate restTemplate;
    private final CompanyService companyService;
    private final SourceService sourceService;
    private final Map<String, String> companyApis;
    private final Map<String, Class<? extends ApiCompanyResponse>> companyDtos;

    @Autowired
    public CompanyManagerImpl(RestTemplate restTemplate,
                              CompanyService companyService,
                              SourceService sourceService,
                              @Qualifier(JobsConfigurations.COMPANIES_APIS) Map<String, String> companyApis,
                              @Qualifier(JobsConfigurations.RETURNED_TYPES_COMPANIES)
                                    Map<String, Class<? extends ApiCompanyResponse>> companyDtos) {
        this.restTemplate = restTemplate;
        this.companyService = companyService;
        this.sourceService = sourceService;
        this.companyApis = companyApis;
        this.companyDtos = companyDtos;
    }

    @Override
    public void startupCompanies() {
        companyApis.entrySet()
                .forEach(this::handleCompanies);
    }

    private void handleCompanies(Map.Entry<String, String> companyApi) {
        String apiName = companyApi.getKey();
        String apiUrl = companyApi.getValue();
        String sourceId = sourceService.getIdFromName(apiName);
        Class<? extends ApiCompanyResponse> companyDto = companyDtos.get(apiName);

        ResponseEntity<? extends ApiCompanyResponse> response =
                restTemplate.exchange(apiUrl, HttpMethod.GET, null, companyDto);

        if (Objects.nonNull(response.getBody()) && !CollectionUtils.isEmpty(response.getBody().getCompanyDtos())) {
            Set<CompanyDto> companies = response.getBody().getCompanyDtos();
            companyService.handleCompanies(companies, sourceId);
        }
    }

}

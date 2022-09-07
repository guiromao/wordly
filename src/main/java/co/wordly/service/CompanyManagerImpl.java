package co.wordly.service;

import co.wordly.component.SourceComponent;
import co.wordly.configuration.JobsConfigurations;
import co.wordly.data.dto.apiresponse.company.ApiCompanyResponse;
import co.wordly.data.dto.company.CompanyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
public class CompanyManagerImpl implements CompanyManager {

    private final RestTemplate restTemplate;
    private final CompanyService companyService;
    private final SourceService sourceService;
    private final Map<String, SourceComponent> sourceComponents;

    @Autowired
    public CompanyManagerImpl(RestTemplate restTemplate,
                              CompanyService companyService,
                              SourceService sourceService,
                              @Qualifier(JobsConfigurations.SOURCE_COMPONENTS) Map<String, SourceComponent> sourceComponents) {
        this.restTemplate = restTemplate;
        this.companyService = companyService;
        this.sourceService = sourceService;
        this.sourceComponents = sourceComponents;
    }

    @Override
    public void startupCompanies() {
        sourceComponents.entrySet().stream()
                .filter(sourceComponent -> Objects.nonNull(sourceComponent.getValue().getCompanyApiUrl()) &&
                        Objects.nonNull(sourceComponent.getValue().getCompaniesResponse()))
                .forEach(this::handleCompanies);
    }

    // Creates companies in the Application, for API's that have separated endpoints for exposing companies
    private void handleCompanies(Map.Entry<String, SourceComponent> sourceComponentEntry) {
        String apiName = sourceComponentEntry.getKey();
        SourceComponent sourceComponent = sourceComponentEntry.getValue();
        String apiUrl = sourceComponent.getCompanyApiUrl();
        String sourceId = sourceService.getIdFromName(apiName);
        Class<? extends ApiCompanyResponse> companiesDto = sourceComponent.getCompaniesResponse();

        ResponseEntity<? extends ApiCompanyResponse> response =
                restTemplate.exchange(apiUrl, HttpMethod.GET, null, companiesDto);

        if (Objects.nonNull(response.getBody()) && !CollectionUtils.isEmpty(response.getBody().getCompanyDtos())) {
            Set<CompanyDto> companies = response.getBody().getCompanyDtos();
            companyService.handleCompanies(companies, sourceId);
        }
    }

}

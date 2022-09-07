package co.wordly.component;

import co.wordly.data.converter.JobConverter;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.dto.apiresponse.company.ApiCompanyResponse;

/**
 * Abstracts a set of functionalities expected from a Source (eg, Remotive, Landing.Jobs, etc)
 */
public interface SourceComponent {

    String getApiUrl();

    JobConverter getConverter();

    String getCompanyApiUrl();

    Class<? extends ApiResponse> getReturnedObjects();

    Class<? extends ApiCompanyResponse> getCompaniesResponse();

}

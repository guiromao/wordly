package co.wordly.data.dto.apiresponse.company;

import co.wordly.data.dto.company.CompanyDto;
import co.wordly.data.dto.company.LandingJobsCompanyDto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@JsonPropertyOrder(alphabetic = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class ApiCompanyResponseLandingJobs implements ApiCompanyResponse {

    private final Set<LandingJobsCompanyDto> landingJobsCompanyDtos;

    @JsonCreator
    public ApiCompanyResponseLandingJobs(Set<LandingJobsCompanyDto> landingJobsCompanyDtos) {
        this.landingJobsCompanyDtos = landingJobsCompanyDtos;
    }

    @Override
    public Set<CompanyDto> getCompanyDtos() {
        return landingJobsCompanyDtos.stream()
                .map(LandingJobsCompanyDto::getCompanyDto)
                .collect(Collectors.toSet());
    }

    public Set<LandingJobsCompanyDto> getLandingJobsCompanyDtos() {
        return landingJobsCompanyDtos;
    }

    @Override
    public String toString() {
        return "ApiCompanyResponseLandingJobs{" +
                "landingJobsCompanyDtos=" + landingJobsCompanyDtos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApiCompanyResponseLandingJobs that = (ApiCompanyResponseLandingJobs) o;
        return Objects.equals(landingJobsCompanyDtos, that.landingJobsCompanyDtos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(landingJobsCompanyDtos);
    }

}

package co.wordly.data.dto.company;

import co.wordly.configuration.JobsConfigurations;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonPropertyOrder(alphabetic = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class LandingJobsCompanyDto implements ApiCompanyDto {

    private static final String PROPERTY_ID = "id";
    private static final String PROPERTY_NAME = "name";

    private final String id;
    private final String name;

    @JsonCreator
    public LandingJobsCompanyDto(@JsonProperty(PROPERTY_ID) String id,
                                 @JsonProperty(PROPERTY_NAME) String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public CompanyDto getCompanyDto() {
        return new CompanyDto(this.id, this.name, JobsConfigurations.LANDING_JOBS);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "LandingJobsCompanyDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
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
        LandingJobsCompanyDto that = (LandingJobsCompanyDto) o;
        return id.equals(that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}

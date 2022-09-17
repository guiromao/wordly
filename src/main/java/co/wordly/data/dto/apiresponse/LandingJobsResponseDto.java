package co.wordly.data.dto.apiresponse;

import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.LandingJobsDto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class LandingJobsResponseDto implements ApiResponse {

    private final Set<LandingJobsDto> jobs;

    @JsonCreator
    public LandingJobsResponseDto(Set<LandingJobsDto> jobs) {
        this.jobs = jobs;
    }

    @Override
    public Set<JobDto> getJobs() {
        return jobs.stream()
                .map(LandingJobsDto::getJobDto)
                .collect(Collectors.toSet());
    }

    public Set<LandingJobsDto> getLandinggJobs() {
        return jobs;
    }

    @Override
    public String toString() {
        return "LandingJobsResponseDto{" +
                "jobs=" + jobs +
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
        LandingJobsResponseDto that = (LandingJobsResponseDto) o;
        return Objects.equals(jobs, that.jobs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobs);
    }

}

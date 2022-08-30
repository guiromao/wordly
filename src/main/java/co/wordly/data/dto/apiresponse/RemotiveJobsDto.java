package co.wordly.data.dto.apiresponse;

import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.RemotiveDto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

// Class that mimics the response object coming from the Remotive API
@JsonPropertyOrder(alphabetic = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class RemotiveJobsDto implements ApiResponse {

    private static final String PROPERTY_JOBS = "jobs";

    private final Set<RemotiveDto> jobs;

    @JsonCreator
    public RemotiveJobsDto(@JsonProperty(PROPERTY_JOBS) Set<RemotiveDto> jobs) {
        this.jobs = jobs;
    }

    @Override
    public Set<JobDto> getJobs() {
        return jobs.stream()
                .map(RemotiveDto::getJobDto)
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "RemotiveJobsDto{" +
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
        RemotiveJobsDto that = (RemotiveJobsDto) o;
        return Objects.equals(jobs, that.jobs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobs);
    }

}

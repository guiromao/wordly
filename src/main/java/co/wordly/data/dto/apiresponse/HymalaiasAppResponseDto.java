package co.wordly.data.dto.apiresponse;

import co.wordly.data.dto.HymalaiasAppDto;
import co.wordly.data.dto.JobDto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@JsonPropertyOrder(alphabetic = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class HymalaiasAppResponseDto implements ApiResponse {

    private static final String PROPERTY_OFFSET = "offset";
    private static final String PROPERTY_LIMIT = "limit";
    private static final String PROPERTY_TOTAL_COUNT = "total_count";
    private static final String PROPERTY_JOBS = "jobs";

    private final Integer offset;
    private final Integer limit;
    private final Integer totalExistingJobs;
    private final Set<HymalaiasAppDto> hymalaiasJobs;

    @JsonCreator
    public HymalaiasAppResponseDto(@JsonProperty(PROPERTY_OFFSET) Integer offset,
                                   @JsonProperty(PROPERTY_LIMIT) Integer limit,
                                   @JsonProperty(PROPERTY_TOTAL_COUNT) Integer totalExistingJobs,
                                   @JsonProperty(PROPERTY_JOBS) Set<HymalaiasAppDto> hymalaiasJobs) {
        this.offset = offset;
        this.limit = limit;
        this.totalExistingJobs = totalExistingJobs;
        this.hymalaiasJobs = hymalaiasJobs;
    }

    @Override
    public Set<JobDto> getJobs() {
        return hymalaiasJobs.stream()
                .map(HymalaiasAppDto::getJobDto)
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "HymalaiasAppResponse{" +
                "offset=" + offset +
                ", limit=" + limit +
                ", totalExistingJobs=" + totalExistingJobs +
                ", jobs=" + hymalaiasJobs +
                '}';
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getTotalExistingJobs() {
        return totalExistingJobs;
    }

    public Set<HymalaiasAppDto> getHymalaiasJobs() {
        return hymalaiasJobs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HymalaiasAppResponseDto that = (HymalaiasAppResponseDto) o;
        return Objects.equals(offset, that.offset) && Objects.equals(limit, that.limit) &&
                Objects.equals(totalExistingJobs, that.totalExistingJobs) && Objects.equals(hymalaiasJobs, that.hymalaiasJobs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offset, limit, totalExistingJobs, hymalaiasJobs);
    }

}

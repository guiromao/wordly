package co.wordly.data.dto.apiresponse;

import co.wordly.data.dto.JobDto;

import java.util.Set;

public interface ApiResponse {

    Set<JobDto> getJobs();

}

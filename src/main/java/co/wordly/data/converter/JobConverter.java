package co.wordly.data.converter;

import co.wordly.data.dto.JobDto;
import co.wordly.data.entity.JobEntity;

public interface JobConverter {

    JobEntity convert(JobDto jobDto);

}

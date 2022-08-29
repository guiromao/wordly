package co.wordly.data.repository;

import co.wordly.data.entity.JobEntity;

public interface JobRepositoryCustom {

    boolean exists(JobEntity jobEntity);

}

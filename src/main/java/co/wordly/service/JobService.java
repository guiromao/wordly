package co.wordly.service;

import co.wordly.data.entity.JobEntity;

import java.util.Set;

public interface JobService {

    void handleJobs(Set<JobEntity> jobs);

}

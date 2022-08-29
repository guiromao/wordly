package co.wordly.service;

import co.wordly.data.entity.JobEntity;
import co.wordly.data.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // TO DO
    // Create handle for the whole Set of JobEntities - update in Bulk
    public void handleJob(JobEntity jobEntity) {
        if (!jobRepository.exists(jobEntity)) {
            jobRepository.save(jobEntity);
        } // else create Update
    }

}

package co.wordly.data.repository;

import co.wordly.data.entity.JobEntity;
import co.wordly.data.model.JobSnippet;

import java.util.Set;

public interface JobRepositoryCustom {

    boolean exists(JobEntity jobEntity);

    // Get all existing Source Ids + Source Job Ids.
    // Example: if "Remotive" has SourceId of "1", and Job X has job ID "job-1",
    // then one element of the map will be { "1": "job-1" }
    Set<JobSnippet> getSourceJobIdsDetails();

}

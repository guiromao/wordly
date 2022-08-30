package co.wordly.data.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class JobSnippet {

    private final String jobId;
    private final String sourceId;
    private final String sourceJobId;
    private final LocalDateTime creationDate;

    public JobSnippet(String jobId, String sourceId, String sourceJobId,
                      LocalDateTime creationDate) {
        this.jobId = jobId;
        this.sourceId = sourceId;
        this.sourceJobId = sourceJobId;
        this.creationDate = creationDate;
    }

    public boolean isSameSnippet(JobSnippet snippet) {
        return Objects.equals(this.sourceId, snippet.sourceId) &&
                Objects.equals(this.sourceJobId, snippet.sourceJobId);
    }

    @Override
    public String toString() {
        return "JobSnippet{" +
                "jobId='" + jobId + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", sourceJobId='" + sourceJobId + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    public String getJobId() {
        return jobId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getSourceJobId() {
        return sourceJobId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobSnippet that = (JobSnippet) o;
        return jobId.equals(that.jobId) && Objects.equals(sourceId, that.sourceId)
                && Objects.equals(sourceJobId, that.sourceJobId) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, sourceId, sourceJobId, creationDate);
    }

}

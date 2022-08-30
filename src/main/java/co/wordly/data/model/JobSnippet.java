package co.wordly.data.model;

import java.util.Objects;

public class JobSnippet {

    private final String jobId;
    private final String sourceId;
    private final String sourceJobId;

    public JobSnippet(String jobId, String sourceId, String sourceJobId) {
        this.jobId = jobId;
        this.sourceId = sourceId;
        this.sourceJobId = sourceJobId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobSnippet that = (JobSnippet) o;
        return jobId.equals(that.jobId) && Objects.equals(sourceId, that.sourceId) && Objects.equals(sourceJobId, that.sourceJobId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, sourceId, sourceJobId);
    }

}

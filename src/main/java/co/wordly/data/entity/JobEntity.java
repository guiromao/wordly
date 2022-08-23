package co.wordly.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Document(value = JobEntity.JOB_COLLECTION)
@TypeAlias(JobEntity.JOB_COLLECTION)
public class JobEntity {

    private static final String FIELD_TITLE = "title";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_SALARY = "salary";
    private static final String FIELD_COMPANY_ID = "companyId";
    private static final String FIELD_URL = "url";
    private static final String FIELD_COMPANY_LOGO = "companyLogoUrl";
    private static final String FIELD_SOURCE_ID = "sourceId";
    private static final String FIELD_SOURCE_JOB_ID = "sourceJobId";
    private static final String FIELD_PUBLISH_DATE = "publishDate";

    public static final String JOB_COLLECTION = "job";

    @Id
    private final String id;

    @Field(FIELD_TITLE)
    private final String title;

    @Field(FIELD_DESCRIPTION)
    private final String description;

    @Field(FIELD_SALARY)
    private final String salary;

    @Field(FIELD_COMPANY_ID)
    private final String companyId;

    @Field(FIELD_URL)
    private final String url;

    @Field(FIELD_COMPANY_LOGO)
    private final String companyLogoUrl;

    @Field(FIELD_SOURCE_ID)
    private final String sourceId;

    @Field(FIELD_SOURCE_JOB_ID)
    private final String sourceJobId;

    @Field(FIELD_PUBLISH_DATE)
    private final Instant publishDate;

    @PersistenceCreator
    private JobEntity(String id, String title,
                      String description, String salary,
                      String companyId, String url, String companyLogoUrl,
                      String sourceId, String sourceJobId, Instant publishDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.companyId = companyId;
        this.url = url;
        this.companyLogoUrl = companyLogoUrl;
        this.sourceId = sourceId;
        this.sourceJobId = sourceJobId;
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", salary='" + salary + '\'' +
                ", companyId='" + companyId + '\'' +
                ", url='" + url + '\'' +
                ", companyLogoUrl='" + companyLogoUrl + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", sourceJobId='" + sourceJobId + '\'' +
                ", publishDate=" + publishDate +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSalary() {
        return salary;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getUrl() {
        return url;
    }

    public String getCompanyLogoUrl() {
        return companyLogoUrl;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getSourceJobId() {
        return sourceJobId;
    }

    public Instant getPublishDate() {
        return publishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobEntity job = (JobEntity) o;
        return id.equals(job.id) && Objects.equals(title, job.title) &&
                Objects.equals(description, job.description) &&
                Objects.equals(salary, job.salary) && Objects.equals(companyId, job.companyId) &&
                Objects.equals(url, job.url) && Objects.equals(companyLogoUrl, job.companyLogoUrl) &&
                sourceId.equals(job.sourceId) && sourceJobId.equals(job.sourceJobId) &&
                Objects.equals(publishDate, job.publishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, salary, companyId,
                url, companyLogoUrl, sourceId, sourceJobId, publishDate);
    }

    public static class Builder {

        private String title;
        private String description;
        private String salary;
        private String companyId;
        private String url;
        private String companyLogoUrl;
        private String sourceId;
        private String sourceJobId;
        private Instant publishDate;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder salary(String salary) {
            this.salary = salary;
            return this;
        }

        public Builder companyId(String companyId) {
            this.companyId = companyId;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder companyLogoUrl(String companyLogoUrl) {
            this.companyLogoUrl = companyLogoUrl;
            return this;
        }

        public Builder sourceId(String sourceId) {
            this.sourceId = sourceId;
            return this;
        }
        public Builder sourceJobId(String sourceJobId) {
            this.sourceJobId = sourceJobId;
            return this;
        }

        public Builder publishDate(Instant publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        public JobEntity build() {
            final String jobId = UUID.randomUUID().toString();

            return new JobEntity(jobId, title, description, salary, companyId,
                    url, companyLogoUrl, sourceId, sourceJobId, publishDate);
        }

    }

}

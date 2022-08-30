package co.wordly.data.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonPropertyOrder(alphabetic = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class RemotiveDto implements ApiDto {

    private static final String PROPERTY_JOB_ID = "id";
    private static final String PROPERTY_JOB_URL = "url";
    private static final String PROPERTY_JOB_TITLE = "title";
    private static final String PROPERTY_JOB_DESCRIPTION = "description";
    private static final String PROPERTY_COMPANY_NAME = "company_name";
    private static final String PROPERTY_COMPANY_LOGO = "company_logo_url";
    private static final String PROPERTY_SALARY = "salary";
    private static final String PROPERTY_PUBLICATION_DATE = "publication_date";

    private final String jobId;
    private final String jobUrl;
    private final String jobTitle;
    private final String jobDescription;
    private final String companyName;
    private final String companyLogoUrl;
    private final String salary;
    private final String publicationDate;

    @JsonCreator
    public RemotiveDto(@JsonProperty(PROPERTY_JOB_ID) String jobId,
                       @JsonProperty(PROPERTY_JOB_URL) String jobUrl,
                       @JsonProperty(PROPERTY_JOB_TITLE) String jobTitle,
                       @JsonProperty(PROPERTY_JOB_DESCRIPTION) String jobDescription,
                       @JsonProperty(PROPERTY_COMPANY_NAME) String companyName,
                       @JsonProperty(PROPERTY_COMPANY_LOGO) String companyLogoUrl,
                       @JsonProperty(PROPERTY_SALARY) String salary,
                       @JsonProperty(PROPERTY_PUBLICATION_DATE) String publicationDate) {
        this.jobId = jobId;
        this.jobUrl = jobUrl;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.companyName = companyName;
        this.companyLogoUrl = companyLogoUrl;
        this.salary = salary;
        this.publicationDate = publicationDate;
    }

    @Override
    public JobDto getJobDto() {
        return new JobDto.Builder()
                .title(jobTitle)
                .description(jobDescription)
                .salary(salary)
                .companyName(companyName)
                .url(jobUrl)
                .sourceJobId(jobId)
                .companyLogoUrl(companyLogoUrl)
                .publishDate(publicationDate)
                .build();
    }

    @Override
    public String toString() {
        return "RemotiveDto{" +
                "jobId='" + jobId + '\'' +
                ", jobUrl='" + jobUrl + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyLogoUrl='" + companyLogoUrl + '\'' +
                ", salary='" + salary + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                '}';
    }

    public String getId() {
        return jobId;
    }

    public String getTitle() {
        return jobTitle;
    }

    public String getDescription() {
        return jobDescription;
    }

    @JsonProperty(PROPERTY_JOB_ID)
    public String getJobId() {
        return jobId;
    }

    @JsonProperty(PROPERTY_JOB_URL)
    public String getJobUrl() {
        return jobUrl;
    }

    @JsonProperty(PROPERTY_JOB_TITLE)
    public String getJobTitle() {
        return jobTitle;
    }

    @JsonProperty(PROPERTY_JOB_DESCRIPTION)
    public String getJobDescription() {
        return jobDescription;
    }

    @JsonProperty(PROPERTY_COMPANY_NAME)
    public String getCompanyName() {
        return companyName;
    }

    @JsonProperty(PROPERTY_COMPANY_LOGO)
    public String getCompanyLogoUrl() {
        return companyLogoUrl;
    }

    @JsonProperty(PROPERTY_SALARY)
    public String getSalary() {
        return salary;
    }

    public String getCompanyId() {
        return null;
    }

    public String getUrl() {
        return null;
    }

    public String getCompanyUrl() {
        return null;
    }

    public String getSourceId() {
        return null;
    }

    public String getSourceJobId() {
        return null;
    }

    public String getPublishDate() {
        return publicationDate;
    }

    @JsonProperty(PROPERTY_PUBLICATION_DATE)
    public String getPublicationDate() {
        return publicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RemotiveDto that = (RemotiveDto) o;
        return Objects.equals(jobId, that.jobId) && Objects.equals(jobUrl, that.jobUrl) && Objects.equals(jobTitle, that.jobTitle) &&
                Objects.equals(jobDescription, that.jobDescription) && Objects.equals(companyName, that.companyName) &&
                Objects.equals(companyLogoUrl, that.companyLogoUrl) && Objects.equals(salary, that.salary) &&
                Objects.equals(publicationDate, that.publicationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, jobUrl, jobTitle, jobDescription, companyName, companyLogoUrl, salary, publicationDate);
    }
}

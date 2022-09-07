package co.wordly.data.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonPropertyOrder(alphabetic = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LandingJobsDto implements ApiDto {

    private static final String PROPERTY_JOB_ID = "id";
    private static final String PROPERTY_COMPANY_ID = "company_id";
    private static final String PROPERTY_TITLE = "title";
    private static final String PROPERTY_DESCRIPTION = "role_description";
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_CREATED_AT = "created_at";
    private static final String PROPERTY_GROSS_SALARY_LOW = "gross_salary_low";
    private static final String PROPERTY_GROSS_SALARY_HIGH = "gross_salary_high";
    private static final String PROPERTY_CURRENCY = "currency_code";

    private final String jobId;
    private final String companyId;
    private final String title;
    private final String description;
    private final String url;
    private final String createdAt;
    private final String salaryLow;
    private final String salaryHigh;
    private final String currency;

    public LandingJobsDto(@JsonProperty(PROPERTY_JOB_ID) String jobId,
                          @JsonProperty(PROPERTY_COMPANY_ID) String companyId,
                          @JsonProperty(PROPERTY_TITLE) String title,
                          @JsonProperty(PROPERTY_DESCRIPTION) String description,
                          @JsonProperty(PROPERTY_URL) String url,
                          @JsonProperty(PROPERTY_CREATED_AT) String createdAt,
                          @JsonProperty(PROPERTY_GROSS_SALARY_LOW) String salaryLow,
                          @JsonProperty(PROPERTY_GROSS_SALARY_HIGH) String salaryHigh,
                          @JsonProperty(PROPERTY_CURRENCY) String currency) {
        this.jobId = jobId;
        this.companyId = companyId;
        this.title = title;
        this.description = description;
        this.url = url;
        this.createdAt = createdAt;
        this.salaryLow = salaryLow;
        this.salaryHigh = salaryHigh;
        this.currency = currency;
    }

    @Override
    public JobDto getJobDto() {
        String formattedSalary = Objects.nonNull(salaryLow) && Objects.nonNull(salaryHigh) ?
                salaryLow + " to " + salaryHigh + " " + currency
                : null;

        return new JobDto.Builder()
                .sourceJobId(jobId)
                .companyId(companyId)
                .title(title)
                .description(description)
                .url(url)
                .publishDate(createdAt)
                .salary(formattedSalary)
                .build();
    }

    @Override
    public String toString() {
        return "LandingJobsDto{" +
                "jobId='" + jobId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", salaryLow='" + salaryLow + '\'' +
                ", salaryHigh='" + salaryHigh + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }

    public String getJobId() {
        return jobId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getSalaryLow() {
        return salaryLow;
    }

    public String getSalaryHigh() {
        return salaryHigh;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LandingJobsDto that = (LandingJobsDto) o;
        return jobId.equals(that.jobId) && Objects.equals(companyId, that.companyId) && Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) && Objects.equals(url, that.url) &&
                Objects.equals(createdAt, that.createdAt) && Objects.equals(salaryLow, that.salaryLow) &&
                Objects.equals(salaryHigh, that.salaryHigh) && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, companyId, title, description, url, createdAt, salaryLow, salaryHigh, currency);
    }

}

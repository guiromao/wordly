package co.wordly.data.dto;

import java.util.Objects;

public class JobDto {

    private final String title;
    private final String description;
    private final String salary;
    private final String companyName;
    private final String url;
    private final String companyUrl;
    private final String companyLogoUrl;
    private final String sourceId;
    private final String sourceJobId;
    private final String publishDate;

    private JobDto(String title, String description, String salary, String companyName, String url, String companyUrl, String companyLogoUrl,
                  String sourceId, String sourceJobId, String publishDate) {
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.companyName = companyName;
        this.url = url;
        this.companyUrl = companyUrl;
        this.companyLogoUrl = companyLogoUrl;
        this.sourceId = sourceId;
        this.sourceJobId = sourceJobId;
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return "JobDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", salary='" + salary + '\'' +
                ", companyName='" + companyName + '\'' +
                ", url='" + url + '\'' +
                ", companyUrl='" + companyUrl + '\'' +
                ", companyLogoUrl='" + companyLogoUrl + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", sourceJobId='" + sourceJobId + '\'' +
                ", publishDate='" + publishDate + '\'' +
                '}';
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

    public String getCompanyName() {
        return companyName;
    }

    public String getUrl() {
        return url;
    }

    public String getCompanyUrl() {
        return companyUrl;
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

    public String getPublishDate() {
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
        JobDto jobDto = (JobDto) o;
        return Objects.equals(title, jobDto.title) &&
                Objects.equals(description, jobDto.description) && Objects.equals(salary, jobDto.salary) &&
                Objects.equals(companyName, jobDto.companyName) && Objects.equals(url, jobDto.url) &&
                Objects.equals(companyUrl, jobDto.companyUrl) && Objects.equals(companyLogoUrl, jobDto.companyLogoUrl) &&
                Objects.equals(sourceId, jobDto.sourceId) && Objects.equals(sourceJobId, jobDto.sourceJobId) &&
                Objects.equals(publishDate, jobDto.publishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, salary, companyName, url, companyUrl, companyLogoUrl, sourceId, sourceJobId, publishDate);
    }

    public static class Builder {

        private String title;
        private String description;
        private String salary;
        private String companyName;
        private String url;
        private String companyUrl;
        private String companyLogoUrl;
        private String sourceId;
        private String sourceJobId;
        private String publishDate;

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

        public Builder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder companyUrl(String companyUrl) {
            this.companyUrl = companyUrl;
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

        public Builder publishDate(String publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        public JobDto build() {
            return new JobDto(
                    title, description, salary, companyName, url,
                    companyUrl, companyLogoUrl, sourceId,
                    sourceJobId, publishDate
            );
        }

    }

}

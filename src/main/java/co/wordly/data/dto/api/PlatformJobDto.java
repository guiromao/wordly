package co.wordly.data.dto.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonPropertyOrder(alphabetic = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public record PlatformJobDto (@JsonProperty(PROPERTY_ID) String id,
                             @JsonProperty(PROPERTY_TITLE) String title,
                             @JsonProperty(PROPERTY_DESCRIPTION) String description,
                             @JsonProperty(PROPERTY_COMPANY_NAME) String companyName,
                             @JsonProperty(PROPERTY_COMPANY_LOGO_URL) String companyLogoUrl,
                             @JsonProperty(PROPERTY_SALARY) String salary,
                             @JsonProperty(PROPERTY_URL) String url,
                             @JsonProperty(PROPERTY_PUBLISH_DATE) LocalDateTime publishDate) implements Serializable {

    private static final Long serialId = 2L;

    private static final String PROPERTY_ID = "id";
    private static final String PROPERTY_TITLE = "title";
    private static final String PROPERTY_DESCRIPTION = "description";
    private static final String PROPERTY_COMPANY_NAME = "companyName";
    private static final String PROPERTY_SALARY = "salary";
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_COMPANY_LOGO_URL = "companyLogoUrl";
    private static final String PROPERTY_PUBLISH_DATE = "publishDate";

    public static class Builder {

        private String id;
        private String title;
        private String description;
        private String companyName;
        private String companyLogoUrl;
        private String salary;
        private String url;
        private LocalDateTime publishDate;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public Builder companyLogoUrl(String companyLogoUrl) {
            this.companyLogoUrl = companyLogoUrl;
            return this;
        }

        public Builder salary(String salary) {
            this.salary = salary;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder publishDate(LocalDateTime publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        public PlatformJobDto build() {
            return new PlatformJobDto(id, title, description, companyName, companyLogoUrl,
                    salary, url, publishDate);
        }

    }

}

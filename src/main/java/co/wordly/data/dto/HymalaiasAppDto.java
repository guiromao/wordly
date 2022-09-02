package co.wordly.data.dto;

import co.wordly.data.dto.enums.HymalaiasCategory;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;
import java.util.Set;

@JsonPropertyOrder(alphabetic = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class HymalaiasAppDto implements ApiDto {

    private static final String PROPERTY_TITLE = "title";
    private static final String PROPERTY_DESCRIPTION = "description";
    private static final String PROPERTY_COMPANY_NAME = "companyName";
    private static final String PROPERTY_COMPANY_LOGO = "companyLogo";
    private static final String PROPERTY_CATEGORIES = "categories";
    private static final String PROPERTY_PUBLISH_DATE = "pubDate";
    private static final String PROPERTY_APPLICATION_LINK = "applicationLink";
    private static final String PROPERTY_GUID = "guid";

    private final String title;
    private final String description;
    private final String companyName;
    private final String companyLogoUrl;

    // To change to set of enum?
    private final Set<String> categories;
    private final Integer pubDate;
    private final String url;
    private final String guid;

    @JsonCreator
    public HymalaiasAppDto(@JsonProperty(PROPERTY_TITLE) String title,
                           @JsonProperty(PROPERTY_DESCRIPTION) String description,
                           @JsonProperty(PROPERTY_COMPANY_NAME) String companyName,
                           @JsonProperty(PROPERTY_COMPANY_LOGO) String companyLogoUrl,
                           @JsonProperty(PROPERTY_CATEGORIES) Set<String> categories,
                           @JsonProperty(PROPERTY_PUBLISH_DATE) Integer pubDate,
                           @JsonProperty(PROPERTY_APPLICATION_LINK) String url,
                           @JsonProperty(PROPERTY_GUID) String guid) {
        this.title = title;
        this.description = description;
        this.companyName = companyName;
        this.companyLogoUrl = companyLogoUrl;
        this.categories = categories;
        this.pubDate = pubDate;
        this.url = url;
        this.guid = guid;
    }

    @Override
    public JobDto getJobDto() {
        return new JobDto.Builder()
                .title(title)
                .description(description)
                .companyName(companyName)
                .companyLogoUrl(companyLogoUrl)
                .publishDate(String.valueOf(pubDate))
                .url(url)
                .sourceJobId(guid)
                .build();
    }

    @Override
    public String toString() {
        return "HymalaiasAppDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyLogoUrl='" + companyLogoUrl + '\'' +
                ", categories=" + categories +
                ", pubDate=" + pubDate +
                ", url='" + url + '\'' +
                ", guid='" + guid + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyLogoUrl() {
        return companyLogoUrl;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public Integer getPubDate() {
        return pubDate;
    }

    public String getUrl() {
        return url;
    }

    public String getGuid() {
        return guid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HymalaiasAppDto that = (HymalaiasAppDto) o;
        return Objects.equals(title, that.title) && Objects.equals(description, that.description) &&
                Objects.equals(companyName, that.companyName) && Objects.equals(companyLogoUrl, that.companyLogoUrl) &&
                Objects.equals(categories, that.categories) && Objects.equals(pubDate, that.pubDate) &&
                Objects.equals(url, that.url) && Objects.equals(guid, that.guid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, companyName, companyLogoUrl, categories, pubDate, url, guid);
    }

}

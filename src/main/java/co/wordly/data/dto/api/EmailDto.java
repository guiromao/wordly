package co.wordly.data.dto.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.Set;

public class EmailDto {

    private static final String PROPERTY_EMAIL = "email";
    private static final String PROPERTY_KEYWORDS = "keywords";

    private final String email;
    private final Set<String> keywords;

    @JsonCreator
    public EmailDto(@JsonProperty(PROPERTY_EMAIL) String email,
                    @JsonProperty(PROPERTY_KEYWORDS) Set<String> keywords) {
        this.email = email;
        this.keywords = keywords;
    }

    @JsonProperty(PROPERTY_EMAIL)
    public String getEmail() {
        return email;
    }

    @JsonProperty(PROPERTY_KEYWORDS)
    public Set<String> getKeywords() {
        return keywords;
    }

    @Override
    public String toString() {
        return "EmailDto{" +
                "email='" + email + '\'' +
                ", keywords=" + keywords +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmailDto emailDto = (EmailDto) o;
        return email.equals(emailDto.email) && Objects.equals(keywords, emailDto.keywords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, keywords);
    }

}

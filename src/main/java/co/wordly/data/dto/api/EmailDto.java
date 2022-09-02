package co.wordly.data.dto.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.Set;

public class EmailDto {

    private static final String PROPERTY_EMAIL = "email";
    private static final String PROPERTY_TERMS = "terms";

    private final String email;
    private final Set<String> terms;

    @JsonCreator
    public EmailDto(@JsonProperty(PROPERTY_EMAIL) String email,
                    @JsonProperty(PROPERTY_TERMS) Set<String> terms) {
        this.email = email;
        this.terms = terms;
    }

    @JsonProperty(PROPERTY_EMAIL)
    public String getEmail() {
        return email;
    }

    @JsonProperty(PROPERTY_TERMS)
    public Set<String> getTerms() {
        return terms;
    }

    @Override
    public String toString() {
        return "EmailDto{" +
                "email='" + email + '\'' +
                ", terms=" + terms +
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
        return email.equals(emailDto.email) && Objects.equals(terms, emailDto.terms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, terms);
    }

}

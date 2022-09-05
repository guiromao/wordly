package co.wordly.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;
import java.util.Set;

@Document(collection = EmailEntity.EMAIL_COLLECTION)
@TypeAlias(EmailEntity.EMAIL_COLLECTION)
public class EmailEntity {

    private static final String FIELD_KEYWORDS = "keywords";

    public static final String EMAIL_COLLECTION = "email";

    @Id
    private final String email;

    // Field to store the terms that the user looks for (eg, "Java", "Backend", etc)
    @Field(FIELD_KEYWORDS)
    private final Set<String> keywords;

    @PersistenceCreator
    public EmailEntity(String email, Set<String> keywords) {
        this.email = email;
        this.keywords = keywords;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    @Override
    public String toString() {
        return "EmailEntity{" +
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
        EmailEntity that = (EmailEntity) o;
        return email.equals(that.email) && Objects.equals(keywords, that.keywords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, keywords);
    }

}

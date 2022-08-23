package co.wordly.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;
import java.util.UUID;

@Document(value = CompanyEntity.COMPANY_COLLECTION)
@TypeAlias(CompanyEntity.COMPANY_COLLECTION)
public class CompanyEntity {

    private static final String FIELD_NAME = "name";

    public static final String COMPANY_COLLECTION = "company";

    @Id
    private final String id;

    @Field(FIELD_NAME)
    private final String name;

    @PersistenceCreator
    public CompanyEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CompanyEntity create(String name) {
        final String companyId = UUID.randomUUID().toString();

        return new CompanyEntity(companyId, name);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyEntity company = (CompanyEntity) o;
        return id.equals(company.id) && Objects.equals(name, company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}

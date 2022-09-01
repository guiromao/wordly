package co.wordly.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

// Represents the Company that offers the Job
@Document(value = CompanyEntity.COMPANY_COLLECTION)
@TypeAlias(CompanyEntity.COMPANY_COLLECTION)
@CompoundIndex(name = "idx_name_text", def = "{'name': 'text'}")
@CompoundIndex(name = "idx_name", def = "{'name': 1}")
@CompoundIndex(name = "idx_relations", def = "{'sourceRelations': 1}")
public class CompanyEntity {

    private static final String FIELD_NAME = "name";
    private static final String FIELD_SOURCE_RELATIONS = "sourceRelations";

    public static final String COMPANY_COLLECTION = "company";

    @Id
    private final String id;

    @Field(FIELD_NAME)
    private final String name;

    // Maps the Sources that have this Company listed, and the ID of that Company
    // for that same Source
    @Field(FIELD_SOURCE_RELATIONS)
    private final Map<String, String> sourceRelations;

    @PersistenceCreator
    public CompanyEntity(String id, String name, Map<String, String> sourceRelations) {
        this.id = id;
        this.name = name;
        this.sourceRelations = sourceRelations;
    }

    public static CompanyEntity create(String name, Map<String, String> firstRelation) {
        final String companyId = UUID.randomUUID().toString();

        return new CompanyEntity(companyId, name, firstRelation);
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

    public Map<String, String> getSourceRelations() {
        return sourceRelations;
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
        return id.equals(company.id) && Objects.equals(name, company.name) &&
                Objects.equals(sourceRelations, company.sourceRelations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sourceRelations);
    }

}

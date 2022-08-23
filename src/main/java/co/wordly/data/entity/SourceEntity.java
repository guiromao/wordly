package co.wordly.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;
import java.util.UUID;

// Class to represent the Website where the Job is collected from
@Document(value = SourceEntity.SOURCE_COLLECTION)
@TypeAlias(SourceEntity.SOURCE_COLLECTION)
public class SourceEntity {

    private static final String FIELD_NAME = "name";

    public static final String SOURCE_COLLECTION = "source";

    @Id
    private final String id;

    @Field(FIELD_NAME)
    private final String name;

    @PersistenceCreator
    public SourceEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SourceEntity create(String name) {
        final String sourceId = UUID.randomUUID().toString();

        return new SourceEntity(sourceId, name);
    }

    @Override
    public String toString() {
        return "Source{" +
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
        SourceEntity source = (SourceEntity) o;
        return id.equals(source.id) && name.equals(source.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}

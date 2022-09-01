package co.wordly.data.dto.company;

import java.util.Objects;

public class CompanyDto {

    private final String id;
    private final String name;
    private final String sourceName;

    public CompanyDto(String id, String name, String sourceName) {
        this.id = id;
        this.name = name;
        this.sourceName = sourceName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSourceName() {
        return sourceName;
    }

    @Override
    public String toString() {
        return "CompanyDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sourceName='" + sourceName + '\'' +
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
        CompanyDto that = (CompanyDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(sourceName, that.sourceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sourceName);
    }

}

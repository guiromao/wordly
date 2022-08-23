package co.wordly.data.repository;

import co.wordly.data.entity.SourceEntity;

import java.util.List;
import java.util.Set;

public interface SourceRepositoryCustom {

    List<SourceEntity> findByNames(Set<String> names);

}

package co.wordly.data.repository;

import co.wordly.data.entity.CompanyEntity;

import java.util.List;
import java.util.Set;

public interface CompanyRepositoryCustom {

    List<CompanyEntity> findByNames(Set<String> companyNames);

    // Companies with existing name, but without the Source ID listed there
    List<CompanyEntity> findCompaniesToAddId(Set<String> companyNames, String sourceId);

    List<CompanyEntity> findBySource(String sourceId);

}

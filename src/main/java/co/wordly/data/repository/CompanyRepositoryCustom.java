package co.wordly.data.repository;

import java.util.Set;

public interface CompanyRepositoryCustom {

    Set<String> findExisting(Set<String> companyNames);

}

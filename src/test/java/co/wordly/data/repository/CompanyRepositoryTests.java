package co.wordly.data.repository;

import co.wordly.data.entity.CompanyEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CompanyRepositoryTests {

    @Autowired
    CompanyRepository companyRepository;

    Set<CompanyEntity> companies = Set.of(
            new CompanyEntity("id-1", "Microsoft",
                    Map.of("source-1", "id-source-1",
                            "source-2", "id-source-2",
                            "source-3", "id-source-3")),
            new CompanyEntity("id-2", "Google",
                    Map.of("source-1", "id-source-4",
                            "source-2", "id-source-5",
                            "source-3", "id-source-6")),
            new CompanyEntity("id-3", "Oracle",
                              Map.of("source-1", "id-source-7",
                                      "source-3", "id-source-3"))
    );

    @BeforeEach
    void setup() {
        companyRepository.saveAll(companies);
    }

    @Test
    void testFindByNames() {
        Set<String> searchedNames = Set.of("Google", "Oracle");

        Set<CompanyEntity> expectedResults = companies.stream()
                .filter(company -> searchedNames.contains(company.getName()))
                .collect(Collectors.toSet());

        Set<CompanyEntity> test = new HashSet<>(companyRepository.findByNames(searchedNames));

        Assertions.assertEquals(expectedResults, test);
    }

    @Test
    void testFindBySource() {
        final String sourceId = "source-2";

        List<CompanyEntity> expectedCompanies = companies.stream()
                .filter(company -> company.getSourceRelations().containsKey(sourceId))
                .toList();

        List<CompanyEntity> test = companyRepository.findBySource(sourceId);

        Assertions.assertEquals(new HashSet<>(expectedCompanies), new HashSet<>(test));
    }

    @Test
    void testFindCompanyToAddSource() {
        final String sourceId = "source-2";

        List<CompanyEntity> expectedCompanies = companies.stream()
                .filter(company -> !company.getSourceRelations().containsKey(sourceId))
                .toList();

        List<CompanyEntity> test = companyRepository.findCompaniesToAddId(Set.of("Microsoft",
                "Google", "Oracle"), sourceId);

        Assertions.assertEquals(expectedCompanies, test);
    }

}

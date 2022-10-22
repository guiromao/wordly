package co.wordly.service;

import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.company.CompanyDto;
import co.wordly.data.entity.CompanyEntity;
import co.wordly.data.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CompanyServiceTest {

    static final LocalDateTime TIME = LocalDateTime.now();

    SourceService sourceService;
    CompanyRepository companyRepository;

    CompanyService companyService;

    @BeforeEach
    void setup() {
        companyRepository = mock(CompanyRepository.class);
        sourceService = mock(SourceService.class);
        companyService = new CompanyServiceImpl(companyRepository, sourceService);

        when(companyRepository.findByNames(any(Set.class))).thenReturn(companyEntities());
        when(companyRepository.findCompaniesToAddId(any(Set.class), anyString())).thenReturn(unmappedCompanyEntities());
    }

    @Test
    void testHandleCompanies() {
        companyService.handleCompanies(companyDtos(), "remotive-id");

        verify(companyRepository, times(1)).saveAll(any(Iterable.class));
    }

    @Test
    void testHandleCompaniesOf() {
        when(companyRepository.findBySource(anyString())).thenReturn(companyEntities());
        when(sourceService.getNameFromId(anyString())).thenReturn("Remotive");

        companyService.handleCompanies(companyDtos(), "remotive-id");

        verify(companyRepository, times(1)).saveAll(any(Iterable.class));
    }

    private Set<CompanyDto> companyDtos() {
        return Set.of(
                new CompanyDto("uefa-id", "UEFA", "Remotive")
        );
    }

    private List<CompanyEntity> companyEntities() {
        Map<String, String> googleSourcesMap = new HashMap<>();
        Map<String, String> oracleSourcesMap = new HashMap<>();

        googleSourcesMap.put("remotive-id", "123");
        googleSourcesMap.put("landingjibs-id", "abc");

        oracleSourcesMap.put("remotive-id", "456");
        oracleSourcesMap.put("landingjobs-id", "def");

        return List.of(
                new CompanyEntity("google-id", "Google", googleSourcesMap),
                new CompanyEntity("oracle-id", "Oracle", oracleSourcesMap)
        );
    }

    private List<CompanyEntity> unmappedCompanyEntities() {
        return List.of(
                new CompanyEntity("jetbrains-id", "JetBrains", new HashMap<>())
        );
    }

    private Set<JobDto> jobDtos() {
        return Set.of(
                new JobDto.Builder()
                        .title("Software Engineer Kotlin")
                        .description("Software Engineer for great projects")
                        .publishDate(TIME.toString())
                        .sourceId("remotive")
                        .sourceJobId("remotive-job-id-1")
                        .companyName("GreenTech")
                        .companyLogoUrl("http://greentech.com/1.png")
                        .companyUrl("http://greentech.com")
                        .url("https://job.com/software-engineer")
                        .salary("$60000")
                        .build(),
                new JobDto.Builder()
                        .title("Software Engineer Java")
                        .description("Software Engineer for great projects")
                        .publishDate(TIME.toString())
                        .sourceId("remotive")
                        .sourceJobId("remotive-job-id-2")
                        .companyName("HealthTech")
                        .companyLogoUrl("http://healthtech.com/1.png")
                        .companyUrl("http://healthtech.com")
                        .url("https://job.com/software-engineer-java")
                        .salary("$65000")
                        .build()
        );
    }

}

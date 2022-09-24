package co.wordly.service;

import co.wordly.data.dto.JobDto;
import co.wordly.data.dto.apiresponse.ApiResponse;
import co.wordly.data.dto.company.CompanyDto;
import co.wordly.data.entity.CompanyEntity;
import co.wordly.data.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final SourceService sourceService;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository,
                              SourceService sourceService) {
        this.companyRepository = companyRepository;
        this.sourceService = sourceService;
    }

    @Override
    public void handleCompaniesOf(Set<JobDto> jobs, final String sourceId) {
        List<CompanyEntity> preSavedCompanies = resolveCompanies(jobs, sourceId);
        String sourceName = sourceService.getNameFromId(sourceId);

        Set<CompanyDto> companyDtos = jobs.stream()
                .map(job -> new CompanyDto(job.getCompanyId(),
                        resolveCompanyName(job, sourceId, preSavedCompanies), sourceName))
                .collect(Collectors.toSet());

        handle(companyDtos, sourceId);
    }

    @Override
    public void handleCompanies(Set<CompanyDto> companies, String sourceId) {
        handle(companies, sourceId);
    }

    private void handle(Set<CompanyDto> companyDtos, String sourceId) {
        Map<String, String> uniqueCompanies = companyDtos.stream()
                .distinct()
                .filter(dto -> Objects.nonNull(dto.getName()))
                .collect(Collectors.toMap(CompanyDto::getName,
                        dto -> Optional.ofNullable(dto.getId()).orElse(sourceId)));

        Set<String> existingCompanies = companyRepository.findByNames(new HashSet<>(uniqueCompanies.keySet())).stream()
                .map(CompanyEntity::getName)
                .collect(Collectors.toSet());

        Set<CompanyEntity> companiesToSave = uniqueCompanies.keySet().stream()
                .filter(companyName -> !existingCompanies.contains(companyName))
                .map(companyName -> {
                    Map<String, String> firstSource = new HashMap<>();
                    firstSource.put(sourceId, uniqueCompanies.get(companyName));
                    return CompanyEntity.create(companyName, firstSource);
                })
                .collect(Collectors.toSet());

        Set<CompanyEntity> companiesToSaveId =
                new HashSet<>(companyRepository.findCompaniesToAddId(uniqueCompanies.keySet(), sourceId));
        companiesToSaveId.forEach(company -> company.getSourceRelations().put(sourceId, uniqueCompanies.get(company.getName())));

        companiesToSave.addAll(companiesToSaveId);

        companyRepository.saveAll(companiesToSave);
    }

    private List<CompanyEntity> resolveCompanies(Set<JobDto> jobs, String sourceId) {
        Set<String> companyIds = jobs.stream()
                .map(JobDto::getCompanyId)
                .collect(Collectors.toSet());

        if (!CollectionUtils.isEmpty(companyIds)) {
            return companyRepository.findBySource(sourceId);
        }

        return Collections.emptyList();
    }

    private String resolveCompanyName(JobDto jobDto, String sourceId,
                                      List<CompanyEntity> companies) {
        if (Objects.nonNull(jobDto.getCompanyName())) {
            return jobDto.getCompanyName();
        }

        return companies.stream()
                .filter(company -> jobDto.getCompanyId().equals(company.getSourceRelations().get(sourceId)))
                .map(CompanyEntity::getName)
                .findFirst()
                .orElse(null);
    }
}

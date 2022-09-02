package co.wordly.data.converter;

import co.wordly.data.dto.api.PlatformJobDto;
import co.wordly.data.entity.JobEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PlatformJobConverter {

    public static Set<PlatformJobDto> convert(List<JobEntity> jobs) {
        return jobs.stream()
                .map(PlatformJobConverter::convert)
                .collect(Collectors.toSet());
    }

    public static PlatformJobDto convert(JobEntity job) {
        return new PlatformJobDto.Builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .salary(job.getSalary())
                .url(job.getUrl())
                .companyLogoUrl(job.getCompanyLogoUrl())
                .build();
    }

}

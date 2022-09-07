package co.wordly.data.converter;

import co.wordly.data.dto.api.PlatformJobDto;
import co.wordly.data.entity.JobEntity;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PlatformJobConverter {

    private PlatformJobConverter() {
        // no instantiation
    }

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
                .publishDate(job.getPublishDate())
                .build();
    }

    public static String convertToEmailList(Set<JobEntity> jobs) {
        return jobs.stream()
                .map(PlatformJobConverter::convertToEmail)
                .reduce("", (subTotal, element) -> subTotal + element + "\n\n");
    }

    public static String convertToEmail(JobEntity job) {
        StringBuilder stringBuilder = new StringBuilder().append("Title: ").append(job.getTitle()).append("\n")
                .append("Description: ").append(job.getDescription()).append("\n")
                .append("URL: ").append(job.getUrl()).append("\n");

        if (StringUtils.hasText(job.getSalary())) {
            stringBuilder.append("Salary: ").append(job.getSalary()).append("\n");
        }

        return stringBuilder.append("Publish Date: \n").append(job.getPublishDate())
                .append("\n\n###########################\n\n\n").toString();
    }

}

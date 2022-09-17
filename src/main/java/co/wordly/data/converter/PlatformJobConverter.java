package co.wordly.data.converter;

import co.wordly.data.dto.api.PlatformJobDto;
import co.wordly.data.entity.JobEntity;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PlatformJobConverter {

    private static final String NEW_LINE = System.getProperty("line.separator");

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
        StringBuilder stringBuilder = new StringBuilder().append("Title: ").append(job.getTitle()).append(NEW_LINE)
                .append("Description: ").append(job.getDescription()).append(NEW_LINE)
                .append("URL: ").append(job.getUrl()).append(NEW_LINE);

        if (StringUtils.hasText(job.getSalary())) {
            stringBuilder.append("Salary: ").append(job.getSalary()).append(NEW_LINE);
        }

        return stringBuilder.append("Publish Date: \n").append(job.getPublishDate())
                .append(NEW_LINE).append(NEW_LINE)
                .append("###########################")
                .append(NEW_LINE).append(NEW_LINE).append(NEW_LINE)
                .toString();
    }

}

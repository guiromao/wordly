package co.wordly.data.converter;

import co.wordly.data.dto.api.PlatformJobDto;
import co.wordly.data.entity.JobEntity;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsLast;
import static java.util.Comparator.reverseOrder;

public class PlatformJobConverter {

    private static final String NEW_LINE = "<br>";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("E, MMM dd yyyy");

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
                .sorted(nullsLast(comparing(JobEntity::getPublishDate, reverseOrder())))
                .filter(Objects::nonNull)
                .map(PlatformJobConverter::convertToEmail)
                .reduce("", (subTotal, element) -> subTotal + element);
    }

    public static String convertToEmail(JobEntity job) {
        StringBuilder stringBuilder =
                new StringBuilder().append("<h2>").append(resolveUrl(job))
                        .append(job.getTitle())
                        .append(Objects.nonNull(job.getUrl()) ? "</a>" : "")
                        .append("</h2>")
                        .append(job.getDescription()).append(NEW_LINE)
                        .append("<h4>URL: ").append(job.getUrl()).append(NEW_LINE);

        if (StringUtils.hasText(job.getSalary())) {
            stringBuilder.append("Salary: ").append(job.getSalary()).append(NEW_LINE);
        }

        return stringBuilder.append("Publish Date: ")
                .append(DATE_FORMATTER.format(job.getPublishDate()))
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append("##########################################################</h4>")
                .append(NEW_LINE)
                .toString();
    }

    private static String resolveUrl(JobEntity job) {
        String result = "";

        if (Objects.nonNull(job.getUrl())) {
            result = "<a href=\"" + job.getUrl() + "\">";
        }

        return result;
    }

}

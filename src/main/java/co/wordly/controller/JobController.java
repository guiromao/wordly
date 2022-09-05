package co.wordly.controller;

import co.wordly.data.dto.api.PlatformJobDto;
import co.wordly.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public Set<PlatformJobDto> getJobs(@RequestParam(value = "keywords", required = false) Set<String> keywords,
                                       @RequestParam(value = "fromDate", required = false)
                                       @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime fromDate,
                                       @RequestParam(value = "toDate", required = false)
                                       @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime toDate,
                                       @RequestParam(value = "offset") Integer offset,
                                       @RequestParam(value = "limit") Integer limit) {
        return jobService.fetchJobs(keywords, fromDate, toDate, offset, limit);
    }

}

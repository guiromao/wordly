package co.wordly.controller;

import co.wordly.data.dto.api.PlatformJobDto;
import co.wordly.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Set<PlatformJobDto> getJobs(@RequestParam(value = "text", required = false) String text,
                                       @RequestParam(value = "fromDate", required = false) LocalDateTime fromDate,
                                       @RequestParam(value = "toDate", required = false) LocalDateTime toDate,
                                       @RequestParam(value = "offset") Integer offset,
                                       @RequestParam(value = "limit") Integer limit) {
        return jobService.fetchJobs(text, fromDate, toDate, offset, limit);
    }

}

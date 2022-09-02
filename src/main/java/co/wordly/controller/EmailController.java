package co.wordly.controller;

import co.wordly.data.dto.api.EmailDto;
import co.wordly.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public EmailDto createEmail(@RequestBody EmailDto emailDto) {
        return emailService.save(emailDto);
    }

    @GetMapping("/{email}")
    public EmailDto getEmail(@PathVariable(value = "email") String email) {
        return emailService.getEmail(email);
    }

}

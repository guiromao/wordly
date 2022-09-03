package co.wordly.service;

import co.wordly.data.entity.JobEntity;

import java.util.Set;

public interface EmailSender {

    void send(String toEmail, Set<JobEntity> jobs);

}

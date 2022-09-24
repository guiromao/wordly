package co.wordly.data.converter;

import co.wordly.data.dto.api.EmailDto;
import co.wordly.data.entity.EmailEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class EmailConverter {

    private EmailConverter(){
        // no instantiation
    }

    public static Set<EmailDto> toDtoSet(Set<EmailEntity> emails) {
        return emails.stream()
                .map(EmailConverter::toDto)
                .collect(Collectors.toSet());
    }

    public static EmailDto toDto(EmailEntity email) {
        return new EmailDto(email.getEmail(), email.getKeywords());
    }

    public static EmailEntity toEntity(EmailDto dto) {
        return new EmailEntity(dto.getEmail(), dto.getKeywords());
    }

}

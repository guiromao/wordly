package co.wordly.data.repository;

import co.wordly.data.entity.EmailEntity;

import java.util.List;

public interface EmailRepositoryCustom {

    List<EmailEntity> findByOffsetAndLimit(int offset, int limit);

}

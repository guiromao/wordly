package co.wordly.service;

import co.wordly.data.entity.SourceEntity;
import co.wordly.data.repository.SourceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SourceServiceTest {

    SourceRepository sourceRepository;

    SourceService sourceService;

    @BeforeEach
    void setup() {
        sourceRepository = mock(SourceRepository.class);

        sourceService = new SourceServiceImpl(sourceRepository);
    }

    @Test
    void testGetIdFromNameNotExisting() {
        when(sourceRepository.findByName(anyString())).thenReturn(Optional.empty());

        String test = sourceService.getIdFromName("not-existing-name");

        Assertions.assertEquals("", test);
    }

    @Test
    void testGetIdFromName() {
        when(sourceRepository.findByName(anyString())).thenReturn(Optional.of(sourceEntity()));

        String test = sourceService.getIdFromName("Remotive");

        Assertions.assertEquals(sourceEntity().getId(), test);
    }

    @Test
    void testGetNameFromIdNotExisting() {
        when(sourceRepository.findById(anyString())).thenReturn(Optional.empty());

        String test = sourceService.getNameFromId("not-existing-id");

        Assertions.assertNull(test);
    }

    @Test
    void testGetNameFromId() {
        when(sourceRepository.findById(anyString())).thenReturn(Optional.of(sourceEntity()));

        String test = sourceService.getNameFromId("remotive-id");

        Assertions.assertEquals(sourceEntity().getName(), test);
    }

    @Test
    void testHandleEmptySources() {
        when(sourceRepository.findByNames(any(Set.class))).thenReturn(Collections.emptyList());

        sourceService.handle(Collections.emptySet());

        verify(sourceRepository, never()).saveAll(any(Iterable.class));
    }

    @Test
    void testHandle() {
        when(sourceRepository.findByNames(any(Set.class))).thenReturn(List.of(sourceEntity()));

        sourceService.handle(Set.of("Remotive", "Landing Jobs"));

        verify(sourceRepository, times(1)).saveAll(any(Iterable.class));
    }

    private SourceEntity sourceEntity() {
        return new SourceEntity("remotive-id", "Remotive");
    }

}

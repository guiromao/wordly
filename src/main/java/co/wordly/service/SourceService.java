package co.wordly.service;

import java.util.Set;

public interface SourceService {

    void handle(Set<String> sourceNames);

    String getIdFromName(String name);

    String getNameFromId(String id);

}

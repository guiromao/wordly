package co.wordly.configuration;

import java.util.Set;

public final class HymalaiasAppConfig {

    public static final Set<String> SUPPORTED_CATEGORIES = Set.of(
            "Data-Analyst", "Data-Scientist", "Software-Engineer",
            "Front-End-Developer", "Back-End-Developer",
            "Information-Security-Developer", "Security-Engineer",
            "Full-Stack-Engineer", "Data-Developer", "Data-Science",
            "Blockchain-Developer"
    );

    private HymalaiasAppConfig() {
        // no instantiation
    }

}

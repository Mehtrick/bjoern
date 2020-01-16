package de.mehtrick.bjoern.generator.junitsupport;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Enum to check the supported junitVersions
 * Currently version 4 and 5 are supported
 */
public enum SupportedJunitVersion {
    junit4(4, org.junit.Test.class, Before.class, Junit4GenerationStrategy.class), junit5(5, org.junit.jupiter.api.Test.class, BeforeEach.class, Junit5GenerationStrategy.class);
    private final int junitVersion;
    private final Class<?> testAnnotationClass;
    private final Class<? extends JunitGenerationStrategy> junitGenerationStrategyClass;


    private final Class<?> beforeAnnotationClass;

    SupportedJunitVersion(int versionnumber, Class<?> testAnnotationClass, Class<?> beforeAnnotationClass, Class<? extends JunitGenerationStrategy> junitGenerationStrategyClass) {
        this.junitVersion = versionnumber;
        this.testAnnotationClass = testAnnotationClass;
        this.beforeAnnotationClass = beforeAnnotationClass;
        this.junitGenerationStrategyClass = junitGenerationStrategyClass;
    }

    public static SupportedJunitVersion getByVersionnumber(String versionNumber) throws NotSupportedJunitVersionException {
        return Arrays.stream(SupportedJunitVersion.values()).filter(js -> Objects.equals(versionNumber, String.valueOf(js.junitVersion))).findFirst().orElseThrow(NotSupportedJunitVersionException::new);
    }

    public static Set<String> getSupportedVersionNumbers() {
        return Arrays.stream(SupportedJunitVersion.values()).map(js -> String.valueOf(js.junitVersion)).collect(Collectors.toSet());
    }

    public Class<?> getTestAnnotationClass() {
        return testAnnotationClass;
    }

    public Class<?> getBeforeAnnotationClass() {
        return beforeAnnotationClass;
    }

    public JunitGenerationStrategy getJunitStrategy() {
        try {
            return junitGenerationStrategyClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}

package de.mehtrick.bjoern.base;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import de.mehtrick.bjoern.parser.modell.Bjoern;
import de.mehtrick.bjoern.parser.modell.BjoernScenario;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Enum to check the supported junitVersions
 * Currently version 4 and 5 are supported
 */
public enum SupportedJunitVersion {
    junit4(4, org.junit.Test.class, Before.class), junit5(5, org.junit.jupiter.api.Test.class, BeforeEach.class);

    private final int junitVersion;
    private final Class<?> testAnnotationClass;


    private final Class<?> beforeAnnotationClass;

    SupportedJunitVersion(int versionnumber, Class<?> testAnnotationClass, Class<?> beforeAnnotationClass) {
        this.junitVersion = versionnumber;
        this.testAnnotationClass = testAnnotationClass;
        this.beforeAnnotationClass = beforeAnnotationClass;
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

    public void addExtraAnnotations(MethodSpec.Builder main, BjoernScenario scenario) {
        if (this.equals(junit5)) {
            main.addAnnotation(AnnotationSpec.builder(DisplayName.class)
                    .addMember("value", "$S", "Scenario: " + scenario.getName())
                    .build());
        }
    }

    public void addExtraClassAnnotation(TypeSpec.Builder featureClassBuilder, Bjoern bjoern) {
        if (this.equals(junit5)) {
            featureClassBuilder.addAnnotation(AnnotationSpec.builder(DisplayName.class)
                    .addMember("value", "$S", "Feature: " + bjoern.getFeature())
                    .build());
        }
    }
}

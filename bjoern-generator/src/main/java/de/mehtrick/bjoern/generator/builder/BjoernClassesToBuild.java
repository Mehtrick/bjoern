package de.mehtrick.bjoern.generator.builder;

import com.squareup.javapoet.TypeSpec;

/**
 * Bjoern generates two classes.
 * An Abstract Class that contains the Scenarios
 * An Interface that defines the test method signatures
 */
public class BjoernClassesToBuild {

    private TypeSpec featureClass;
    private TypeSpec featureInterface;

    public BjoernClassesToBuild(TypeSpec featureClass, TypeSpec featureInterface) {
        this.featureClass = featureClass;
        this.featureInterface = featureInterface;
    }

    public TypeSpec getFeatureClass() {
        return featureClass;
    }

    public TypeSpec getFeatureInterface() {
        return featureInterface;
    }

}

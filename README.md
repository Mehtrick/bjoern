# Bjoern [![Build Status](https://travis-ci.org/Mehtrick/bjoern.svg?branch=master)](https://travis-ci.org/Mehtrick/bjoern) ![Maven Central](https://img.shields.io/maven-central/v/de.mehtrick/bjoern-gradle-plugin)

 **B**ehaviordriven **J**avabased **O**nehundret and ten percent **E**fficient **R**eadable **N**otation

-----


Bjoern is a universal bdd test generater (and a person).
The main focus is to generate java-classes from bdd-style text files to ensure **synchronisation** between the specification and the code. 

The generated classes are **simple** and are designed to be generated **each time**. You **should not** edit the generated files because they will be deleted on every run of the generator.

The generated classes are plain Junit. You can extend them to define your own runners which makes it compatible with **spring** or **mockito**.

## How to use

As a gradle plugin

```gradle
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'de.mehtrick:bjoern-gradle-plugin:1.1.0'
    }
}
apply plugin: "de.mehtrick.bjoern.gradle-plugin"
```

you then have to configure the generator in gradle

```gradle
bjoern{
	folder = "${projectDir}/src/test/resources"
	pckg ="de.mehtrick.bjoern-sample"
	gendir = "${projectDir}/src/gen/bjoern"
}
```

### Available Tasks

| Taskname      | Description|
|-------|-------------------------|
|`bjoerngen`|Generates the abstract test classes| 
|`bjoerndoc`|Generates documentation of the bjoern files. (Default is asciidoc)|

### List of Parameters

| Parameter name |required| Description | example |
|----------------|--------|-------------|---------|
|path|yes (if the folder is not set)|The absoulte path to your specification|"${projectDir}/src/test/resources/bjoern.yml"|
|folder|yes (if the path is not set. If both are set folder wins)|The absoulte path to the folder where all specifications are placed|"${projectDir}/src/test/resources"|
|pckg|yes|The package declaration of the generated classes|"de.mehtrick.bjoern-sample"|
|gendir|yes|The folder where all of the files will be generated|"${projectDir}/src/test/gen"|
|extendedTestClass|no|Fully qualified Name of class which all of the generated files will extend|"de.mehtrick.AbstractTestClass"|
|docdir|no(only when generating Docs)|The folder where all the documentations will be generated|"${projectDir}/src/test/resources"|
|template|no|Name of the freemarker Templatefile that will be loaded during generation. Default is an asciidoc template, which is part of the classpath|"/asciidoc.ftlh"|
|templatefolder|no|The folder where selfwritten freemarker templates for documentationgeneration can be placed|"${projectDir}/src/main/resources/templates"|
|docExtension|no|The extension of the generated Doc Files. Default is adoc|"adoc"|

## Specification

The specification is yaml based. You will find the typical BDD keywords in it

```yaml
Feature: Test Foo
Background:
  Given: 
    - A Foo
    - A Bar
Scenarios: 
  - Scenario: Foo is not happy
    Given: 
      - there are "2" bottles of wine
      - there are "0" bottles of beer
    When:
      - Foo wants to drink "1" bottle of beer
    Then:
      - Foo says "why is my beer empty"  
  - Scenario: Foo is happy
    Given: 
      - there are "2" bottles of wine
      - there are "1" bottles of beer
    When:
      - Foo wants to drink "1" bottle of beer
    Then:
      - Foo says "yeah beer"  
```

## Code generation

Bjoern will then generate the TestClasses based on the spec

```java
import java.lang.String;
import org.junit.Before;
import org.junit.Test;

/**
 * Test Foo */
public abstract class AbstractTestFoo {
  @Before
  public void background() {
    given_AFoo();
    given_ABar();
  }

  /**
   * Foo is not happy */
  @Test
  public void FooIsNotHappy() {
    given_ThereAreBottlesOfWine("2");
    given_ThereAreBottlesOfBeer("0");
    when_FooWantsToDrinkBottleOfBeer("1");
    then_FooSays("why is my beer empty");
  }

  /**
   * Foo is happy */
  @Test
  public void GetraenkVorhanden() {
    given_ThereAreBottlesOfWine("2");
    given_ThereAreBottlesOfBeer("1");
    when_FooWantsToDrinkBottleOfBeer("1");
    then_FooSays("yeah beer");
  }

  public abstract void given_ThereAreBottlesOfBeer(String param1);

  public abstract void given_ABar();

  public abstract void when_FooWantsToDrinkBottleOfBeer(String param1);

  public abstract void given_ThereAreBottlesOfWine(String param1);

  public abstract void given_AFoo();

  public abstract void then_FooSays(String param1);
}

```

## Doc Generation

### Example Asciidoc

```adoc
= Test Foo
:toc:

== Background
[cols="10%,90%"]
|===
|*Given* |A Foo
>|*And* |A Bar
|===


== Scenario: Foo is not happy
[cols="10%,90%"]
|===
|*Given* |there are "2" bottles of wine
>|*And* |there are "0" bottles of beer
|*When* |Foo wants to drink "1" bottle of beer
|*Then* |Foo says "why is my beer empty"
|===

== Scenario: Foo is happy
[cols="10%,90%"]
|===
|*Given* |there are "2" bottles of wine
>|*And* |there are "1" bottles of beer
|*When* |Foo wants to drink "1" bottle of beer
|*Then* |Foo says "yeah beer"
|===
```

The Developer now has to implement the missing methods. In one feature class the methods will be reused if their name is written the same all over the specification

## Project Structure

### Bjoern Parser

The parser project reads the bjoern file and parses it to java-classes using mainly jackson. It does not just convert it to pojos but reads things like parameters from the statements.
The Parse also contains a ```validator``` which helps you with the correct syntax. Keep in mind, that this might catch not all the exceptions, so you might get some jackson-mapping-exceptions as well.

### Bjoern Generator

Is basend on the ```Bjoern Parser``` and generates java class files from the pojos of the ```Bjoern Parser```. This is done by using java poet. Every feature will generate a coresponding class and every scenario in that feature will become a test method. The BDD statements like ```Given When Then``` will generate abstract methods which need to be implemented by the developer.

### Bjoern Gradle Generator

Is basend on the ```Bjoern Generator``` and wraps its functionality into a gradle plugin. The major task is called ```bjoern``` which will generate the java classes based on your gradle-bjoern-config.

### Bjoern Base Generator

Project to share base functionality like configuration or file-filter for every generator

### Bjoern Doc Generator

Generates documentations based on the bjoern files. It uses apache freemarker as template engine. As default the generator will create asciidoc files. Other formats aren't supported yet. However you can create your own freemarker templates and configure them via gradle

## How to build

### Build

```gradle

gradlew build
```

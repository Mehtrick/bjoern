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
        classpath 'de.mehtrick:bjoern-gradle-plugin:1.2.6'
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

| Parameter name |required| Description |Default value| example |
|----------------|--------|-------------|---------|---------|
|path|yes (if the folder is not set)|The absoulte path to your specification||"${projectDir}/src/test/resources/bjoern.zgr"|
|folder|yes (if the path is not set. If both are set folder wins)|The absoulte path to the folder where all specifications are placed||"${projectDir}/src/test/resources"|
|pckg|yes|The package declaration of the generated classes||"de.mehtrick.bjoern-sample"|
|gendir|yes|The folder where all of the files will be generated||"${projectDir}/src/test/gen"|
|extendedTestClass|no|Fully qualified Name of class which all of the generated files will extend||"de.mehtrick.AbstractTestClass"|
|docdir|no(only when generating Docs)|The folder where all the documentations will be generated||"${projectDir}/src/test/resources"|
|template|no|Name of the freemarker Templatefile that will be loaded during generation. Default is an asciidoc template, which is part of the classpath|"/asciidoc.ftlh"|"/asciidoc.ftlh"|
|templatefolder|no|The folder where selfwritten freemarker templates for documentationgeneration can be placed|By default the internal bjoern templates will be used|"${projectDir}/src/main/resources/templates"|
|docExtension|no|The extension of the generated Doc Files.|"adoc"|"adoc"|
|junitVersion|no|The used junit-version. Possible values are 4 or 5. The value changes the used junit annotations, to secure the correct test functionality|"4"|"5"|
|encoding|no|The encoding used for the spec files. **This Encoding ist not used for the generated java files**. Bjoern uses [java poet](https://github.com/square/javapoet) which only generates UTF-8 files.|"UTF-8"|"UTF-16"|

## Specification

The specification is yaml based. Bjoern uses the .zgr file extension to determine the specification files.
You will find the typical BDD keywords in it. For more convenience there is a [VS-Code plugin](https://marketplace.visualstudio.com/items?itemName=mehtrick.bjoern#review-details) which greatly improves the usability of the specs.

`example.zgr`
```yaml
Feature: Test eines KassenAutomaten
Background:
  Given:
    - Ein typ der was trinken will
Scenarios:
  - Scenario: Getränk nicht vorhanden
    Given:
      - Ein Automat
      - Mit "2" Flaschen Cola
      - Mit "0" Flaschen Sprite
    When:
      - Kaufe "1" Sprite
    Then:
      - Automat sagt "alle"
      - Es existieren "2" Cola im Automaten
  - Scenario: Getraenk vorhanden
    Given:
      - Ein Automat
      - Mit "2" Flaschen Cola
      - Mit "0" Flaschen Sprite
    When:
      - Kaufe "1" Cola
    Then:
      - Automat sagt "ok"
      - Es existieren "1" Cola im Automaten

```

## Code generation

Bjoern will then generate the TestClasses based on the spec

First it will generate an abstract class which contains the test structure in form of the scenarios
```java
package de.mehtrick.bjoern;

import java.lang.Exception;
import org.junit.Before;
import org.junit.Test;

/**
 * Test eines KassenAutomaten */
public abstract class AbstractTestEinesKassenautomaten extends AbstractTestclass implements TestEinesKassenautomatenInterface {
  /**
   * Getränk nicht vorhanden */
  @Test
  public void getraenkNichtVorhanden() throws Exception {
    given_EinAutomat();
    given_MitFlaschenCola("2");
    given_MitFlaschenSprite("0");
    when_KaufeSprite("1");
    then_AutomatSagt("alle");
    then_EsExistierenColaImAutomaten("2");
  }

  /**
   * Getraenk vorhanden */
  @Test
  public void getraenkVorhanden() throws Exception {
    given_EinAutomat();
    given_MitFlaschenCola("2");
    given_MitFlaschenSprite("0");
    when_KaufeCola("1");
    then_AutomatSagt("ok");
    then_EsExistierenColaImAutomaten("1");
  }

  @Before
  public void background() throws Exception {
    given_EinTypDerWasTrinkenWill();
  }
}


```

Then bjoern will also generate Interfaces defining the test method signatures

``` java
package de.mehtrick.bjoern;

import java.lang.Exception;
import java.lang.String;

public interface TestEinesKassenautomatenInterface {
  void given_EinAutomat() throws Exception;

  void when_KaufeSprite(String param1) throws Exception;

  void then_EsExistierenColaImAutomaten(String param1) throws Exception;

  void given_EinTypDerWasTrinkenWill() throws Exception;

  void given_MitFlaschenSprite(String param1) throws Exception;

  void then_AutomatSagt(String param1) throws Exception;

  void given_MitFlaschenCola(String param1) throws Exception;

  void when_KaufeCola(String param1) throws Exception;
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

# Jvior

Jvior is a universal bdd test generater.

# How to use

As a gradle plugin
```
buildscript {
    repositories {
        jcenter()
        maven{
    	    url = "https://nexus.ppvoting.de/content/repositories/release/"
        }
    }
    dependencies {
        classpath 'de.mehtrick.jvior:jvior-gradle-plugin:0.1.4'
    }
}
apply plugin: "de.mehtrick.jvior.gradle-plugin"
```

you then have to configure the generator in gradle


```
jvior{
	folder = "${projectDir}/src/test/resources"
	pckg ="de.mehtrick.jvior-sample"
	gendir = "${projectDir}/src/gen/jvior"
}
```

| Parameter name |required| Description | example |
|----------------|--------|-------------|---------|
|path|yes (if the folder is not set)|The absoulte path to your specification|"${projectDir}/src/test/resources/jvior.yml"|
|folder|yes (if the path is not set. If both are set folder wins)|The absoulte path to the folder where all specifications are placed|"${projectDir}/src/test/resources"|
|pckg|yes|The package declaration of the generated classes|"de.mehtrick.jvior-sample"|
|gendir|yes|The folder where all of the files will be generated|"${projectDir}/src/test/gen"|
|extendedTestClass|no|Fully qualified Name of class which all of the generated files will extend|"de.mehtrick.AbstractTestClass"|

To run the generator just hit the gradle task `jvior`

# Specification
The specification is yaml based. You will find the typical BDD keywords in it

```
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
  - Scenario: Getraenk vorhanden
    Given: 
      - there are "2" bottles of wine
      - there are "1" bottles of beer
    When:
      - Foo wants to drink "1" bottle of beer
    Then:
      - Foo says "yeah beer"  
```

# Code generation

Jvior will then generate the TestClasses based on the spec

```
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
   * Getraenk vorhanden */
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

The Developer now has to implement the missing methods. In one feature class the methods will be reused if their name is written the same all over the specification
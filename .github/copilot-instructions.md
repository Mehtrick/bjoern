# Copilot Instructions for Bjoern

## Project Overview

**Bjoern** is a **B**ehavior-driven **J**ava-based **O**ne-hundred and ten percent **E**fficient **R**eadable **N**otation - a universal BDD test generator.

**Main Purpose:** Generation of Java test classes from BDD specification files (`.zgr` format) to ensure synchronization between specification and code.

## Technology Stack

- **Language:** Java (JDK 8+)
- **Build System:** Gradle 8.4+
- **Plugin Type:** Gradle Plugin
- **Test Framework:** JUnit 4 & JUnit 5
- **Publication:** Gradle Plugin Portal & Maven Central

### Key Dependencies
- `com.squareup:javapoet:1.13.0` - Code generation
- `org.freemarker:freemarker:2.3.32` - Template engine
- `com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.16.1` - YAML parsing
- `org.apache.commons:commons-lang3:3.18.0`, `commons-io:commons-io:2.15.1`, `commons-text:1.11.0` - Utilities
- `org.slf4j:slf4j-simple:2.0.12` - Logging
- `org.assertj:assertj-core:3.25.3` - Assertions (Test)

## Project Structure

```
src/main/java/de/mehtrick/bjoern/
├── base/              # Base classes for all generators
│   ├── AbstractBjoernGenerator.java          # Abstract base class
│   ├── BjoernGeneratorConfig.java            # Configuration model
│   └── *Exception.java                       # Custom exceptions
├── generator/         # Code generation engine
│   ├── BjoernCodeGenerator.java              # Main generator
│   └── BjoernCodeGeneratorApplication.java   # CLI application
├── gradle/           # Gradle plugin integration
│   ├── BjoernPlugin.java                    # Plugin entry point
│   ├── BjoernGeneratorTask.java             # Gradle task for code generation
│   ├── BjoernDocGeneratorTask.java          # Gradle task for documentation
│   └── BjoernGeneratorExtension.java        # Gradle extension for configuration
├── parser/           # YAML parser and validation
│   ├── BjoernParser.java                    # Parser interface
│   ├── BjoernTextParser.java                # Text parser implementation
│   ├── modell/       # Data models for specs
│   ├── reader/       # File reader
│   ├── replacer/     # Token replacement
│   └── validator/    # Validation
└── doc/              # Documentation generator
    ├── BjoernDocGenerator.java               # Doc generation engine
    └── BjoernDocGeneratorConfig.java         # Doc configuration

src/main/resources/
└── asciidoc.ftlh    # Freemarker template for AsciiDoc documentation
```

## BDD Specification Format (.zgr Files)

**.zgr** files are YAML-based and use BDD keywords (Given-When-Then):

```yaml
Feature: Description of the feature

Background:
  Given:
    - Common setup steps

Scenarios:
  - Scenario: Scenario 1
    Given:
      - Precondition 1
      - Precondition 2
    When:
      - Action 1
    Then:
      - Assertion 1
      - Assertion 2

  - Scenario: Scenario 2
    Given:
      - Other precondition
    When:
      - Other action
    Then:
      - Other assertion
```

## Code Generation Process

1. **Parser** reads `.zgr` files and converts them to internal data structures
2. **Generator** creates two types of classes:
   - **Abstract test class:** Contains the test structure based on scenarios
   - **Concrete test class:** Inherits from the abstract class for implementation by developers
3. **Freemarker templates** are used for code generation
4. **JavaPoet** is used for clean Java code generation

### Configuration Parameters

Configured in the `bjoern` block of `build.gradle`:

| Parameter | Required | Description | Default |
|-----------|----------|-------------|---------|
| `path` | yes (if `folder` is not set) | Absolute path to the specification | - |
| `folder` | yes (if `path` is not set) | Folder with all specs | - |
| `pckg` | yes | Package of generated classes | - |
| `gendir` | yes | Folder for generated files | - |
| `extendedTestClass` | no | Base test class for generated classes | - |
| `docdir` | no | Folder for generated documentation | - |
| `template` | no | Freemarker template for docs | `/asciidoc.ftlh` |
| `templatefolder` | no | Custom template folder | - |
| `docExtension` | no | File extension of docs | `adoc` |
| `junitVersion` | no | JUnit version (4 or 5) | `5` |
| `encoding` | no | Encoding of spec files | `UTF-8` |
| `specRecursive` | no | Recursive folder scanning | `false` |

## Gradle Tasks

- **`bjoernGen`:** Generates the abstract test classes from `.zgr` files
- **`bjoernDoc`:** Generates documentation from `.zgr` files (Default: AsciiDoc)

The `bjoernGen` task automatically runs before `compileTestJava`.

## Requirements & Characteristics

⚠️ **Important:**
- **Gradle 8.4+** required and fully supported
- Generated classes should **NOT** be manually edited - they will be regenerated on every run
- JavaPoet generates **only UTF-8** files (regardless of encoding setting)

## Best Practices for Code Development

### 1. Parser & Validator
- New parsers must implement `BjoernParser`
- Validation before code generation is critical
- Spec files must be completely validated

### 2. Code Generation
- Use **JavaPoet** for Java code (not string concatenation)
- Use **Freemarker** for template-based generation
- Keep generated classes minimal and simple

### 3. Gradle Integration
- Tasks must inherit from `DefaultTask` or `AbstractBjoernGenerator`
- Extension properties must be validated
- Set task dependencies correctly (e.g., `compileTestJava`)

### 4. Exception Handling
- Use custom exceptions from `base` package
- `BjoernMissingPropertyException` for missing configuration
- `BjoernGeneratorException` for generation errors
- `NotSupportedJunitVersionException` for invalid JUnit versions

### 5. Testing
- Unit tests in `src/test/java/de/mehtrick/bjoern/`
- Test resources in `src/test/resources/`
- Use test files with `.zgr` extension

## Release & Publishing

- **Plugin Portal:** https://plugins.gradle.org/plugin/de.mehtrick.bjoern.gradle-plugin
- **Source Code:** https://github.com/Mehtrick/bjoern
- **Maven Central:** via `uploadArchives` task
- **Versioning:** Defined in `version.config`
- **CI/CD:** Release version via environment variable `RELEASE_VERSION`

## Additional Resources

- **VS Code Extension:** https://marketplace.visualstudio.com/items?itemName=mehtrick.bjoern
- **GitHub Repo:** https://github.com/Mehtrick/bjoern
- **JUnit Integration:** Spring & Mockito-compatible bases possible
- **Templates:** Custom Freemarker templates for custom doc formats

## Copilot-Specific Guidelines

### When reviewing code:
- ✅ Use of JavaPoet for code generation
- ✅ Correct exception handling with custom exceptions
- ✅ Gradle task dependencies
- ✅ Validation before generation
- ✅ Gradle 8 compatibility
- ❌ Direct string concatenation for code generation
- ❌ Generated code in specs (should only contain specifications)

### When implementing new features:
1. Check spec file format (YAML-compatible?)
2. Implement parser logic
3. Add validation
4. Implement code generation
5. Add Gradle integration (if needed)
6. Write tests
7. Update documentation

### File Suffixes:
- `.zgr` - Bjoern specification file
- `.adoc` - AsciiDoc documentation
- `.ftlh` - Freemarker template (HTML)
- `.gradle` - Gradle build file

## Development Tips

- **Debug errors:** Control log level via `slf4j-simple`
- **Template development:** Freemarker syntax in `.ftlh` files
- **Test code generation:** Unit tests with test specs
- **Test plugin:** `gradlew clean build publishToMavenLocal`


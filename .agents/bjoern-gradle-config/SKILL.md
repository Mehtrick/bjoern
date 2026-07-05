---
name: bjoern-gradle-config
description: Use when configuring the Bjoern Gradle plugin in build.gradle files. Covers all configuration parameters, task setup, and common patterns for code and documentation generation.
---

# Bjoern Gradle Plugin Configuration

## Plugin Setup

### Apply the Plugin

```gradle
plugins {
    id "de.mehtrick.bjoern.gradle-plugin" version "1.3.3"
}

apply plugin: 'de.mehtrick.bjoern.gradle-plugin'
```

### Required Dependencies

```gradle
dependencies {
    // For JUnit 5 (default)
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.10.2'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.10.2'
    
    // OR for JUnit 4
    testImplementation 'junit:junit:4.13.2'
    
    // Recommended assertion library
    testImplementation 'org.assertj:assertj-core:3.27.7'
}
```

### Gradle Version Requirement

**Minimum: Gradle > 7.6.2** (due to Jackson dependency bug)
**Recommended: Gradle 8.x**

## Configuration Block

All configuration is done in the `bjoern` block:

```gradle
bjoern {
    // Required parameters
    folder = "${projectDir}/src/test/resources"
    pckg = "com.example.tests"
    gendir = "${projectDir}/src/gen/bjoern"
    
    // Optional parameters
    extendedTestClass = "com.example.AbstractBjoernTest"
    docdir = "${projectDir}/src/gen/bjoern/docs"
    template = "/custom-template.ftlh"
    templateFolder = "${projectDir}/src/main/resources/templates"
    docExtension = "adoc"
    junitVersion = "5"
    encoding = "UTF-8"
    specRecursive = true
    gitHistory = false
}
```

## Parameters Reference

### Required Parameters

| Parameter | Type | Description | Example |
|-----------|------|-------------|---------|
| `path` | String | Absolute path to a single .zgr file | `"${projectDir}/src/test/resources/spec.zgr"` |
| `folder` | String | Absolute path to folder containing .zgr files | `"${projectDir}/src/test/resources"` |
| `pckg` | String | Java package for generated classes | `"com.example.tests"` |
| `gendir` | String | Output directory for generated Java files | `"${projectDir}/src/gen/bjoern"` |

**Note:** Either `path` or `folder` is required. If both are set, `folder` takes precedence.

### Optional Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `extendedTestClass` | String | - | Fully qualified class name that generated tests will extend |
| `docdir` | String | - | Output directory for generated documentation |
| `template` | String | `"/asciidoc.ftlh"` | Freemarker template path for doc generation |
| `templateFolder` | String | - | Custom template directory |
| `docExtension` | String | `"adoc"` | File extension for generated docs |
| `junitVersion` | String | `"5"` | JUnit version: `"4"` or `"5"` |
| `encoding` | String | `"UTF-8"` | Encoding for reading .zgr files (generated Java is always UTF-8) |
| `specRecursive` | Boolean | `false` | Scan folder recursively for .zgr files |
| `gitHistory` | Boolean | `false` | Include git history in generated documentation |

## Gradle Tasks

### Available Tasks

| Task | Description |
|------|-------------|
| `bjoernGen` | Generates abstract test classes from .zgr specs |
| `bjoernDoc` | Generates documentation from .zgr specs |

### Task Execution

```bash
# Generate test classes only
./gradlew bjoernGen

# Generate documentation only
./gradlew bjoernDoc

# Generate both
./gradlew bjoernGen bjoernDoc

# Full build (includes bjoernGen automatically)
./gradlew build
```

### Task Dependencies

**Important:** `bjoernGen` automatically runs before `compileTestJava`. This ensures generated classes are always up-to-date.

To explicitly set the dependency (usually not needed):

```gradle
compileTestJava.dependsOn bjoernGen
```

## Common Configuration Patterns

### Pattern 1: Basic Setup

```gradle
bjoern {
    folder = "${projectDir}/src/test/resources"
    pckg = "com.example.bdd"
    gendir = "${projectDir}/src/gen/bjoern"
}
```

### Pattern 2: With Base Test Class

```gradle
bjoern {
    folder = "${projectDir}/src/test/resources"
    pckg = "com.example.bdd"
    gendir = "${projectDir}/src/gen/bjoern"
    extendedTestClass = "com.example.AbstractBjoernTest"
}
```

The base class can provide common setup/teardown:

```java
public abstract class AbstractBjoernTest {
    @BeforeEach
    public void setup() {
        // Common setup for all generated tests
    }
    
    @AfterEach
    public void teardown() {
        // Common cleanup
    }
}
```

### Pattern 3: JUnit 4 Configuration

```gradle
bjoern {
    folder = "${projectDir}/src/test/resources"
    pckg = "com.example.bdd"
    gendir = "${projectDir}/src/gen/bjoern"
    junitVersion = "4"
}

dependencies {
    testImplementation 'junit:junit:4.13.2'
}
```

### Pattern 4: Recursive Folder Scanning

```gradle
bjoern {
    folder = "${projectDir}/src/test/resources/bdd"
    pckg = "com.example.bdd"
    gendir = "${projectDir}/src/gen/bjoern"
    specRecursive = true  // Scans subfolders
}
```

### Pattern 5: Documentation with Git History

```gradle
bjoern {
    folder = "${projectDir}/src/test/resources"
    pckg = "com.example.bdd"
    gendir = "${projectDir}/src/gen/bjoern"
    
    // Documentation configuration
    docdir = "${projectDir}/build/docs/bdd"
    docExtension = "adoc"
    gitHistory = true  // Shows commit history per spec
}
```

### Pattern 6: Custom Templates

```gradle
bjoern {
    folder = "${projectDir}/src/test/resources"
    pckg = "com.example.bdd"
    gendir = "${projectDir}/src/gen/bjoern"
    
    // Custom template configuration
    template = "/custom-asciidoc.ftlh"
    templateFolder = "${projectDir}/src/main/resources/templates"
}
```

### Pattern 7: Complete Configuration

```gradle
bjoern {
    // Source configuration
    folder = "${projectDir}/src/test/resources/bdd"
    specRecursive = true
    encoding = "UTF-8"
    
    // Code generation
    pckg = "com.example.bdd.tests"
    gendir = "${projectDir}/src/gen/bjoern"
    extendedTestClass = "com.example.BaseBddTest"
    junitVersion = "5"
    
    // Documentation
    docdir = "${projectDir}/build/docs/bdd"
    template = "/custom-template.ftlh"
    templateFolder = "${projectDir}/src/main/resources/bdd-templates"
    docExtension = "adoc"
    gitHistory = true
}
```

## Source Sets Configuration

Add generated sources to test classpath:

```gradle
sourceSets {
    test {
        java {
            srcDirs 'src/test/java'
            srcDirs 'src/gen/bjoern'  // Add generated sources
        }
    }
}
```

## Common Issues and Solutions

### Issue 1: Generated Classes Not Found

**Problem:** Compilation fails because generated classes are not on classpath.

**Solution:**
```gradle
sourceSets {
    test {
        java {
            srcDirs 'src/gen/bjoern'
        }
    }
}
```

### Issue 2: Jackson Dependency Conflict

**Problem:** Gradle 7.6.2 or earlier has Jackson dependency issues.

**Solution:** Upgrade to Gradle 8.x or use Gradle > 7.6.2.

### Issue 3: Wrong JUnit Annotations

**Problem:** Generated tests use JUnit 5 annotations but project uses JUnit 4.

**Solution:** Set `junitVersion = "4"` in bjoern configuration.

### Issue 4: Encoding Issues with Special Characters

**Problem:** .zgr files with special characters are not read correctly.

**Solution:** Set `encoding` parameter to match your .zgr file encoding:
```gradle
bjoern {
    encoding = "UTF-8"  // or "ISO-8859-1", "UTF-16", etc.
}
```

**Note:** Generated Java files are always UTF-8 regardless of this setting.

### Issue 5: Package Name Mismatch

**Problem:** Generated classes have wrong package declaration.

**Solution:** Ensure `pckg` parameter matches your project structure:
```gradle
bjoern {
    pckg = "com.example.bdd"  // Must match directory structure
}
```

### Issue 6: bjoernGen Not Running Automatically

**Problem:** Generated classes are outdated.

**Solution:** The plugin automatically sets `compileTestJava.dependsOn bjoernGen`. If this doesn't work, add it explicitly:
```gradle
compileTestJava.dependsOn bjoernGen
```

## Best Practices

### 1. Use Separate Directories

```gradle
bjoern {
    folder = "${projectDir}/src/test/resources/bdd"  // Specs
    gendir = "${projectDir}/src/gen/bjoern"           // Generated code
    docdir = "${projectDir}/build/docs/bdd"           // Generated docs
}
```

### 2. Add Generated Directories to .gitignore

```gitignore
# Bjoern generated files
src/gen/bjoern/
build/docs/bdd/
```

### 3. Use Base Class for Common Setup

```gradle
bjoern {
    extendedTestClass = "com.example.AbstractBddTest"
}
```

This allows you to:
- Provide common test infrastructure
- Set up Spring context
- Configure test data
- Implement shared Given/When/Then methods

### 4. Enable Recursive Scanning for Large Projects

```gradle
bjoern {
    specRecursive = true
}
```

Organize specs by feature:
```
src/test/resources/bdd/
├── user-management/
│   ├── login.zgr
│   └── registration.zgr
├── shopping-cart/
│   ├── add-item.zgr
│   └── checkout.zgr
```

### 5. Use Git History for Documentation

```gradle
bjoern {
    gitHistory = true
}
```

This adds commit information to generated docs, showing:
- Last modified date
- Author
- Commit message

### 6. Version Control Strategy

**Commit:**
- `.zgr` specification files
- `build.gradle` configuration
- Base test classes (if using `extendedTestClass`)

**Don't commit:**
- Generated Java files in `gendir`
- Generated documentation in `docdir`

## Complete Example

```gradle
plugins {
    id 'java'
    id "de.mehtrick.bjoern.gradle-plugin" version "1.3.3"
}

apply plugin: 'de.mehtrick.bjoern.gradle-plugin'

repositories {
    mavenCentral()
}

dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.10.2'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.10.2'
    testImplementation 'org.assertj:assertj-core:3.27.7'
}

bjoern {
    folder = "${projectDir}/src/test/resources/bdd"
    pckg = "com.example.bdd"
    gendir = "${projectDir}/src/gen/bjoern"
    extendedTestClass = "com.example.AbstractBddTest"
    junitVersion = "5"
    
    docdir = "${projectDir}/build/docs/bdd"
    specRecursive = true
    gitHistory = true
}

sourceSets {
    test {
        java {
            srcDirs 'src/test/java'
            srcDirs 'src/gen/bjoern'
        }
    }
}

compileTestJava.dependsOn bjoernGen
```

## Validation Checklist

When configuring the plugin, verify:

- [ ] Gradle version > 7.6.2 (preferably 8.x)
- [ ] `folder` or `path` is set correctly
- [ ] `pckg` matches your project package structure
- [ ] `gendir` is added to `sourceSets.test.java.srcDirs`
- [ ] `junitVersion` matches your test dependencies
- [ ] JUnit dependencies are added to `dependencies` block
- [ ] Generated directories are in `.gitignore`
- [ ] `bjoernGen` runs before `compileTestJava` (automatic or explicit)

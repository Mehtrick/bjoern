# AGENTS.md

## Build & Test

```bash
./gradlew build          # compile + test (CI matrix: Java 8, 11, 17, 21)
./gradlew test           # tests only (JUnit 5 platform)
./gradlew bjoerngen      # generate abstract test classes from .zgr specs
./gradlew bjoerndoc      # generate AsciiDoc documentation from .zgr specs
```

No separate lint or typecheck step — `build` covers compilation + tests.

## Project Structure

Single-module Gradle plugin project (`settings.gradle` root = `bjoern-gradle-plugin`).

Source packages under `src/main/java/de/mehtrick/bjoern/`:
- `parser/` — reads `.zgr` (YAML) spec files via Jackson
- `generator/` — produces Java test classes via JavaPoet
- `doc/` — produces AsciiDoc via Freemarker templates (`src/main/resources/*.ftlh`)
- `gradle/` — plugin entry point (`BjoernPlugin`), tasks, extension
- `base/` — shared config model and custom exceptions

`bjoern-sample/` is a standalone consumer project with its own Gradle wrapper.

## Key Conventions

- **Generated code is ephemeral.** Files in `src/gen/` are gitignored and regenerated on every `bjoerngen` run. Never edit them.
- Spec files use the `.zgr` extension (YAML-based BDD: Feature/Background/Scenarios with Given/When/Then).
- Use **JavaPoet** for Java code generation — never string concatenation.
- Use **Freemarker** (`.ftlh`) for template-based doc generation.
- Custom exceptions live in `base/`: `BjoernMissingPropertyException`, `BjoernGeneratorException`, `NotSupportedJunitVersionException`.
- Plugin version comes from `RELEASE_VERSION` env var (set by CI on release tags); library version in `version.config`.
- Gradle wrapper is 8.4. README notes Gradle >7.6.2 required due to Jackson dependency bug.

## Testing

- Tests use JUnit 5 (`useJUnitPlatform()`) with AssertJ assertions.
- Test specs (`.zgr` files) live in `src/test/resources/`.
- Generated test output goes to `src/gen/` (gitignored).

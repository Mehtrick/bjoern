package de.mehtrick.bjoern.generator.junitsupport;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import de.mehtrick.bjoern.generator.builder.BjoernStatementParser;
import de.mehtrick.bjoern.parser.modell.Bjoern;
import de.mehtrick.bjoern.parser.modell.BjoernScenario;
import de.mehtrick.bjoern.parser.modell.BjoernStatement;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Junit5GenerationStrategy implements JunitGenerationStrategy {

    private int ordernumber = 1;

    @Override
    public void generateScenarios(Bjoern bjoern, TypeSpec.Builder featureClassBuilder) {
        List<TypeSpec> scenarios = build(bjoern);
        featureClassBuilder.addAnnotation(AnnotationSpec.builder(DisplayName.class)
                .addMember("value", "$S", String.format("Feature: %s", bjoern.getFeature())).build());
        featureClassBuilder.addTypes(scenarios);
    }

    private List<TypeSpec> build(Bjoern bjoern) {
        return bjoern.getScenarios().stream().map(this::parseBjoernScenario)
                .collect(Collectors.toList());
    }

    private TypeSpec parseBjoernScenario(BjoernScenario scenario) {
        return TypeSpec.classBuilder(scenario.getNameFormatted())
                .addAnnotation(Nested.class)
                .addAnnotation(AnnotationSpec.builder(DisplayName.class)
                        .addMember("value", "\"Scenario: " + scenario.getName() + "\"").build())
                .addAnnotation(AnnotationSpec.builder(TestMethodOrder.class)
                        .addMember("value", "$T.class",
                                MethodOrderer.OrderAnnotation.class)
                        .build())
                .addMethods(parseKeywordsInScenario(scenario))
                .build();
    }

    private List<MethodSpec> parseKeywordsInScenario(BjoernScenario scenario) {
        List<MethodSpec> keywordsInScenario = new ArrayList<>();
        if (scenario.getGiven() != null) {
            keywordsInScenario.addAll(parseStatementsInKeywords(keywordsInScenario, "Given", scenario.getGiven()));
        }
        if (scenario.getWhen() != null) {
            keywordsInScenario.addAll(parseStatementsInKeywords(keywordsInScenario, "When", scenario.getWhen()));
        }
        if (scenario.getThen() != null) {
            keywordsInScenario.addAll(parseStatementsInKeywords(keywordsInScenario, "Then", scenario.getThen()));
        }
        return keywordsInScenario;

    }

    private List<MethodSpec> parseStatementsInKeywords(List<MethodSpec> keywordsInScenario, String keyword, List<BjoernStatement> statements) {
        return statements.stream().map(s -> parseSingleStatement(s, keyword)).collect(Collectors.toList());
    }

    private Iterable<MethodSpec> parseStatementsInKeyword(List<BjoernStatement> statements, String keyword) {
        return statements.stream().map(s -> parseSingleStatement(s, keyword)).collect(Collectors.toList());
    }

    private MethodSpec parseSingleStatement(BjoernStatement bjoernStatement, String keyword) {
        MethodSpec.Builder main = MethodSpec.methodBuilder(StringUtils.uncapitalize(bjoernStatement.getStatementWithoutParameters() + "Test"))
                .addException(Exception.class)
                .addAnnotation(org.junit.jupiter.api.Test.class)
                .addAnnotation(AnnotationSpec.builder(DisplayName.class)
                        .addMember("value", "$S", String.format("%s: %s", keyword, bjoernStatement.getPrimitiveStatement().replaceAll("\"", "")))
                        .build())
                .addAnnotation(AnnotationSpec.builder(Order.class)
                        .addMember("value", String.valueOf(ordernumber++))
                        .build())
                .addStatement(BjoernStatementParser.createMethodCallOutOfStatemet(bjoernStatement));
        return main.build();
    }
}

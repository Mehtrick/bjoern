package de.mehtrick.bjoern.parser.validator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum BjoernKeywords {

    GIVEN("Given:"), WHEN("When:"), THEN("Then:"), BACKGROUND("Background:"), FEATURE("Feature:"), SCENARIO("- Scenario:"), SCENARIOS("Scenarios:"), STATEMENT("-");

    protected String keyword;

    BjoernKeywords(String keyword) {
        this.keyword = keyword;
    }

    public static List<String> getKeywordValues() {
        return Arrays.stream(BjoernKeywords.values()).map(k -> k.keyword).collect(Collectors.toList());
    }

    public static String getKeywordsAsSingleString() {
        return BjoernKeywords.getKeywordValues().stream().collect(Collectors.joining(","));
    }
}

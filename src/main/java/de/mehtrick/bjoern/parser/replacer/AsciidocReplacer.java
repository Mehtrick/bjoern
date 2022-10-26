package de.mehtrick.bjoern.parser.replacer;

import java.util.HashMap;
import java.util.Map;

public class AsciidocReplacer {

    static final Map<String, String> asciidocReplacements;
    static {
        asciidocReplacements = new HashMap<>();
        asciidocReplacements.put("|", "\\|");
    }

    public static String replace(String in){
        return StringReplacer.replaceWithValues(in, asciidocReplacements);
    }
}

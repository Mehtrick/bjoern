package de.mehtrick.bjoern.parser.modell;

import de.mehtrick.bjoern.parser.BjoernTextParser;

import java.util.ArrayList;
import java.util.List;

/**
 * A Scenario contains of - a name which describes it - and a list of BjoernStatements
 *
 * @author mehtrick
 * @see de.mehtrick.bjoern.parser.modell.BjoernStatement
 */
public class BjoernScenario extends BjoernBackground {
    private String name;
    private List<BjoernStatement> when = new ArrayList<>();

    public BjoernScenario(BjoernZGRScenario bjoernZGRScenario) {
        setName(bjoernZGRScenario.getScenario());
        this.setGiven(parseStatements(bjoernZGRScenario.getGiven(), BDDKeyword.GIVEN));
        this.setWhen(parseStatements(bjoernZGRScenario.getWhen(), BDDKeyword.WHEN));
        this.setThen(parseStatements(bjoernZGRScenario.getThen(), BDDKeyword.THEN));
    }

    public String getNameFormatted() {
        return BjoernTextParser.parseText(getName());
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BjoernStatement> getWhen() {
        return this.when;
    }

    public void setWhen(List<BjoernStatement> when) {
        this.when = when;
    }

    public String toString() {
        return "BjoernScenario(name=" + this.getName() + ", when=" + this.getWhen() + ")";
    }

}

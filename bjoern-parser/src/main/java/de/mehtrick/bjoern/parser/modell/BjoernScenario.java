package de.mehtrick.bjoern.parser.modell;

import java.util.ArrayList;
import java.util.List;

import de.mehtrick.bjoern.parser.BjoernTextParser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A Scenario contains of - a name which describes it - {@link #given} -
 * {@link #when} - {@link #then}
 * 
 * @author mehtrick
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
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

}

package de.mehtrick.jvior.parser.modell;

import java.util.ArrayList;
import java.util.List;

import de.mehtrick.jvior.parser.JviorTextParser;
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
public class JviorScenario extends JviorBackground {
	private String name;
	private List<JviorStatement> when = new ArrayList<>();

	public JviorScenario(JviorYMLScenario jviorYMLScenario) {
		setName(jviorYMLScenario.getScenario());
		this.setGiven(parseStatements(jviorYMLScenario.getGiven(), BDDKeyword.GIVEN));
		this.setWhen(parseStatements(jviorYMLScenario.getWhen(), BDDKeyword.WHEN));
		this.setThen(parseStatements(jviorYMLScenario.getThen(), BDDKeyword.THEN));
	}

	public String getNameFormatted() {
		return JviorTextParser.parseText(getName());
	}

}

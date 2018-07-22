package de.mehtrick.jvior.parser.modell;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.mehtrick.jvior.parser.JviorUtil;
import lombok.Data;

/**
 * A Scenario contains of - a name which describes it - {@link #given} -
 * {@link #when} - {@link #then}
 * 
 * @author mehtrick
 *
 */
@Data
public class JviorScenario {
	private String name;
	private List<JviorStatement> given = new ArrayList<>();
	private List<JviorStatement> when = new ArrayList<>();
	private List<JviorStatement> then = new ArrayList<>();

	public JviorScenario(JviorYMLScenario jviorYMLScenario) {
		setName(jviorYMLScenario.getScenario());
		given = parseStatements(jviorYMLScenario.getGiven(), "given");
		when = parseStatements(jviorYMLScenario.getWhen(), "when");
		then = parseStatements(jviorYMLScenario.getThen(), "then");
	}

	private List<JviorStatement> parseStatements(List<String> yamlStatementList, String keyword) {
		return yamlStatementList.stream().map(s -> new JviorStatement(s, keyword)).collect(Collectors.toList());
	}

	public String getNameFormatted() {
		return JviorUtil.parseTextToCamelCase(getName());
	}

}

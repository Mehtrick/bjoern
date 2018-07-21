package de.mehtrick.jvior.parser.modell;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.mehtrick.jvior.parser.modell.yaml.Scenario;
import lombok.Data;

@Data
public class JviorScenario {
	private String name;
	private List<JviorStatement> given = new ArrayList<>();
	private List<JviorStatement> when = new ArrayList<>();
	private List<JviorStatement> then = new ArrayList<>();

	public JviorScenario(Scenario scenario) {
		setName(scenario.getScenario());
		given = parseStatements(scenario.getGiven());
		when = parseStatements(scenario.getWhen());
		then = parseStatements(scenario.getThen());
	}

	private List<JviorStatement> parseStatements(List<String> yamlStatementList) {
		return yamlStatementList.stream().map(JviorStatement::new).collect(Collectors.toList());

	}

}

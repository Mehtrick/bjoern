package de.mehtrick.jvior.parser.modell;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class JviorScenario {
	private String name;
	private List<JviorStatement> given = new ArrayList<>();
	private List<JviorStatement> when = new ArrayList<>();
	private List<JviorStatement> then = new ArrayList<>();

	public JviorScenario(JviorYMLScenario jviorYMLScenario) {
		setName(jviorYMLScenario.getScenario());
		given = parseStatements(jviorYMLScenario.getGiven());
		when = parseStatements(jviorYMLScenario.getWhen());
		then = parseStatements(jviorYMLScenario.getThen());
	}

	private List<JviorStatement> parseStatements(List<String> yamlStatementList) {
		return yamlStatementList.stream().map(JviorStatement::new).collect(Collectors.toList());

	}

}

package de.mehtrick.jvior.parser.modell;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JviorBackground {

	private List<JviorStatement> given = new ArrayList<>();
	private List<JviorStatement> then = new ArrayList<>();

	public JviorBackground(JviorYMLBackground ymlBackground) {
		given = parseStatements(ymlBackground.getGiven(), BDDKeyword.GIVEN);
		// then = parseStatements(jviorYMLScenario.getThen(), BDDKeyword.THEN);
	}

	protected List<JviorStatement> parseStatements(List<String> yamlStatementList, BDDKeyword keyword) {
		if (yamlStatementList != null) {
			return yamlStatementList.stream().map(s -> new JviorStatement(s, keyword)).collect(Collectors.toList());
		} else {
			return null;
		}
	}

}

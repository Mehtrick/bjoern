package de.mehtrick.bjoern.parser.modell;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BjoernBackground {

	private List<BjoernStatement> given = new ArrayList<>();
	private List<BjoernStatement> then = new ArrayList<>();

	public BjoernBackground(BjoernZGRBackground zgrBackground) {
		given = parseStatements(zgrBackground.getGiven(), BDDKeyword.GIVEN);
		// then = parseStatements(bjoernZGRScenario.getThen(), BDDKeyword.THEN);
	}

	protected List<BjoernStatement> parseStatements(List<String> zgrStatementList, BDDKeyword keyword) {
		if (zgrStatementList != null) {
			return zgrStatementList.stream().map(s -> new BjoernStatement(s, keyword)).collect(Collectors.toList());
		} else {
			return null;
		}
	}

}

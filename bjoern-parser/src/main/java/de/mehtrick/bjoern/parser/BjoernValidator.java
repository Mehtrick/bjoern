package de.mehtrick.bjoern.parser;

import java.util.ArrayList;
import java.util.List;

public class BjoernValidator {

	public void validate(String bjoernSpec) {
		validateNoEmptyStatements(bjoernSpec);
	}

	private void validateNoEmptyStatements(String bjoernSpec) {
		String[] lines = bjoernSpec.split("\\r?\\n");
		List<Integer> emptyStatementLines = new ArrayList<Integer>();
		for (int i = 1; i <= lines.length; i++) {
			validateLine(lines, i, emptyStatementLines);
		}
		if (!emptyStatementLines.isEmpty()) {
			throw new EmptyStatementException(emptyStatementLines);
		}
	}

	private void validateLine(String[] lines, int i, List<Integer> emptyStatementLines) {
		String currentLines = lines[i - 1].trim();
		if (statementIsEmpty(currentLines)) {
			emptyStatementLines.add(i);
		}

	}

	private boolean statementIsEmpty(String currentLines) {
		return currentLines.startsWith("-") && currentLines.length() == 1;
	}

}

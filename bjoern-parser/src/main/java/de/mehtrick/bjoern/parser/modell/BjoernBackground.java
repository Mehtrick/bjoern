package de.mehtrick.bjoern.parser.modell;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BjoernBackground {

	private List<BjoernStatement> given = new ArrayList<>();
	private List<BjoernStatement> then = new ArrayList<>();

	public BjoernBackground(BjoernZGRBackground zgrBackground) {
		given = parseStatements(zgrBackground.getGiven(), BDDKeyword.GIVEN);
		// then = parseStatements(bjoernZGRScenario.getThen(), BDDKeyword.THEN);
	}

	public BjoernBackground() {
	}

	protected List<BjoernStatement> parseStatements(List<String> zgrStatementList, BDDKeyword keyword) {
		if (zgrStatementList != null) {
			return zgrStatementList.stream().map(s -> new BjoernStatement(s, keyword)).collect(Collectors.toList());
		} else {
			return null;
		}
	}

	public List<BjoernStatement> getGiven() {
		return this.given;
	}

	public void setGiven(List<BjoernStatement> given) {
		this.given = given;
	}

	public List<BjoernStatement> getThen() {
		return this.then;
	}

	public void setThen(List<BjoernStatement> then) {
		this.then = then;
	}

	public String toString() {
		return "BjoernBackground(given=" + this.getGiven() + ", then=" + this.getThen() + ")";
	}
}

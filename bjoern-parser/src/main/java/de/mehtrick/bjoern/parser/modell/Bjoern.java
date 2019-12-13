package de.mehtrick.bjoern.parser.modell;

import de.mehtrick.bjoern.parser.BjoernTextParser;

import java.util.List;
import java.util.stream.Collectors;

public class Bjoern {
	private String feature;
	private BjoernBackground background;
	private List<BjoernScenario> scenarios;
	private String filePath;

	public Bjoern(BjoernZGRModell yamlModell, String path) {
		setFeature(yamlModell.getFeature());
		setScenarios(yamlModell.getScenarios().stream().map(BjoernScenario::new).collect(Collectors.toList()));
		setFilePath(path);
		if (yamlModell.getBackground() != null) {
			setBackground(new BjoernBackground(yamlModell.getBackground()));
		}
	}

	public String getFeatureNameFormatted() {
		return BjoernTextParser.parseText(getFeature());
	}

	public String getFeature() {
		return this.feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public BjoernBackground getBackground() {
		return this.background;
	}

	public void setBackground(BjoernBackground background) {
		this.background = background;
	}

	public List<BjoernScenario> getScenarios() {
		return this.scenarios;
	}

	public void setScenarios(List<BjoernScenario> scenarios) {
		this.scenarios = scenarios;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String toString() {
		return "Bjoern(feature=" + this.getFeature() + ", background=" + this.getBackground() + ", scenarios=" + this.getScenarios() + ", filePath=" + this.getFilePath() + ")";
	}
}

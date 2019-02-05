package de.mehtrick.bjoern.parser.modell;

import java.util.List;
import java.util.stream.Collectors;

import de.mehtrick.bjoern.parser.BjoernTextParser;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
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

}

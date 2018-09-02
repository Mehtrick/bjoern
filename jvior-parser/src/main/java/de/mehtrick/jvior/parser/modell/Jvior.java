package de.mehtrick.jvior.parser.modell;

import java.util.List;
import java.util.stream.Collectors;

import de.mehtrick.jvior.parser.JviorTextParser;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Jvior {
	private String feature;
	private JviorBackground background;
	private List<JviorScenario> scenarios;
	private String filePath;

	public Jvior(JviorYMLModell yamlModell, String path) {
		setFeature(yamlModell.getFeature());
		setScenarios(yamlModell.getScenarios().stream().map(JviorScenario::new).collect(Collectors.toList()));
		setFilePath(path);
		if (yamlModell.getBackground() != null) {
			setBackground(new JviorBackground(yamlModell.getBackground()));
		}
	}

	public String getFeatureNameFormatted() {
		return JviorTextParser.parseText(getFeature());
	}

}

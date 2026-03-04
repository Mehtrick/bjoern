package de.mehtrick.bjoern.parser.modell;

import de.mehtrick.bjoern.parser.BjoernTextParser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Bjoern {
	private static final Pattern MARKDOWN_LINK_PATTERN = Pattern.compile("\\[([^\\]]+)\\]\\(([^)]+)\\)");

	private String feature;
	private String version;
	private String reference;
	private BjoernBackground background;
	private List<BjoernScenario> scenarios;
	private String filePath;

	public Bjoern(BjoernZGRModell yamlModell, String path) {
		setFeature(yamlModell.getFeature());
		setVersion(yamlModell.getVersion());
		setReference(yamlModell.getReference());
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

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * Returns the reference formatted as a Javadoc hyperlink if it is a Markdown link ([text](url)),
	 * otherwise returns the plain reference text.
	 */
	public String getReferenceAsJavadoc() {
		if (reference == null) {
			return null;
		}
		Matcher matcher = MARKDOWN_LINK_PATTERN.matcher(reference);
		if (matcher.matches()) {
			return "<a href=\"" + matcher.group(2) + "\">" + matcher.group(1) + "</a>";
		}
		return reference;
	}

	/**
	 * Returns the reference formatted as an AsciiDoc hyperlink if it is a Markdown link ([text](url)),
	 * otherwise returns the plain reference text.
	 */
	public String getReferenceAsAsciidoc() {
		if (reference == null) {
			return null;
		}
		Matcher matcher = MARKDOWN_LINK_PATTERN.matcher(reference);
		if (matcher.matches()) {
			return "link:" + matcher.group(2) + "[" + matcher.group(1) + "]";
		}
		return reference;
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
		return "Bjoern(feature=" + this.getFeature() + ", version=" + this.getVersion() + ", reference=" + this.getReference() + ", background=" + this.getBackground() + ", scenarios=" + this.getScenarios() + ", filePath=" + this.getFilePath() + ")";
	}
}

package de.mehtrick.bjoern.parser.modell;

import de.mehtrick.bjoern.parser.BjoernTextParser;
import de.mehtrick.bjoern.parser.replacer.AsciidocReplacer;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Bjoern {
	private static final Pattern MARKDOWN_LINK_PATTERN = Pattern.compile("\\[([^\\]]+)\\]\\(([^)]+)\\)");

	private String feature;
	private String version;
	private String reference;
	private String changelog;
	private BjoernBackground background;
	private List<BjoernScenario> scenarios;
	private String filePath;

	public Bjoern(BjoernZGRModell yamlModell, String path) {
		setFeature(yamlModell.getFeature());
		setVersion(yamlModell.getVersion());
		setReference(yamlModell.getReference());
		setChangelog(yamlModell.getChangelog());
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

	public String getChangelog() {
		return this.changelog;
	}

	public void setChangelog(String changelog) {
		this.changelog = changelog;
	}

	/**
	 * Returns the changelog with AsciiDoc-specific characters escaped (e.g. pipe characters).
	 */
	public String getChangelogAsAsciidoc() {
		if (changelog == null) {
			return null;
		}
		return AsciidocReplacer.replace(changelog);
	}

	/**
	 * Returns the reference formatted as a Javadoc hyperlink if it is a Markdown link ([text](url))
	 * with an allowed URL scheme (http/https), otherwise returns the plain reference text.
	 * Link text and URL are HTML-escaped to prevent markup injection.
	 */
	public String getReferenceAsJavadoc() {
		if (reference == null) {
			return null;
		}
		Matcher matcher = MARKDOWN_LINK_PATTERN.matcher(reference);
		if (matcher.matches()) {
			String text = matcher.group(1);
			String url = matcher.group(2);
			if (!isAllowedUrlScheme(url)) {
				return reference;
			}
			return "<a href=\"" + StringEscapeUtils.escapeHtml4(url) + "\">" + StringEscapeUtils.escapeHtml4(text) + "</a>";
		}
		return reference;
	}

	/**
	 * Returns the reference formatted as an AsciiDoc hyperlink if it is a Markdown link ([text](url))
	 * with an allowed URL scheme (http/https), otherwise returns the plain reference text.
	 * Link text is escaped to prevent AsciiDoc macro injection. Open brackets in the URL are
	 * percent-encoded since '[' is the AsciiDoc macro attribute delimiter.
	 */
	public String getReferenceAsAsciidoc() {
		if (reference == null) {
			return null;
		}
		Matcher matcher = MARKDOWN_LINK_PATTERN.matcher(reference);
		if (matcher.matches()) {
			String text = matcher.group(1);
			String url = matcher.group(2);
			if (!isAllowedUrlScheme(url)) {
				return reference;
			}
			String safeUrl = url.replace("[", "%5B").replace("]", "%5D");
			return "link:" + safeUrl + "[" + text.replace("]", "\\]") + "]";
		}
		return reference;
	}

	private static boolean isAllowedUrlScheme(String url) {
		if (url == null || url.isEmpty()) {
			return false;
		}
		String lower = url.toLowerCase(Locale.ROOT);
		return lower.startsWith("http://") || lower.startsWith("https://");
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
		return "Bjoern(feature=" + this.getFeature() + ", version=" + this.getVersion() + ", reference=" + this.getReference() + ", changelog=" + this.getChangelog() + ", background=" + this.getBackground() + ", scenarios=" + this.getScenarios() + ", filePath=" + this.getFilePath() + ")";
	}
}

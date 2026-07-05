
package de.mehtrick.bjoern.parser.modell;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

/**
 * Representation of the bjoern spec file scenario as a pojo
 *
 * @author mehtrick
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Scenario", "Deprecated", "Given", "When", "Then" })
class BjoernZGRScenario implements Serializable {

	@JsonProperty("Scenario")
	private String scenario;
	@JsonProperty("Deprecated")
	private boolean deprecated;
	@JsonProperty("Given")
	private List<String> given = null;
	@JsonProperty("When")
	private List<String> when = null;
	@JsonProperty("Then")
	private List<String> then = null;
	private final static long serialVersionUID = -2873042134445817561L;

	@JsonProperty("Scenario")
	public String getScenario() {
		return scenario;
	}

	@JsonProperty("Scenario")
	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	@JsonProperty("Deprecated")
	public boolean isDeprecated() {
		return deprecated;
	}

	@JsonProperty("Deprecated")
	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
	}

	@JsonProperty("Given")
	public List<String> getGiven() {
		return given;
	}

	@JsonProperty("Given")
	public void setGiven(List<String> given) {
		this.given = given;
	}

	@JsonProperty("When")
	public List<String> getWhen() {
		return when;
	}

	@JsonProperty("When")
	public void setWhen(List<String> when) {
		this.when = when;
	}

	@JsonProperty("Then")
	public List<String> getThen() {
		return then;
	}

	@JsonProperty("Then")
	public void setThen(List<String> then) {
		this.then = then;
	}

	public String toString() {
		return "BjoernZGRScenario(scenario=" + this.getScenario() + ", deprecated=" + this.isDeprecated() + ", given=" + this.getGiven() + ", when=" + this.getWhen() + ", then=" + this.getThen() + ")";
	}
}

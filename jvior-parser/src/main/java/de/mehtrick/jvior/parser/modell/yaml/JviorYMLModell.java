
package de.mehtrick.jvior.parser.modell.yaml;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.ToString;

@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Feature", "Scenarios" })
public class JviorYMLModell implements Serializable {

	@JsonProperty("Feature")
	private String feature;
	@JsonProperty("Scenarios")
	private List<Scenario> scenarios = null;
	private final static long serialVersionUID = -5118234148570119505L;

	@JsonProperty("Feature")
	public String getFeature() {
		return feature;
	}

	@JsonProperty("Feature")
	public void setFeature(String feature) {
		this.feature = feature;
	}

	@JsonProperty("Scenarios")
	public List<Scenario> getScenarios() {
		return scenarios;
	}

	@JsonProperty("Scenarios")
	public void setScenarios(List<Scenario> scenarios) {
		this.scenarios = scenarios;
	}

}

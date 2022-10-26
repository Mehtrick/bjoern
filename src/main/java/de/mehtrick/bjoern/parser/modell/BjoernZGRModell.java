
package de.mehtrick.bjoern.parser.modell;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

/**
 * Representation of the bjoern spec file as a pojo
 *
 * @author mehtrick
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Feature", "Scenarios" })
public class BjoernZGRModell implements Serializable {

	@JsonProperty("Feature")
	private String feature;

	@JsonProperty("Background")
	private BjoernZGRBackground background;

	@JsonProperty("Scenarios")
	private List<BjoernZGRScenario> bjoernZGRScenarios = null;
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
	public List<BjoernZGRScenario> getScenarios() {
		return bjoernZGRScenarios;
	}

	@JsonProperty("Scenarios")
	public void setScenarios(List<BjoernZGRScenario> bjoernZGRScenarios) {
		this.bjoernZGRScenarios = bjoernZGRScenarios;
	}

	@JsonProperty("Background")
	public BjoernZGRBackground getBackground() {
		return background;
	}

	@JsonProperty("Background")
	public void setBackground(BjoernZGRBackground background) {
		this.background = background;
	}

	public String toString() {
		return "BjoernZGRModell(feature=" + this.getFeature() + ", background=" + this.getBackground() + ", bjoernZGRScenarios=" + this.bjoernZGRScenarios + ")";
	}
}

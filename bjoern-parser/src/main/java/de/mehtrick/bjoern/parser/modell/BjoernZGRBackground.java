package de.mehtrick.bjoern.parser.modell;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"Given"})
public class BjoernZGRBackground {

	@JsonProperty("Given")
	private List<String> given = null;

	public BjoernZGRBackground() {
	}

	@JsonProperty("Given")
	public List<String> getGiven() {
		return given;
	}

	@JsonProperty("Given")
	public void setGiven(List<String> given) {
		this.given = given;
	}


	public String toString() {
		return "BjoernZGRBackground(given=" + this.getGiven() + ")";
	}
}

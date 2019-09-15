package de.mehtrick.bjoern.parser.modell;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Given" })
public class BjoernZGRBackground {

	@JsonProperty("Given")
	private List<String> given = null;

	@JsonProperty("Given")
	public List<String> getGiven() {
		return given;
	}

	@JsonProperty("Given")
	public void setGiven(List<String> given) {
		this.given = given;
	}

}

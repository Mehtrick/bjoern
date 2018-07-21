
package de.mehtrick.jvior.parser.modell.yaml;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.ToString;

@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Scenario",
    "Given",
    "When",
    "Then"
})
public class Scenario implements Serializable
{

    @JsonProperty("Scenario")
    private String scenario;
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

}

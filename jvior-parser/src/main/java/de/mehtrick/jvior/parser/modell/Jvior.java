package de.mehtrick.jvior.parser.modell;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Jvior {
	private String feature;
	private List<JviorScenario> scenarios;

}

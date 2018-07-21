package de.mehtrick.jvior.parser.modell;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JviorScenario {
	private String name;
	private List<JviorStatement> given;
	private List<JviorStatement> when;
	private List<JviorStatement> then;

}

package de.mehtrick.jvior.parser.modell;

import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JviorStatement {

	private String primitiveStatement;
	private String StatementWithoutParameters;
	@Builder.Default
	private Map<Integer, String> parameters = new HashMap<>();

}

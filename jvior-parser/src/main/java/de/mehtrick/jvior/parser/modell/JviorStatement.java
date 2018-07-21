package de.mehtrick.jvior.parser.modell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.text.WordUtils;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JviorStatement {

	private String primitiveStatement;
	private String StatementWithoutParameters;
	private List<String> parameters = new ArrayList<>();

	public JviorStatement(String statement) {
		setPrimitiveStatement(statement);
		setStatementWithoutParameters(removeParametersFromStatement(statement));
		parseParameters(statement);
	}

	private void parseParameters(String statement) {
		Pattern MY_PATTERN = Pattern.compile("\"(.*?)\"");
		Matcher matcher = MY_PATTERN.matcher(statement);
		while (matcher.find()) {
			parameters.add(matcher.group().replaceAll("\"", ""));
		}
	}

	private String removeParametersFromStatement(String statement) {
		return Arrays.asList(statement.split("\"(.*?)\"")).stream().map(WordUtils::capitalizeFully)
				.map(s -> s.replaceAll(" ", "")).collect(Collectors.joining());
	}

}

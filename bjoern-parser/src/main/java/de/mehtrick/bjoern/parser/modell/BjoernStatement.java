package de.mehtrick.bjoern.parser.modell;

import de.mehtrick.bjoern.parser.BjoernTextParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A more complex extraction of the bjoern-spec statement A statement is part of
 * a given, when or then. This class contains the original statement as
 * {@link #primitiveStatement} Also it contains a camelCased
 * {@link #statementWithoutParameters}. The parameters are extracted in a list
 * found in {@link #parameters}
 *
 * @author mehtrick
 */
public class BjoernStatement {

    private static final String PARAMETERPATTERN = "\"(\\\\.|[^\\\"])*\"";
    private String primitiveStatement;
    /**
     * camelCased statement without the parameter in the double qoutes
     */
    private String statementWithoutParameters;
    private List<String> parameters = new ArrayList<>();

    /**
     * Creates a bjoernstatement which will be the base for future generation processes
     *
     * @param statement The statement from the zgr file
     * @param keyword   the keyword under which the statement is found in the zgr file
     */
    public BjoernStatement(String statement, BDDKeyword keyword) {
        setPrimitiveStatement(statement);
        setStatementWithoutParameters(keyword.name().toLowerCase() + "_" + normalizeStatement(statement));
        parseParameters(statement);
    }

    private void parseParameters(String statement) {
        Pattern MY_PATTERN = Pattern.compile(PARAMETERPATTERN);
        Matcher matcher = MY_PATTERN.matcher(statement);
        while (matcher.find()) {
            parameters.add(matcher.group().replaceAll("(?<!\\\\)\"", ""));
        }
    }

    private String normalizeStatement(String statement) {
        return Arrays.stream(statement.split(PARAMETERPATTERN)).map(BjoernTextParser::parseText)
                .collect(Collectors.joining(BjoernTextParser.BLANK_REPLACEMENT));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BjoernStatement) {
            BjoernStatement compareableStatement = (BjoernStatement) obj;
            return Objects.equals(compareableStatement.getStatementWithoutParameters(),
                    this.getStatementWithoutParameters());

        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return statementWithoutParameters.hashCode() + getParameters().size();
    }

    public String getPrimitiveStatement() {
        return this.primitiveStatement;
    }

    public void setPrimitiveStatement(String primitiveStatement) {
        this.primitiveStatement = primitiveStatement;
    }

    public String getStatementWithoutParameters() {
        return this.statementWithoutParameters;
    }

    public void setStatementWithoutParameters(String statementWithoutParameters) {
        this.statementWithoutParameters = statementWithoutParameters;
    }

    public List<String> getParameters() {
        return this.parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public String toString() {
        return "BjoernStatement(primitiveStatement=" + this.getPrimitiveStatement() + ", statementWithoutParameters=" + this.getStatementWithoutParameters() + ", parameters=" + this.getParameters() + ")";
    }
}

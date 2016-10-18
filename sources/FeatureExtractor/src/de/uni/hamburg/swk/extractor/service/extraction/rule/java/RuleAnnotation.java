package de.uni.hamburg.swk.extractor.service.extraction.rule.java;

import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.service.extraction.Rule;
import de.uni.hamburg.swk.extractor.utils.Constants;

public class RuleAnnotation extends Rule
{
    private final String SIMPLE_ANNOTATION = "^\\s*@%s\\s*(\\(.*\\)\\s*$|\\s*$)";
    private final String FULLY_QUALIFIED_ANNOTATION = "^\\s*@%s.%s\\s*(\\(.*\\)\\s*$|\\s*$)"; 
    private final String PARAMS_ANNOTATION = ".*@(|%s.)%s\\s*\\(.*%s.*\\)";

    @Override
    public boolean check(String line, Indicator indicator)
    {
        return !indicator.getParameter().equals(Constants.STRING_EMPTY)
                ? hasParams(line, indicator.getValue(), indicator.getParameter(), indicator.getScope())
                : (hasFullyQualifiedAnnotation(line, indicator.getValue(), indicator.getScope())
                        || hasSimpleAnnotation(line, indicator.getValue()));
    }

    /**
     * 
     * @param line The line to be checked
     * @param value The type of the Annotation
     * @param parameter The parameters that this annotation should match
     * @param scope The import for this annotation if annotation was used fully
     *            qualified
     * @return If the line matches the given parameters
     */
    private boolean hasParams(String line, String value, String parameter, String scope)
    {
        String mScope = sanitize(scope);
        String mValue = sanitize(value);
        String mParameter = sanitize(parameter);

        return line.matches(String.format(PARAMS_ANNOTATION, mScope, mValue, mParameter));
    }

    /**
     * 
     * @param line The line to be checked
     * @param value The type of the Annotation
     * @param scope The import for this annotation if annotation was used fully
     *            qualified
     * @return If the line matches the given parameters
     */
    private boolean hasFullyQualifiedAnnotation(String line, String value, String scope)
    {
        return line.matches(String.format(FULLY_QUALIFIED_ANNOTATION, scope, value));
    }

    /**
     * Checks if the given annotation of type {@code <T>} exists in the line.
     * <br>
     * The annotation is identified by {@code @<T>}
     * 
     * @param line The line to be checked
     * @param value The type of the Annotation
     * @return If the line matches the given parameters
     */
    private boolean hasSimpleAnnotation(String line, String value)
    {
        return line.matches(String.format(SIMPLE_ANNOTATION, value));
    }
}

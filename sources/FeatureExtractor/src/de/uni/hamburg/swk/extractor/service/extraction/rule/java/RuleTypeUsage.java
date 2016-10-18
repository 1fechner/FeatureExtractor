package de.uni.hamburg.swk.extractor.service.extraction.rule.java;

import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.service.extraction.Rule;

/**
 * The usage of a type &lt;T&gt; is indicated by one or more of the following:<br>
 * <li>Use of the runtime '{@code <T>.class}' object of that type <br> <li>A
 * cast to that type with '{@code (<T>)}</code>' <li>Instantiation of that type
 * by calling its constructor with ' {@code new <T>(...)}' <li>Declaring a
 * variable of type {@code <T>} <li>Using the type {@code <T>} as a parameter
 * 
 * @author tobias
 *
 */
public class RuleTypeUsage extends Rule
{
    private static String CLASS_MATCH = "^.*(%s.|)%s.class.*$";
    private static String CAST_MATCH = "^.*((%s.|)%s).*$";
    private static String INSTANTIATION_MATCH = ".*new (%s.|)%s\\s*\\(.*";
    private static String INSTANCEOF_MATCH = "^.*instanceof\\s+(%s.|)%s.*$";
    private static String VAR_PARAM_MATCH = ".*(\\s|\\(|,)(%s.|)%s\\s+\\w*.*";

    @Override
    public boolean check(String line, Indicator indicator)
    {
        return line.matches(String.format(CLASS_MATCH, indicator.getScope(), indicator.getValue()))
                || line.matches(String.format(CAST_MATCH, indicator.getScope(), indicator.getValue()))
                || line.matches(String.format(INSTANCEOF_MATCH, indicator.getScope(), indicator.getValue()))
                || line.matches(String.format(INSTANTIATION_MATCH, indicator.getScope(), indicator.getValue()))
                || line.matches(String.format(VAR_PARAM_MATCH, indicator.getScope(), indicator.getValue()));
    }
}
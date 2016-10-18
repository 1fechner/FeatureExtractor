package de.uni.hamburg.swk.extractor.service.extraction.rule.xml;

import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.service.extraction.Rule;

public class RuleTag extends Rule
{
    private final String SIMPLE_TAG = "^\\s*<%s(>| .*>).*";
    private final String TAG_PARAMETER = "^\\s*<%s\\s*.*\\s*%s.*>.*";

    @Override
    public boolean check(String line, Indicator indicator)
    {

        return indicator.getParameter() != null ? hasParameter(line, indicator.getValue(), indicator.getParameter())
                : hasTag(line, indicator.getValue());
    }

    private boolean hasTag(String line, String value)
    {
        return line.matches(String.format(SIMPLE_TAG, value));
    }

    private boolean hasParameter(String line, String value, String parameter)
    {
        String sParam = sanitize(parameter);

        return line.matches(String.format(TAG_PARAMETER, value, sParam));
    }
}

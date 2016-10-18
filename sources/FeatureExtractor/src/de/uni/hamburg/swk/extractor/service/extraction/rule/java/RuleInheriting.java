package de.uni.hamburg.swk.extractor.service.extraction.rule.java;

import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.service.extraction.Rule;

public class RuleInheriting extends Rule
{
    private final String MATCH = ".*extends\\s+%s.*";

    @Override
    public boolean check(String line, Indicator indicator)
    {
        return line.matches(String.format(MATCH, indicator.getValue()));
    }
}
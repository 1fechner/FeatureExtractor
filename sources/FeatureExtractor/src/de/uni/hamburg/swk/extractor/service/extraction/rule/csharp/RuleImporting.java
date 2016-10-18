package de.uni.hamburg.swk.extractor.service.extraction.rule.csharp;

import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.service.extraction.Rule;

public class RuleImporting extends Rule
{
    private final String MATCH = "using %s;";
    
    @Override
    public boolean check(String line, Indicator indicator)
    {
        return line.matches(String.format(MATCH, indicator.getValue()));
    }
}
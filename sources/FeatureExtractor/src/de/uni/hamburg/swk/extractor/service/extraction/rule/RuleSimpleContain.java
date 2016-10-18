package de.uni.hamburg.swk.extractor.service.extraction.rule;

import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.service.extraction.Rule;

public class RuleSimpleContain extends Rule
{
    @Override
    public boolean check(String line, Indicator indicator)
    {
        return line.contains(indicator.getValue());
    }
}

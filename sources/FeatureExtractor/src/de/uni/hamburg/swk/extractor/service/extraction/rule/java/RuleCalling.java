package de.uni.hamburg.swk.extractor.service.extraction.rule.java;

import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.service.extraction.Rule;

public class RuleCalling extends Rule
{

    @Override
    public boolean check(String line, Indicator indicator)
    {
        return line.contains(String.format(".%s(", indicator.getValue()));
    }

}

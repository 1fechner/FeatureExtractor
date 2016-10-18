package de.uni.hamburg.swk.extractor.service.extraction.rule;

import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.service.extraction.Rule;

/**
 * Rule for all undefined indicators. Does not match anything by default and
 * returns false
 * 
 * @author tobias
 *
 */
public class RuleUndefined extends Rule
{
    @Override
    public boolean check(String line, Indicator indicator)
    {
        return false;
    }
}
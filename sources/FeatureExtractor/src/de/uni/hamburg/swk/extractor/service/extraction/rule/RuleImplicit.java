package de.uni.hamburg.swk.extractor.service.extraction.rule;

import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.service.extraction.Rule;

/**
 * Rule for implicit indicators. These indicate features that are or are not
 * implicitly used, when a set of features is used elsewhere. These are rather
 * tricky, because they can't be identified (e.g. default values that are used
 * when not explicitly overwritten).
 * 
 * @author tobias
 *
 */
public class RuleImplicit extends Rule
{
    @Override
    public boolean check(String line, Indicator indicator)
    {
        return true;
    }

}

package de.uni.hamburg.swk.extractor.service.extraction;

import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.utils.Constants;

/**
 * Abstract base class for all rules. Each rule checks whether the given line
 * matches the values and parameters defined by the given indicator
 * 
 * @author tobias
 *
 */
public abstract class Rule
{    
    /**
     * Checks whether the given line matches the rules specified by the
     * indicator
     * 
     * @param line The line to be checked
     * @param indicator The indicator providing the rules
     * @return True if the lines matches the rules
     */
    public abstract boolean check(String line, Indicator indicator);
    

    /**
     * Sanitizes the given value, thereby replacing several characters, that
     * need to be escaped so they are recognized as should when using the value
     * in a regular expression
     * 
     * @param val The value to be sanitized
     * @return The sanitized value
     */
    protected String sanitize(String val)
    {
        return val.replace("*", Constants.MATCH_ANY_WORD).replace(" ", Constants.MATCH_SPACES).replace("{", "\\{").replace("}", "\\}");
    }
}
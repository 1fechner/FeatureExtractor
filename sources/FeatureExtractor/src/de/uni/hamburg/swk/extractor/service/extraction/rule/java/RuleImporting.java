package de.uni.hamburg.swk.extractor.service.extraction.rule.java;

import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.service.extraction.Rule;

public class RuleImporting extends Rule
{
    private final String MATCH = "import %s;";
    private final char DIV = '.';
    private final char ANY = '*';
    
    @Override
    public boolean check(String line, Indicator indicator)
    {
    	// Check if full import is used
    	if (line.startsWith(String.format(MATCH, indicator.getValue())))
    		return true;    	
    	
    	// If not, check if super packages (with .*) are used
    	String[] sub = splitNamespace(indicator.getValue());
    	
    	String p = "";
    	
    	if (sub.length > 0)
    	{
    		for (int i = 0; i < sub.length; ++i)
    		{
    			p += sub[i];
    			p += DIV;
    			
    			if (line.startsWith(String.format(MATCH, p + ANY)));
    		}
    	}
    	
    	// Import was not found
    	return false;
    }
    
    private String[] splitNamespace(String namespace)
    {
    	return namespace.split(".");
    }
}
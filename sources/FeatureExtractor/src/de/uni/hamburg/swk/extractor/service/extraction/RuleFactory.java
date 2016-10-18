package de.uni.hamburg.swk.extractor.service.extraction;

import de.uni.hamburg.swk.extractor.database.entities.IndicatorLanguage;
import de.uni.hamburg.swk.extractor.database.entities.IndicatorType;
import de.uni.hamburg.swk.extractor.service.extraction.rule.RuleUndefined;

public class RuleFactory
{
    private RuleFactory()
    {
        // ...
    }

    /**
     * Returns a new rule object according to the given type
     * 
     * @param type The type of the rule to be constructed
     * @return A new rule to check the given type
     */
    public static Rule getRuleFor(IndicatorType type, IndicatorLanguage language)
    {        
        return create(type, language);
//        System.out.println(Rule.class.getPackage().getName());
//        
//        System.out.println(create(type, language));
//
//        switch (type)
//        {
//            case SimpleContain:
//                return new RuleSimpleContain();
//            case SimpleMatch:
//                return new RuleSimpleMatch();
//            case Importing:
//                return new RuleImporting();
//            case Inheriting:
//                return new RuleInheriting();
//            case Implementing:
//                return null;
//            case TypeUsage:
//                return new RuleTypeUsage();
//            case Calling:
//                return new RuleCalling();
//            case CallingParams:
//                return null;
//            case Annotation:
//                return new RuleAnnotation();
//            case Implicit:
//            default:
//                return new RuleUndefined();
//        }
    }

    private static Rule create(IndicatorType type, IndicatorLanguage language)
    {
        String rule = Rule.class.getPackage().getName() + ".rule";
        rule += ".";
        
        if (language != IndicatorLanguage.Any)
            rule += language.name().toLowerCase() + ".";
        
        rule += "Rule" + type.name();
        
        try
        {
            // Initialize rule via reflection
            Rule r = (Rule) Class.forName(rule).newInstance();
            return r;
        }
        catch (Exception e)
        {
            System.out.println("No rule");
            e.printStackTrace();
        }
        return new RuleUndefined();
    }
}

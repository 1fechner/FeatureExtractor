package de.uni.hamburg.swk.extractor.management;

import java.util.List;

import de.uni.hamburg.swk.extractor.database.dao.impl.IndicatorDAO;
import de.uni.hamburg.swk.extractor.database.dao.impl.TechnologyFeatureDAO;
import de.uni.hamburg.swk.extractor.database.dao.impl.TechnologySolutionDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;

public class ManagementCommandViewFlat extends ManagementCommand
{
    private TechnologySolutionDAO _technologySolutionDAO;
    private TechnologyFeatureDAO _technologyFeatureDAO;
    private IndicatorDAO _indicatorDAO;

    public ManagementCommandViewFlat()
    {
        _technologySolutionDAO = new TechnologySolutionDAO();
        _technologyFeatureDAO = new TechnologyFeatureDAO();
        _indicatorDAO = new IndicatorDAO();
    }

    @Override
    public void execute(String[] args)
    {
        System.out.println("Displaying all assets in database\n");

        printTechnologySolutions();

        printTechnologyFeatures();

        printIndicators();
    }

    private void printTechnologySolutions()
    {
        List<TechnologySolution> res = _technologySolutionDAO.getAll();

        System.out.println("<< Technology Solution >>");

        for (TechnologySolution t : res)
        {
            System.out.println(String.format("Name:\t\t%s (ID: %s)", t.getName(), t.getId()));
            System.out.println("Description:\t" + t.getDescription());
            System.out.println("DependsOn:\t" + (t.getDependsOn() != null ? t.getDependsOn().getName() : "-"));
            System.out.println("DB-Ref:\t\t" + t.getDecisionBuddyRef());
            System.out.println("-----------------------------");
        }
    }

    private void printTechnologyFeatures()
    {
        System.out.println("<< TechnologyFeature >>");

        List<TechnologyFeature> featRes = _technologyFeatureDAO.getAll();

        for (TechnologyFeature f : featRes)
        {
            System.out.println(String.format("Name:\t\t%s (ID: %s)", f.getName(), f.getId()));
            System.out.println("Description:\t" + f.getDescription());
            System.out.println(String.format("BelongsTo:\t%s", f.getBelongsTo() != null ? f.getBelongsTo().getName()
                    : "-"));
            System.out.println(String.format("DependsOn:\t%s", f.getDependsOn() != null ? f.getDependsOn().getName()
                    : "-"));
            System.out.println("-----------------------------");
        }
    }

    private void printIndicators()
    {
        System.out.println("<< Indicator >>");

        List<Indicator> indicRes = _indicatorDAO.getAll();

        for (Indicator i : indicRes)
        {
            System.out.println(String.format("Indicator\t%s", i.getId()));
            System.out.println(String.format("BelongsTo:\t%s", i.getBelongsTo().getName()));
            System.out.println("Type:\t\t" + i.getType());
            System.out.println("Value:\t\t" + i.getValue());
            System.out.println("Parameter:\t" + i.getParameter());
            System.out.println("Scope:\t\t" + i.getScope());
            System.out.println("Confidence:\t" + i.getConfidence());
            System.out.println("-----------------------------");
        }
    }
}
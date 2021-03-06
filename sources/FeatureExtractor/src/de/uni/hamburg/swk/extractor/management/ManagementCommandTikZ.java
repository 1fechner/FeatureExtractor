package de.uni.hamburg.swk.extractor.management;

import java.util.Date;
import java.util.List;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.database.dao.impl.IndicatorDAO;
import de.uni.hamburg.swk.extractor.database.dao.impl.TechnologyFeatureDAO;
import de.uni.hamburg.swk.extractor.database.dao.impl.TechnologySolutionDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;
import de.uni.hamburg.swk.extractor.utils.HelperFunctions;

/**
 * Prints the current state of the database as a tree. Visualizes the
 * dependencies between the assets
 * 
 * @author tobias
 *
 */
public class ManagementCommandTikZ extends ManagementCommand
{
    private TechnologySolutionDAO _technologySolutionDAO;
    private TechnologyFeatureDAO _technologyFeatureDAO;
    private IndicatorDAO _indicatorDAO;

    private final String LATEX_HEADER = "\\documentclass{standalone}" + "\\usepackage{tikz}" + "\\usetikzlibrary{trees}"
            + "\\begin{document}" + "\\tikzstyle{every node}=[draw=black,thick,anchor=west]"
            + "\\tikzstyle{indicator}=[draw=red,fill=red!30]" + "\\tikzstyle{feature}=[fill=gray!50]"
            + "\\begin{tikzpicture}[  grow via three points="
            + "{one child at (0.5,-0.7) and  two children at (0.5,-0.7) and (0.5,-1.4)}, "
            + " edge from parent path={(\\tikzparentnode.south) |- (\\tikzchildnode.west)}]";
    private final String LATEX_FOOTER = "\\end{tikzpicture}" + "\\end{document}";

    public ManagementCommandTikZ()
    {
        _technologySolutionDAO = new TechnologySolutionDAO();
        _technologyFeatureDAO = new TechnologyFeatureDAO();
        _indicatorDAO = new IndicatorDAO();
    }

    @Override
    public void execute(String[] args)
    {
        List<TechnologySolution> root = _technologySolutionDAO.getTechnologySolutionsRoot();

        System.out.println("Generating LaTeX-file...");

        // System.out.println(Configuration.getOutput());
        // HelperFunctions.setFileOutput(Configuration.getOutput());

        System.out.println(String.format("%% Generated by Feature Extractor Version %s on %s",
                Configuration.getVersion(), new Date()));

        System.out.println(LATEX_HEADER);

        for (TechnologySolution t : root)
        {
            System.out.println(String.format("\\node {%s}", t.getName()));

            int s = 0;

            s += printChildrenForTechnologySolution(t.getId(), 1);
            s += printFeaturesForTechnologySolution(t.getId(), 1);

            for (int i = 0; i < s; ++i)
            {
                System.out.println("child [missing] {}");
            }

            System.out.println(";");
        }

        System.out.println(LATEX_FOOTER);

        // HelperFunctions.restoreOutput();

        System.out.println("Done!");
    }

    /**
     * Print all children for a technology solution
     * 
     * @param id The id of the solution to be printed
     * @param depth The indention depth for printing
     */
    private int printChildrenForTechnologySolution(int id, int depth)
    {
        List<TechnologySolution> node = _technologySolutionDAO.getTechnologySolutionsChildren(id);

        int sub = 0;

        for (TechnologySolution t : node)
        {
            System.out
                    .println(String.format("%schild { node {%s}  ", HelperFunctions.getIndention(depth), t.getName()));

            int s = 0;
            s += printChildrenForTechnologySolution(t.getId(), depth + 1);
            s += printFeaturesForTechnologySolution(t.getId(), depth + 1);

            System.out.println(String.format("%s}", HelperFunctions.getIndention(depth)));

            for (int i = 0; i < s; ++i)
            {
                System.out.println(String.format("%schild [missing] {}", HelperFunctions.getIndention(depth)));
            }

            sub += s;
        }

        return sub + node.size();
    }

    /**
     * 
     * @param id The id of the technology feature to be printed
     * @param depth The indention depth for printing
     */
    private int printFeaturesForTechnologySolution(int id, int depth)
    {
        List<TechnologyFeature> node = _technologyFeatureDAO.getRootFor(id);

        int sub = 0;

        for (TechnologyFeature t : node)
        {
            System.out.println(
                    String.format("%schild { node [feature] {%s}", HelperFunctions.getIndention(depth), t.getName()));
            int s = 0;

            s += printChildrenForFeature(t.getId(), depth + 1);
            s += printIndicatorsForFeature(t.getId(), depth + 1);

            System.out.println(String.format("%s}", HelperFunctions.getIndention(depth)));

            for (int i = 0; i < s; ++i)
            {
                System.out.println(String.format("%schild [missing] {}", HelperFunctions.getIndention(depth)));
            }
            sub += s;
        }

        return node.size() + sub;
    }

    private int printChildrenForFeature(int id, int depth)
    {
        List<TechnologyFeature> node = _technologyFeatureDAO.getChidrenFor(id);
        // SessionService.getCurrentSession()
        // .createQuery("FROM TechnologyFeature WHERE dependsOn = " + id +
        // " ORDER BY name").list();

        int sub = 0;

        for (TechnologyFeature t : node)
        {
            System.out.println(
                    String.format("%schild { node [feature]{%s}", HelperFunctions.getIndention(depth), t.getName()));

            int s = 0;
            s += printChildrenForFeature(t.getId(), depth + 1);
            s += printIndicatorsForFeature(t.getId(), depth + 1);
            System.out.println(String.format("%s}", HelperFunctions.getIndention(depth)));

            for (int i = 0; i < s; ++i)
            {
                System.out.println(String.format("%schild [missing] {}", HelperFunctions.getIndention(depth)));
            }

            sub += s;
        }

        return node.size() + sub;
    }

    /**
     * 
     * @param id The id of the feature to be printed
     * @param depth The indention depth for printing
     */
    private int printIndicatorsForFeature(int id, int depth)
    {
        List<Indicator> indicators = _indicatorDAO.getIndicatorsForFeature(id);

        // Append all indicators
        for (Indicator i : indicators)
        {
            System.out.println(String.format("%schild { node [indicator]{%s}}", HelperFunctions.getIndention(depth),
                    i.getType(), i.getValue()));
        }

        return indicators.size();
    }
}
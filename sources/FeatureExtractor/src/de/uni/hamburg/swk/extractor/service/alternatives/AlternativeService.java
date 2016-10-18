package de.uni.hamburg.swk.extractor.service.alternatives;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Transaction;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.configuration.OptimizationRules;
import de.uni.hamburg.swk.extractor.database.SessionService;
import de.uni.hamburg.swk.extractor.database.dao.impl.AlternativeDAO;
import de.uni.hamburg.swk.extractor.database.dao.impl.ResultSetDAO;
import de.uni.hamburg.swk.extractor.database.dao.impl.TechnologyFeatureDAO;
import de.uni.hamburg.swk.extractor.database.dao.impl.TechnologySolutionDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.Feature;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;
import de.uni.hamburg.swk.extractor.database.entities.alternatives.Alternative;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;
import de.uni.hamburg.swk.extractor.database.entities.result.ResultSet;
import de.uni.hamburg.swk.extractor.gui.callback.CallbackRegistry;
import de.uni.hamburg.swk.extractor.gui.callback.CallbackType;
import de.uni.hamburg.swk.extractor.service.Service;

public class AlternativeService extends Service
{
    private static final String FINISHED = "Finished";
    private static final String S_IS_AN_ALTERNATIVE_TO_S_S = "\t'%s' is an alternative to '%s' (%s)";
    private static final String S_S_FEATURE_S_USED = "%s (%s feature(s) used)";
    private static final String CHECKING_S = "Checking %s...";
    private static final String WORKING_ON_PROJECT_S_CONFIDENCE_THRESHOLD_WAS_S = "Working on Project '%s', Confidence Threshold was %s";
    private static final String SETTING_UP = "Setting up...";
    private TechnologyFeatureDAO _technologyFeatureDAO;
    private TechnologySolutionDAO _technologySolutionDAO;
    private ResultSetDAO _resultsetDAO;
    private AlternativeDAO _alternativeDAO;

    Map<TechnologySolution, Set<Feature>> _technologiesFeatureUsed;
    Map<TechnologySolution, Set<Feature>> _technologiesFeatureAll;

    public AlternativeService()
    {
        _technologyFeatureDAO = new TechnologyFeatureDAO();
        _technologySolutionDAO = new TechnologySolutionDAO();
        _resultsetDAO = new ResultSetDAO();
        _alternativeDAO = new AlternativeDAO();
    }

    /**
     * Find alternatives for features saved for the given projectId
     * 
     * @param projectName The project to find alternatives for
     */
    public List<Alternative> findAlternatives(Project project)
    {
        CallbackRegistry.invoke(CallbackType.STATUS, new Object[] { SETTING_UP }, AlternativeService.class);

        Project p = Configuration.getSelectedProject();

        if (p == null)
        {
            print("No Project selected. Please select a Project first.");
            return new ArrayList<>();
        }

        print(String.format(WORKING_ON_PROJECT_S_CONFIDENCE_THRESHOLD_WAS_S, p.getName(), p.getMinConfidence()));

        List<ResultSet> resultSets = _resultsetDAO.getGroupByTechnologyFeature(p);

        List<TechnologyFeature> featuresUsed = new ArrayList<TechnologyFeature>();

        for (ResultSet res : resultSets)
        {
            featuresUsed.add(res.getTechnologyFeature());
        }

        _technologiesFeatureUsed = groupFeaturesByTechnologies(featuresUsed);

        _technologiesFeatureAll = groupAllFeatures();

        Transaction tx = SessionService.getCurrentSession().beginTransaction();

        // Delete old alternatives saved for this project
        _alternativeDAO.deleteForProject(p.getId());

        List<Alternative> alternatives = new ArrayList<Alternative>();

        // Find alternatives for each technology
        for (TechnologySolution t1 : _technologiesFeatureUsed.keySet())
        {
            CallbackRegistry.invoke(CallbackType.STATUS, new Object[] { String.format(CHECKING_S, t1.getName()) },
                    AlternativeService.class);
            print(String.format(S_S_FEATURE_S_USED, t1.getName(), _technologiesFeatureUsed.get(t1).size()));

            for (TechnologySolution t2 : _technologiesFeatureAll.keySet())
            {
                if ((OptimizationRules.R_301_DIRECT_RELATION_NO_ALTERNATIVE && ((t2.isParent(t1) || t1.isParent(t2))))
                        || t1.equals(t2))
                    continue;

                Alternative a = findAlternative(p, t1, t2, _technologiesFeatureUsed.get(t1),
                        _technologiesFeatureAll.get(t2));

                if (a.getCoverage() > Configuration.getSelectedProject().getMinFeatureCoverage())
                {
                    print(String.format(S_IS_AN_ALTERNATIVE_TO_S_S, a.getTechnology().getName(),
                            a.getIsAlternativeTo().getName(), a.getCoverage()));

                    alternatives.add(a);
                    SessionService.getCurrentSession().save(a);
                }
            }
        }

        // Print results
        printFeatureCoverageMatrix(alternatives);

        CallbackRegistry.invoke(CallbackType.STATUS, new Object[] { FINISHED }, AlternativeService.class);

        tx.commit();

        return alternatives;
    }

    private void printFeatureCoverageMatrix(List<Alternative> alternatives)
    {
        Map<TechnologySolution, List<Alternative>> map = new HashMap<TechnologySolution, List<Alternative>>();

        for (Alternative a : alternatives)
        {
            if (map.containsKey(a.getIsAlternativeTo()))
            {
                List<Alternative> ls = map.get(a.getIsAlternativeTo());
                ls.add(a);
                map.put(a.getIsAlternativeTo(), ls);
            }
            else
            {
                List<Alternative> ls = new ArrayList<Alternative>();
                ls.add(a);
                map.put(a.getIsAlternativeTo(), ls);
            }
        }

        for (TechnologySolution t : map.keySet())
        {
            Set<Feature> features = new HashSet<Feature>();

            for (Alternative a : map.get(t))
            {
                features.addAll(_technologiesFeatureUsed.get(a.getIsAlternativeTo()));
            }

            List<Feature> sortedFeatures = new ArrayList<Feature>(features);

            sortedFeatures.sort(Comparator.comparing(e -> e.getName()));

            // Sort list to provide alternatives with highest coverage first
            List<Alternative> alters = map.get(t);
            alters.sort(Comparator.comparing(e -> e.getCoverage()));
            Collections.reverse(alters);

            if (!OptimizationRules.R_000_GUI)
                CoverageMatrixService.printFeatureCoverageMatrixFor(t, sortedFeatures, map.get(t));
        }
    }

    /**
     * Calculates an alternative for the given {@link TechnologySolution} and
     * their set of {@link Feature}
     * 
     * @param alternativeTo The {@link TechnologySolution} an alternative to is
     *            searched for
     * @param technologySolution The {@link TechnologySolution} to check the
     *            coverage for
     * @param shouldHave {@link Feature} the alternative should cover
     * @param has {@link Feature} the currently used {@link TechnologySolution}
     *            covers
     * @return A alternative with the calculated coverage for the given values
     */
    private Alternative findAlternative(Project project, TechnologySolution alternativeTo,
            TechnologySolution technologySolution, Set<Feature> shouldHave, Set<Feature> has)
    {
        Alternative a = new Alternative();
        a.setTechnology(technologySolution);
        a.setIsAlternativeTo(alternativeTo);
        a.setProject(project);
        a.setVersion(project.getVersion());
        a.calculateCoverage(shouldHave, has);

        return a;
    }

    /**
     * Collects all inherited {@link TechnologyFeature} for the given
     * {@link TechnologySolution}
     * 
     * @param technology The {@link TechnologySolution} to find all
     *            {@link TechnologyFeature} for
     * @return A list of all own and inherited {@link TechnologyFeature}
     */
    private Set<Feature> findAllFeaturesForTechnology(TechnologySolution technology)
    {
        Set<Feature> features = new HashSet<Feature>();

        for (TechnologyFeature t : _technologyFeatureDAO.getByBelongsTo(technology.getId()))
        {
            if (t.getFeatureImplemented() != null)
                features.add(t.getFeatureImplemented());
        }

        TechnologySolution iterator = technology.getDependsOn();

        // Iterate through all dependencies and add their direct children
        if (OptimizationRules.R_300_INHERITED_FEATURES)
        {
            while (iterator != null)
            {
                for (TechnologyFeature t : _technologyFeatureDAO.getByBelongsTo(iterator.getId()))
                {
                    if (t.getFeatureImplemented() != null)
                        features.add(t.getFeatureImplemented());
                }

                iterator = iterator.getDependsOn();
            }
        }

        return features;
    }

    /**
     * Groups all {@link Feature} by their {@link TechnologySolution}
     * 
     * @return A map consisting of a {@link TechnologySolution} as key and a
     *         list of implemented {@link Feature} as respective value
     */
    private Map<TechnologySolution, Set<Feature>> groupAllFeatures()
    {
        Map<TechnologySolution, Set<Feature>> res = new HashMap<TechnologySolution, Set<Feature>>();

        List<TechnologySolution> allTechnologies = _technologySolutionDAO.getAll();

        for (TechnologySolution sol : allTechnologies)
        {
            Set<Feature> featuresForTechnology = findAllFeaturesForTechnology(sol);

            res.put(sol, featuresForTechnology);
        }

        return res;
    }

    /**
     * Groups the given list of {@link TechnologyFeature} by they direct
     * dependencies
     * 
     * @param features The list of {@link TechnologyFeature} to be grouped
     * @return A map consisting of the {@link TechnologySolution} as a key and
     *         the list of depending {@link TechnologyFeature} as corresponding
     *         value
     */
    public static Map<TechnologySolution, Set<Feature>> groupFeaturesByTechnologies(List<TechnologyFeature> features)
    {
        Map<TechnologySolution, Set<Feature>> grouped = new HashMap<TechnologySolution, Set<Feature>>();

        for (TechnologyFeature feat : features)
        {
            if (feat.getFeatureImplemented() == null)
                continue;

            if (grouped.containsKey(feat.getBelongsTo()))
            {
                Set<Feature> f = grouped.get(feat.getBelongsTo());
                f.add(feat.getFeatureImplemented());
                grouped.put(feat.getBelongsTo(), f);
            }
            else
            {
                Set<Feature> f = new HashSet<Feature>();
                f.add(feat.getFeatureImplemented());
                grouped.put(feat.getBelongsTo(), f);
            }
        }

        if (OptimizationRules.R_300_INHERITED_FEATURES)
        {
            // Add feature where they are inherited
            for (TechnologySolution s : grouped.keySet())
            {
                for (TechnologyFeature feat : features)
                {

                    if (feat.getFeatureImplemented() == null)
                        continue;

                    if (s.isParent(feat.getBelongsTo()))
                    {
                        Set<Feature> f = grouped.get(s);
                        f.add(feat.getFeatureImplemented());
                        grouped.put(s, f);
                    }
                }
            }
        }

        return grouped;
    }
}
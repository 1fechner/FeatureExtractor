package de.uni.hamburg.swk.extractor.database.entities.alternatives;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.database.entities.ak.Feature;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;

public class Alternative
{
    private int _id;
    private TechnologySolution _technology;
    private TechnologySolution _isAlternativeTo;
    private float _coverage = 0.0f;
    private Project _project;
    private int _version;
    private List<Feature> _featuresImplemeted;

    public Alternative()
    {
        _featuresImplemeted = new ArrayList<Feature>();
    }

    public int getId()
    {
        return _id;
    }

    public void setId(int id)
    {
        this._id = id;
    }

    public TechnologySolution getTechnology()
    {
        return _technology;
    }

    public void setTechnology(TechnologySolution technology)
    {
        this._technology = technology;
    }

    public TechnologySolution getIsAlternativeTo()
    {
        return _isAlternativeTo;
    }

    public void setIsAlternativeTo(TechnologySolution isAlternativeTo)
    {
        this._isAlternativeTo = isAlternativeTo;
    }

    public float getCoverage()
    {
        return _coverage;
    }

    public void setCoverage(float coverage)
    {
        this._coverage = coverage;
    }

    public Project getProject()
    {
        return _project;
    }

    public void setProject(Project project)
    {
        this._project = project;
    }

    public int getVersion()
    {
        return _version;
    }

    public void setVersion(int version)
    {
        this._version = version;
    }

    public List<Feature> getFeaturesImplemeted()
    {
        return _featuresImplemeted;
    }

    public void setFeaturesImplemeted(List<Feature> _featuresImplemeted)
    {
        this._featuresImplemeted = _featuresImplemeted;
    }

    /**
     * Calculates the coverage rate of the sets of {@link Feature} given. The
     * coverage is stored in this alternative
     * 
     * @param subset The first set to check the containment for
     * @param set The second set to check the containment in
     * @return The coverage of elements in subset also found in set
     */
    public void calculateCoverage(Set<Feature> subset, Set<Feature> set)
    {
        // List<RequiredFeatureSet> common = new
        // ArrayList<RequiredFeatureSet>();

        int count = subset.size();
        int found = 0;

        // Convert Sets to list (easier iteration, I don't care for memory
        List<Feature> lSubset = new ArrayList<Feature>(subset);
        List<Feature> lSet = new ArrayList<Feature>(set);

        for (Feature featureSubset : lSubset)
        {
            for (Feature featureSet : lSet)
            {
                if (featureSubset.equals(featureSet))
                {
                    _featuresImplemeted.add(featureSubset);
                    found += 1;

                    if (Configuration.isVerbose())
                        System.out.println(String.format("  has '%s'", featureSubset.getName()));
                }
            }
        }

        float coverage = (float) found / count;

        _coverage = coverage;
    }
}
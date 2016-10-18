package de.uni.hamburg.swk.extractor.database.entities.alternatives;

import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;

public class DecisionPoint
{
    private int _id;
    private TechnologySolution _commonRoot;
    private Alternative _alternative;
    private int _dependencyChainLength;
    private Project _project;
    private int _version;

    public int getId()
    {
        return _id;
    }

    public void setId(int id)
    {
        this._id = id;
    }

    public TechnologySolution getCommonRoot()
    {
        return _commonRoot;
    }

    public void setCommonRoot(TechnologySolution commonRoot)
    {
        this._commonRoot = commonRoot;
    }

    public Alternative getAlternative()
    {
        return _alternative;
    }

    public void setAlternative(Alternative _alternative)
    {
        this._alternative = _alternative;
    }

    public int getDependencyChainLength()
    {
        return _dependencyChainLength;
    }

    public void setDependencyChainLength(int dependencyChainLength)
    {
        this._dependencyChainLength = dependencyChainLength;
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

}
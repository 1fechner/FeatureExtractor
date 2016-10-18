package de.uni.hamburg.swk.extractor.database.entities.result;

public class Project
{
    private int _id;
    private String _name;
    private String _source;
    private float _minConfidence;
    private float _minFeatureCoverage;
    private int _maxDependencyChain;
    private int _version;

    public Project()
    {
    }

    public Project(String name)
    {
        this._name = name;
    }

    public int getId()
    {
        return _id;
    }

    public void setId(int id)
    {
        this._id = id;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        this._name = name;
    }

    public String getSource()
    {
        return _source;
    }

    public void setSource(String source)
    {
        this._source = source;
    }

    public float getMinConfidence()
    {
        return _minConfidence;
    }

    public void setMinConfidence(float minConfidence)
    {
        this._minConfidence = minConfidence;
    }

    public float getMinFeatureCoverage()
    {
        return _minFeatureCoverage;
    }

    public void setMinFeatureCoverage(float minFeatureCoverage)
    {
        this._minFeatureCoverage = minFeatureCoverage;
    }

    public int getMaxDependencyChain()
    {
        return _maxDependencyChain;
    }

    public void setMaxDependencyChain(int maxDependencyChain)
    {
        this._maxDependencyChain = maxDependencyChain;
    }

    public int getVersion()
    {
        return _version;
    }

    public void setVersion(int _version)
    {
        this._version = _version;
    }
}

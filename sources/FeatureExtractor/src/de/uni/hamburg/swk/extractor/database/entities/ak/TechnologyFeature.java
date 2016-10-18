package de.uni.hamburg.swk.extractor.database.entities.ak;

import de.uni.hamburg.swk.extractor.database.entities.CapabilityType;

public class TechnologyFeature
{
    private int _id;
    private TechnologySolution _belongsTo;
    private TechnologyFeature _dependsOn;
    private String _name;
    private String _description;
    private Feature _featureImplemented;
    private CapabilityType _capabilityType;

    public int getId()
    {
        return _id;
    }

    public void setId(int id)
    {
        this._id = id;
    }

    public TechnologySolution getBelongsTo()
    {
        return _belongsTo;
    }

    public void setBelongsTo(TechnologySolution belongsTo)
    {
        this._belongsTo = belongsTo;
    }

    public TechnologyFeature getDependsOn()
    {
        return _dependsOn;
    }

    public void setDependsOn(TechnologyFeature dependsOn)
    {
        this._dependsOn = dependsOn;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        this._name = name;
    }

    public String getDescription()
    {
        return _description;
    }

    public void setDescription(String description)
    {
        this._description = description;
    }

    public Feature getFeatureImplemented()
    {
        return _featureImplemented;
    }

    public void setFeatureImplemented(Feature featureImplemented)
    {
        this._featureImplemented = featureImplemented;
    }

    public CapabilityType getCapabilityType()
    {
        return _capabilityType;
    }

    public void setCapabilityType(CapabilityType capabilityType)
    {
        _capabilityType = capabilityType;
    }
}

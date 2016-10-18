package de.uni.hamburg.swk.extractor.database.entities.result;

import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;

public class ResultSet
// Get all result-sets for project
{
    private int _id;
    private Element _belongsTo;
    private Project _project;
    private TechnologyFeature _technologyFeature;
    private float _confidence;
    private int _version;

    public ResultSet()
    {
        // Empty constructor for Hibernate
    }

    /**
     * Create a new {@link ResultSet} from the given parameter
     * 
     * @param technologyFeature The {@link TechnologyFeature} detected for this
     *            {@link ResultSet}
     * @param element The {@link Element} this {@link ResultSet} belongs to
     * @param confidence The confidence calculated for this result
     * @param project The {@link Project} this {@link ResultSet} belongs to
     */
    public ResultSet(TechnologyFeature technologyFeature, Element element, float confidence, Project project)
    {
        this._technologyFeature = technologyFeature;
        this._belongsTo = element;
        this._confidence = confidence;
        this._project = project;
        this._version = element.getVersion();
    }

    public int getId()
    {
        return _id;
    }

    public void setId(int id)
    {
        this._id = id;
    }

    public Element getBelongsTo()
    {
        return _belongsTo;
    }

    public void setBelongsTo(Element belongsTo)
    {
        this._belongsTo = belongsTo;
    }

    public Project getProject()
    {
        return _project;
    }

    public void setProject(Project _project)
    {
        this._project = _project;
    }

    public TechnologyFeature getTechnologyFeature()
    {
        return _technologyFeature;
    }

    public void setTechnologyFeature(TechnologyFeature technologyFeature)
    {
        this._technologyFeature = technologyFeature;
    }

    public float getConfidence()
    {
        return _confidence;
    }

    public void setConfidence(float confidence)
    {
        this._confidence = confidence;
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

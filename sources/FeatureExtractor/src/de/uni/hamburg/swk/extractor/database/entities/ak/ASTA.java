package de.uni.hamburg.swk.extractor.database.entities.ak;

import de.uni.hamburg.swk.extractor.database.entities.ASTAAttribute;
import de.uni.hamburg.swk.extractor.database.entities.ASTAType;
import de.uni.hamburg.swk.extractor.database.entities.CapabilityType;

public class ASTA
{
    private int _id;
    private TechnologyFeature _belongsTo;
    private String _context;
    private String _description;
    private CapabilityType _capabilityType;
    private ASTAType _type;
    private ASTAAttribute _attribute;
    private String _source;

    public int getId()
    {
        return _id;
    }

    public void setId(int id)
    {
        this._id = id;
    }

    public TechnologyFeature getBelongsTo()
    {
        return _belongsTo;
    }

    public void setBelongsTo(TechnologyFeature belongsTo)
    {
        this._belongsTo = belongsTo;
    }

    public String getContext()
    {
        return _context;
    }

    public void setContext(String context)
    {
        this._context = context;
    }

    public String getDescription()
    {
        return _description;
    }

    public void setDescription(String description)
    {
        this._description = description;
    }

    public CapabilityType getCapabilityType()
    {
        return _capabilityType;
    }

    public void setCapabilityType(CapabilityType capabilityType)
    {
        this._capabilityType = capabilityType;
    }

    public ASTAType getType()
    {
        return _type;
    }

    public void setType(ASTAType type)
    {
        this._type = type;
    }

    public ASTAAttribute getAttribute()
    {
        return _attribute;
    }

    public void setAttribute(ASTAAttribute attribute)
    {
        this._attribute = attribute;
    }

    public String getSource()
    {
        return _source;
    }

    public void setSource(String _source)
    {
        this._source = _source;
    }
}

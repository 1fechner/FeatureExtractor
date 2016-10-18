package de.uni.hamburg.swk.extractor.database.entities.ak;

import de.uni.hamburg.swk.extractor.database.entities.Confidence;
import de.uni.hamburg.swk.extractor.database.entities.IndicatorLanguage;
import de.uni.hamburg.swk.extractor.database.entities.IndicatorType;

public class Indicator
{
    private int _id;
    private TechnologyFeature _belongsTo;
    private Confidence _confidence;
    private IndicatorType _type;
    private IndicatorLanguage _indicatorLanguage;
    private String _value;
    private String _parameter;
    private String _scope;

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

    public Confidence getConfidence()
    {
        return _confidence;
    }

    public void setConfidence(Confidence confidence)
    {
        this._confidence = confidence;
    }

    public IndicatorType getType()
    {
        return _type;
    }

    public void setType(IndicatorType type)
    {
        this._type = type;
    }

    public IndicatorLanguage getIndicatorLanguage()
    {
        return _indicatorLanguage;
    }

    public void setIndicatorLanguage(IndicatorLanguage indicatorLanguage)
    {
        this._indicatorLanguage = indicatorLanguage;
    }

    public String getValue()
    {
        return _value;
    }

    public void setValue(String value)
    {
        this._value = value;
    }

    public String getParameter()
    {
        return _parameter;
    }

    public void setParameter(String params)
    {
        this._parameter = params;
    }

    public String getScope()
    {
        return _scope;
    }
    
    public void setScope(String scope)
    {
        this._scope = scope;
    }
}

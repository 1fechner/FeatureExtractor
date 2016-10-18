package de.uni.hamburg.swk.extractor.database.entities.ak;

public class Feature
{
    private int _id;
    private String _name;
    private String _Description;
    private String _association;

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

    public String getDescription()
    {
        return _Description;
    }

    public void setDescription(String description)
    {
        _Description = description;
    }

    public String getAssociation()
    {
        return _association;
    }

    public void setAssociation(String association)
    {
        this._association = association;
    }
}

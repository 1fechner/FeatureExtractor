package de.uni.hamburg.swk.extractor.database.entities.result;

public class Component
{
    private int _id;
    private Project _belongsTo;
    private String _name;
    private String _path;

    public int getId()
    {
        return _id;
    }

    public void setId(int id)
    {
        this._id = id;
    }

    public Project getBelongsTo()
    {
        return _belongsTo;
    }

    public void setBelongsTo(Project belongsTo)
    {
        this._belongsTo = belongsTo;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        this._name = name;
    }

    public String getPath()
    {
        return _path;
    }

    public void setPath(String path)
    {
        this._path = path;
    }
}

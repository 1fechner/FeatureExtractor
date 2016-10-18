package de.uni.hamburg.swk.extractor.database.entities.result;

public class Element
{
    private int _id;
    private Project _project;
    private Element _parent;
    private String _name;
    private String _path;
    private String _package;
    private String _fileType;
    private boolean _scanned;
    private boolean _directory;
    private int _noOfFeatures;
    private int _version;

    public int getId()
    {
        return _id;
    }

    public void setId(int id)
    {
        this._id = id;
    }

    public Project getProject()
    {
        return _project;
    }

    public void setProject(Project project)
    {
        this._project = project;
    }

    public Element getParent()
    {
        return _parent;
    }

    public void setParent(Element _parent)
    {
        this._parent = _parent;
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
    
    public String getPackage()
    {
        return _package;
    }
    
    public void setPackage(String pack)
    {
        _package = pack;
    }

    public String getFileType()
    {
        return _fileType;
    }

    public void setFileType(String fileType)
    {
        this._fileType = fileType;
    }

    public boolean isScanned()
    {
        return _scanned;
    }

    public void setScanned(boolean scanned)
    {
        this._scanned = scanned;
    }

    public boolean isDirectory()
    {
        return _directory;
    }

    public void setDirectory(boolean directory)
    {
        this._directory = directory;
    }

    public int getNoOfFeatures()
    {
        return _noOfFeatures;
    }

    public void setNoOfFeatures(int noOfFeatures)
    {
        this._noOfFeatures = noOfFeatures;
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

package de.uni.hamburg.swk.extractor.database.entities.ak;

public class TechnologySolution
{
    private int _id;
    private TechnologySolution _dependsOn;
    private String _name;
    private String _description;
    private Integer _decisionBuddyRef;
    private boolean _enabled;

    public int getId()
    {
        return _id;
    }

    public void setId(int id)
    {
        this._id = id;
    }

    public TechnologySolution getDependsOn()
    {
        return _dependsOn;
    }

    public void setDependsOn(TechnologySolution dependsOn)
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

    public Integer getDecisionBuddyRef()
    {
        return _decisionBuddyRef;
    }

    public void setDecisionBuddyRef(Integer decisionBuddyRef)
    {
        this._decisionBuddyRef = decisionBuddyRef;
    }

    public boolean isEnabled()
    {
        return _enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this._enabled = enabled;
    }

    /**
     * Print this {@link TechnologySolution} dependencies as a String
     * 
     * @return A String containing this {@link TechnologyFeature}s dependencies
     */
    public String getDependenciesAsString()
    {
        StringBuilder bldr = new StringBuilder();

        bldr.append(_name);

        TechnologySolution it = _dependsOn;

        while (it != null)
        {
            bldr.append(" > ");
            bldr.append(it.getName());

            it = it.getDependsOn();
        }

        return bldr.toString();
    }

    /**
     * Determines if the given {@link TechnologySolution} are in a child-parent
     * relationship.
     * 
     * @param parent The potential parent
     * @return True if parent is a parent, false if not or both
     *         {@link TechnologySolution} are equal
     */
    public boolean isParent(TechnologySolution parent)
    {
        // False if they are the same
        if (this._id == parent.getId())
            return false;

        TechnologySolution it = this._dependsOn;

        // Iterate over all dependencies
        while (it != null)
        {
            // If a common node was found, parent is somewhere in the path
            if (it.getId() == parent.getId())
                return true;

            it = it.getDependsOn();
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return _id;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof TechnologySolution))
            return false;
        if (obj == this)
            return true;

        TechnologySolution sol = (TechnologySolution) obj;
        return sol.getId() == _id;
    }
}

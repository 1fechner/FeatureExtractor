package de.uni.hamburg.swk.extractor.database.dao.impl;

import java.util.List;

import de.uni.hamburg.swk.extractor.database.dao.AbstractDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;

public class TechnologySolutionDAO extends AbstractDAO<TechnologySolution>
{
    private final String QUERY_ALL = "FROM TechnologySolution";
    private final String QUERY_ID = "FROM TechnologySolution WHERE id = %s";
    private final String QUERY_ROOT = "FROM TechnologySolution WHERE dependsOn IS NULL AND Display = 1 ORDER BY name";
    private final String QUERY_NAME = "FROM TechnologySolution WHERE name = '%s'";
    private final String QUERY_CHILDREN = "FROM TechnologySolution WHERE dependsOn = %s AND Display = 1 ORDER BY name";

    @Override
    public List<TechnologySolution> getAll()
    {
        return asList(QUERY_ALL);
    }

    @Override
    public TechnologySolution getById(int id)
    {
        return getOne(String.format(QUERY_ID, id));
    }

    /**
     * Returns all {@link TechnologySolution} without dependencies
     * 
     * @return A list of all {@link TechnologySolution} without dependencies
     */
    public List<TechnologySolution> getTechnologySolutionsRoot()
    {
        return asList(QUERY_ROOT);
    }

    public TechnologySolution getByName(String name)
    {
        List<TechnologySolution> results = asList(String.format(QUERY_NAME, name));

        return results.size() > 0 ? results.get(0) : null;
    }

    /**
     * Returns all dependencies of a {@link TechnologySolution} for the given id
     * 
     * @param id The id of the parent {@link TechnologySolution}
     * @return A list of {@link TechnologySolution}
     */
    public List<TechnologySolution> getTechnologySolutionsChildren(int id)
    {
        return asList(String.format(QUERY_CHILDREN, id));
    }

}

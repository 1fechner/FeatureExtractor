package de.uni.hamburg.swk.extractor.database.dao.impl;

import java.util.List;

import de.uni.hamburg.swk.extractor.database.dao.AbstractDAO;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;

public class ProjectDAO extends AbstractDAO<Project>
{
    private final String QUERY_ALL = "FROM Project";
    private final String QUERY_ID = "FROM Project WHERE id = %s";
    private final String QUERY_NAME = "FROM Project WHERE name = '%s'";

    @Override
    public List<Project> getAll()
    {
        return asList(QUERY_ALL);
    }

    @Override
    public Project getById(int id)
    {
        return getOne(String.format(QUERY_ID, id));
    }

    public Project getByName(String name)
    {
        List<Project> res = asList(String.format(QUERY_NAME, name));

        return res.size() > 0 ? res.get(0) : null;
    }
}

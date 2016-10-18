package de.uni.hamburg.swk.extractor.database.dao.impl;

import java.util.List;

import de.uni.hamburg.swk.extractor.database.dao.AbstractDAO;
import de.uni.hamburg.swk.extractor.database.entities.alternatives.DecisionPoint;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;

public class DecisionPointDAO extends AbstractDAO<DecisionPoint>
{
    private static final String QUERY_ALL = "FROM DecisionPoint";
    private static final String QUERY_ID = "FROM DecisionPoint WHERE id = %s";
    private static final String QUERY_PROJECT = "FROM DecisionPoint WHERE project = %s AND version >= %s ORDER BY dependencyChainLength DESC";
    private static final String QUERY_DELETE_FOR_PROJECT = "DELETE DecisionPoint WHERE project = %s AND version <= %s";

    @Override
    public DecisionPoint getById(int id)
    {
        return getOne(String.format(QUERY_ID, id));
    }

    @Override
    public List<DecisionPoint> getAll()
    {
        return asList(QUERY_ALL);
    }
    
    public List<DecisionPoint> getAllForProject(Project p)
    {
        return asList(String.format(QUERY_PROJECT, p.getId(), p.getVersion()));
    }

    public int deleteForProject(Project project)
    {
        return executeUpdate(String.format(QUERY_DELETE_FOR_PROJECT, project.getId(), project.getVersion()));
    }
}

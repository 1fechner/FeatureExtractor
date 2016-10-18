package de.uni.hamburg.swk.extractor.database.dao.impl;

import java.util.List;

import de.uni.hamburg.swk.extractor.database.dao.AbstractDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;
import de.uni.hamburg.swk.extractor.database.entities.alternatives.Alternative;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;

public class AlternativeDAO extends AbstractDAO<Alternative>
{
    private final String QUERY_ALL = "from Alternative";
    private final String QUERY_ID = "from Alternative WHERE id = %s";
    private final String QUERY_PROJECT = "from Alternative WHERE project = %s AND version >= %s";
    private final String QUERY_TECHNOLOGY = "from Alternative WHERE isAlternativeTo = %s AND project = %s AND version = %s";
    private final String QUERY_DELETE_PROJECT = "DELETE Alternative WHERE project = %s";

    @Override
    public List<Alternative> getAll()
    {
        return asList(QUERY_ALL);
    }
    
    @Override
    public Alternative getById(int id)
    {
        return getOne(String.format(QUERY_ID, id));
    }

    public List<Alternative> getForProject(Project project)
    {
        return asList(String.format(QUERY_PROJECT, project.getId(), project.getVersion()));
    }

    public List<Alternative> getForTechnology(TechnologySolution technology, Project p)
    {
        return asList(String.format(QUERY_TECHNOLOGY, technology.getId(), p.getId(), p.getVersion()));
    }

    public int deleteForProject(int id)
    {
        return executeUpdate(String.format(QUERY_DELETE_PROJECT, id));
    }



}

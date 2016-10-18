package de.uni.hamburg.swk.extractor.database.dao.impl;

import java.util.List;

import de.uni.hamburg.swk.extractor.database.dao.AbstractDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;
import de.uni.hamburg.swk.extractor.database.entities.result.ResultSet;

public class ResultSetDAO extends AbstractDAO<ResultSet>
{
    private final String QUERY_ALL = "FROM ResultSet";
    private final String QUERY_ID = "FROM ResultSet WHERE id = %s";
    private final String QUERY_BELONGS_TO = "FROM ResultSet WHERE BelongsTo = %s ORDER BY technologyFeature, confidence DESC";
    private final String QUERY_PRINT = "FROM ResultSet ORDER BY BelongsTo, Confidence";
    private final String QUERY_GROUP_BY_TECHNOLOGYFEATURE = "FROM ResultSet WHERE project = %s AND version = %s GROUP BY technologyfeature";
    private final String QUERY_BY_PROJECT = "FROM ResultSet WHERE project = %s AND version = %s ORDER BY confidence desc";
    private final String QUERY_DELETE_ALL = "DELETE ResultSet WHERE project = %s";

    @Override
    public List<ResultSet> getAll()
    {
        return asList(QUERY_ALL);
    }

    @Override
    public ResultSet getById(int id)
    {
        return getOne(String.format(QUERY_ID, id));
    }
    
    /**
     * Return all {@link ResultSet} the belong to the given {@link TechnologyFeature}
     * 
     * @param id The id the sets belong to
     * @return A list of {@link ResultSet} for this id
     */
    public List<ResultSet> getByBelongsTo(int id)
    {
        return asList(String.format(QUERY_BELONGS_TO, id));
    }  

    /**
     * Return all {@link ResultSet} to print
     * 
     * @return A list of {@link ResultSet}
     */
    public List<ResultSet> getPrint()
    {
        return asList(QUERY_PRINT);
    }

    /**
     * Returns all {@link ResultSet} for the given {@link Project}, grouped by
     * their {@link TechnologyFeatureDAO}. Can be used to determine all unique
     * {@link TechnologyFeature} found for a {@link Project}
     * 
     * @param project The {@link Project} to search for
     * @return A list of {@link ResultSet}
     */
    public List<ResultSet> getGroupByTechnologyFeature(Project project)
    {
        return asList(String.format(QUERY_GROUP_BY_TECHNOLOGYFEATURE, project.getId(), project.getVersion()));
    }
    
    /**
     * 
     * @param project
     * @return
     */
    public List<ResultSet> getByProject(Project project)
    {
        return asList(String.format(QUERY_BY_PROJECT, project.getId(), project.getVersion()));
    }

    /**
     * Deletes all {@link ResultSet} from the database
     * 
     * @return
     */
    public int deleteAll(Project project)
    {
        return executeUpdate(String.format(QUERY_DELETE_ALL, project.getId()));
    }
}
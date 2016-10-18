package de.uni.hamburg.swk.extractor.database.dao.impl;

import java.util.List;

import de.uni.hamburg.swk.extractor.database.dao.AbstractDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.Feature;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;

public class TechnologyFeatureDAO extends AbstractDAO<TechnologyFeature>
{
    private final String QUERY_ALL = "FROM TechnologyFeature";
    private final String QUERY_ID = "FROM TechnologyFeature WHERE id = %s";
    private final String QUERY_NAME = "FROM TechnologyFeature WHERE name = '%s' AND belongsTo = %s";
    private final String QUERY_ALTERNATIVES = "FROM TechnologyFeature WHERE feature = %s AND id != %s";
    private final String QUERY_BELONGS_TO = "FROM TechnologyFeature WHERE belongsTo = %s";
    private final String QUERY_ROOT = "FROM TechnologyFeature WHERE belongsTo = %s AND dependsOn = NULL ORDER BY name";
    private final String QUERY_CHILDREN = "FROM TechnologyFeature WHERE dependsOn = %s ORDER BY name";
    private final String QUERY_PACKAGE = "FROM TechnologyFeature WHERE id in (SELECT f.technologyFeature FROM ResultSet as f, Element as e WHERE e.package = '%s' AND e.id = f.belongsTo)";

    /**
     * Returns all {@link TechnologyFeature} in the database
     * 
     * @return A list containing all {@link TechnologyFeature} stored in the
     *         database
     */
    public List<TechnologyFeature> getAll()
    {
        return asList(QUERY_ALL);
    }    

    @Override
    public TechnologyFeature getById(int id)
    {
        return getOne(String.format(QUERY_ID, id));
    }
    
    public TechnologyFeature getByName(String name, int parentId)
    {
        return getOne(String.format(QUERY_NAME, name, parentId));
    }

    /**
     * Returns all possible alternative {@link TechnologyFeature} for the given
     * {@link TechnologyFeature}. These alternatives implement the same
     * {@link Feature}
     * 
     * @param technologyFeature The {@link TechnologyFeature} to search
     *            alternatives for
     * @return A list of all alternatives for the given
     *         {@link TechnologyFeature}. Empty if there are no alternatives or
     *         the {@link TechnologyFeature} does not implement a
     *         {@link Feature}
     */
    public List<TechnologyFeature> getAlternativesFor(TechnologyFeature technologyFeature)
    {
        return asList(String.format(QUERY_ALTERNATIVES, technologyFeature.getFeatureImplemented().getId(),
                technologyFeature.getId()));
    }

    /**
     * Returns all {@link TechnologyFeature} associated with the
     * {@link TechnologySolution} with the given id
     * 
     * @param id The id of the {@link TechnologySolution} the
     *            {@link TechnologyFeature} belong to
     * @return A list of {@link TechnologyFeature} belonging to the
     *         {@link TechnologySolution}. Empty if there are none
     */
    public List<TechnologyFeature> getByBelongsTo(int id)
    {
        return asList(String.format(QUERY_BELONGS_TO, id));
    }

    /**
     * Returns all {@link TechnologyFeature} without a dependency
     * 
     * @param id The id of the {@link TechnologyFeature} to search for
     * @return A list of alle {@link TechnologyFeature} without dependencies,
     *         empty if there are none
     */
    public List<TechnologyFeature> getRootFor(int id)
    {
        return asList(String.format(QUERY_ROOT, id));
    }

    /**
     * Returns all children for the {@link TechnologyFeature} with the given id
     * 
     * @param id The id of the parent {@link TechnologyFeature} whose children
     *            to search for
     * @return A list of {@link TechnologyFeature} with a direct dependency to
     *         the given feature. Empty if there are none
     */
    public List<TechnologyFeature> getChidrenFor(int id)
    {
        return asList(String.format(QUERY_CHILDREN, id));
    }
    
    public List<TechnologyFeature> getForPackage(String packageName)
    {
        return asList(String.format(QUERY_PACKAGE, packageName));
    }
}

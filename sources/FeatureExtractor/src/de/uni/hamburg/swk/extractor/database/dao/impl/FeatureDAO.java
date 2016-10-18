package de.uni.hamburg.swk.extractor.database.dao.impl;

import java.util.List;

import de.uni.hamburg.swk.extractor.database.dao.AbstractDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.Feature;

public class FeatureDAO extends AbstractDAO<Feature>
{
    private final static String QUERY_ALL = "FROM Feature ORDER BY association, name desc";
    private final static String QUERY_ID = "FROM Feature WHERE id = %s";
    private final static String QUERY_NAME = "FROM Feature WHERE name = '%s'";

    @Override
    public Feature getById(int id)
    {
        return getOne(String.format(QUERY_ID, id));
    }

    @Override
    public List<Feature> getAll()
    {
        return asList(QUERY_ALL);
    }

    public Feature getByName(String name)
    {
        return getOne(String.format(QUERY_NAME, name));
    }
}
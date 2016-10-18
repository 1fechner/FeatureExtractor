package de.uni.hamburg.swk.extractor.database.dao.impl;

import java.util.List;

import de.uni.hamburg.swk.extractor.database.dao.AbstractDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.ASTA;

public class ASTADAO extends AbstractDAO<ASTA>
{
    private final String QUERY_ALL = "FROM ASTA";
    private final String QUERY_ID = "FROM ASTA WHERE id = %s";
    private final String QUERY_FEATURE = "FROM ASTA WHERE Feature = %s";
    private final String QUERY_TECHNOLOGY_FEATURE = "FROM ASTA WHERE belongsTo = %s ORDER BY type DESC";
    private final String QUERY_TECHNOLOGY_AND_FEATURE = "FROM ASTA WHERE belongsTo IN (SELECT t.id FROM TechnologyFeature as t WHERE t.featureImplemented = %s AND t.belongsTo = %s) ORDER BY BelongsTo ASC";

    @Override
    public List<ASTA> getAll()
    {
        return asList(QUERY_ALL);
    }

    @Override
    public ASTA getById(int id)
    {
        return getOne(String.format(QUERY_ID, id));
    }

    public List<ASTA> getByFeature(int featureID)
    {
        return asList(String.format(QUERY_FEATURE, featureID));
    }
    
    public List<ASTA> getByTechnologyFeature(int technologyFeatureID)
    {
        return asList(String.format(QUERY_TECHNOLOGY_FEATURE, technologyFeatureID));
    }
    
    public List<ASTA> getByTechnologyAndFeature(int technologyID, int featureID)
    {
        return asList(String.format(QUERY_TECHNOLOGY_AND_FEATURE, featureID, technologyID));
    }
}

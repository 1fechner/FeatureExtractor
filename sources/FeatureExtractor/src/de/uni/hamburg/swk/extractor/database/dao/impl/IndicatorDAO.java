package de.uni.hamburg.swk.extractor.database.dao.impl;

import java.util.List;

import de.uni.hamburg.swk.extractor.database.dao.AbstractDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;

public class IndicatorDAO extends AbstractDAO<Indicator>
{
    private final String QUERY_ALL = "FROM Indicator";
    private final String QUERY_ID = "FROM Indicator WHERE id = %s";
    private final String QUERY_FOR_FEATURE = "FROM Indicator WHERE belongsTo = %s";

    @Override
    public List<Indicator> getAll()
    {
        return asList(QUERY_ALL);
    }
    

    @Override
    public Indicator getById(int id)
    {
        return getOne(String.format(QUERY_ID, id));
    }

    /**
     * Returns a list of all {@link Indicator} for the given
     * {@link TechnologyFeature}.
     * 
     * @param feature The {@link TechnologyFeature} who's {@link Indicator} are
     *            to be returned
     * @return A list of indicators for the given {@link TechnologyFeature}.
     *         Empty if there are no {@link Indicator}
     */
    public List<Indicator> getIndicatorsForFeature(int id)
    {
        return asList(String.format(QUERY_FOR_FEATURE, id));
    }

}

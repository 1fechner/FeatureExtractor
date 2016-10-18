package de.uni.hamburg.swk.extractor.service.extraction.sub.element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uni.hamburg.swk.extractor.database.dao.impl.IndicatorDAO;
import de.uni.hamburg.swk.extractor.database.dao.impl.TechnologyFeatureDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;
import de.uni.hamburg.swk.extractor.database.entities.result.Element;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;
import de.uni.hamburg.swk.extractor.service.Service;

public abstract class AbstractElementScanService extends Service
{
    private TechnologyFeatureDAO _technologyFeatureDAO;
    private IndicatorDAO _indicatorDAO;

    protected Map<TechnologyFeature, List<Indicator>> _featureIndicatorMap;
    protected Map<String, Integer> _distFeatures = new HashMap<String, Integer>();

    public AbstractElementScanService()
    {
        _technologyFeatureDAO = new TechnologyFeatureDAO();
        _indicatorDAO = new IndicatorDAO();
        prepare();
    }

    /**
     * Prepare the service. Opens a session to the db and prefetches all
     * {@link TechnologyFeature}s and their corresponding {@link Indicator} form
     * the db
     */
    private void prepare()
    {
        _featureIndicatorMap = new HashMap<TechnologyFeature, List<Indicator>>();

        List<TechnologyFeature> features = _technologyFeatureDAO.getAll();

        for (TechnologyFeature f : features)
        {
            List<Indicator> indicators = _indicatorDAO.getIndicatorsForFeature(f.getId());

            // Do not add features without indicator to list, this will reduce
            // the complexity of the search, avoiding unnecessary iterations
            if (!indicators.isEmpty())
                _featureIndicatorMap.put(f, indicators);
        }

        print(String.format("Loaded %s Features from AK", _featureIndicatorMap.size()));
    }
    
    public abstract void scan(Project project, List<Element> elementsToScan);
}

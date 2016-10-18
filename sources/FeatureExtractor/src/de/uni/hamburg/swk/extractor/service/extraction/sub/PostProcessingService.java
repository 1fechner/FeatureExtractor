package de.uni.hamburg.swk.extractor.service.extraction.sub;

import java.util.List;

import org.hibernate.Transaction;

import de.uni.hamburg.swk.extractor.database.SessionService;
import de.uni.hamburg.swk.extractor.database.dao.impl.ElementDAO;
import de.uni.hamburg.swk.extractor.database.dao.impl.ResultSetDAO;
import de.uni.hamburg.swk.extractor.database.entities.result.Element;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;
import de.uni.hamburg.swk.extractor.database.entities.result.ResultSet;
import de.uni.hamburg.swk.extractor.gui.callback.CallbackRegistry;
import de.uni.hamburg.swk.extractor.gui.callback.CallbackType;
import de.uni.hamburg.swk.extractor.service.Service;

public class PostProcessingService extends Service
{
    private ResultSetDAO _resultsetDAO;
    private ElementDAO _elementDAO;

    public PostProcessingService()
    {
        _resultsetDAO = new ResultSetDAO();
        _elementDAO = new ElementDAO();
    }

    public void run(Project project)
    {
        long startTime = System.currentTimeMillis();

        Transaction tx = SessionService.getCurrentSession().beginTransaction();
        aggregateResults(project);
        tx.commit();

        long estimatedTime = System.currentTimeMillis() - startTime;

        print(String.format("Postprocessing took %s ms", estimatedTime / 1000d, estimatedTime));
    }

    private void aggregateResults(Project project)
    {
        print("Aggregating results...");

        print("Determining total of features extracted...");
        CallbackRegistry.invoke(CallbackType.STATUS, new Object[] { "Determining total of features extracted..." },
                PostProcessingService.class);

        List<Element> elements = _elementDAO.getByProject(project);

        int total = elements.size();
        int processed = 0;

        for (Element e : elements)
        {
            List<ResultSet> results = _resultsetDAO.getByBelongsTo(e.getId());

            e.setNoOfFeatures(results.size());
            SessionService.getCurrentSession().save(e);

            processed += 1;

            CallbackRegistry.invoke(CallbackType.PROGRESS, new Object[] { processed, total },
                    PostProcessingService.class);
        }

        List<Element> elementsToScan = _elementDAO.getElementsToScan(project);

        total = elementsToScan.size();
        processed = 0;

        print("Aggregating features to packages...");
        CallbackRegistry.invoke(CallbackType.STATUS, new Object[] { "Aggregating features to packages..." },
                PostProcessingService.class);

        for (Element e : elementsToScan)
        {
            Element parent = e.getParent();

            while (parent != null)
            {
                parent.setNoOfFeatures(parent.getNoOfFeatures() + e.getNoOfFeatures());

                SessionService.getCurrentSession().save(parent);

                copyResultSets(e, parent);

                SessionService.getCurrentSession().save(parent);

                parent = parent.getParent();
            }

            processed += 1;

            CallbackRegistry.invoke(CallbackType.PROGRESS, new Object[] { processed, total },
                    PostProcessingService.class);
        }

        CallbackRegistry.invoke(CallbackType.STATUS, new Object[] { "Done!" }, PostProcessingService.class);
        print("Done");
    }

    private void copyResultSets(Element from, Element to)
    {
        for (ResultSet r : _resultsetDAO.getByBelongsTo(from.getId()))
        {
            ResultSet n = new ResultSet();

            n.setBelongsTo(to);
            n.setConfidence(r.getConfidence());
            n.setProject(r.getProject());
            n.setTechnologyFeature(r.getTechnologyFeature());
            n.setVersion(r.getVersion());
            SessionService.getCurrentSession().save(n);
        }
    }
}

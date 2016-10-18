package de.uni.hamburg.swk.extractor.service.extraction;

import java.util.Date;
import java.util.List;

import org.hibernate.Transaction;

import de.uni.hamburg.swk.extractor.configuration.OptimizationRules;
import de.uni.hamburg.swk.extractor.database.SessionService;
import de.uni.hamburg.swk.extractor.database.dao.impl.ElementDAO;
import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;
import de.uni.hamburg.swk.extractor.database.entities.result.Element;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;
import de.uni.hamburg.swk.extractor.service.Service;
import de.uni.hamburg.swk.extractor.service.extraction.sub.ConcurrentElementScanService;
import de.uni.hamburg.swk.extractor.service.extraction.sub.LinearElementScanService;
import de.uni.hamburg.swk.extractor.service.extraction.sub.PostProcessingService;
import de.uni.hamburg.swk.extractor.service.extraction.sub.PreScanService;
import de.uni.hamburg.swk.extractor.service.extraction.sub.element.AbstractElementScanService;
import de.uni.hamburg.swk.extractor.utils.Messages;

public class ExtractionService extends Service
{
    private ElementDAO _elementDAO;

    private PreScanService _prescanService;
    private AbstractElementScanService _elementScanService;
    private PostProcessingService _postprocessingService;

    public ExtractionService()
    {
        _elementDAO = new ElementDAO();

        _prescanService = new PreScanService();
        _postprocessingService = new PostProcessingService();
    }

    /**
     * Extract feature from the given path.
     * 
     * @param path The source directory to scan
     */
    public void extract(Project project)
    {
        if (project == null)
        {
            print("No Project selected. Please open a project first");
            return;
        }

        // Initialize service e.g. setting up DB, prefetch TechnologyFeatures
        print("Preparing scan...");

        _prescanService.run(project);
        // PreScanService.run(project);

        print("Starting scan at " + new Date());

        long startTime = System.currentTimeMillis();
        // Perform the scan for features
        scanProject(project);
        long estimatedTime = System.currentTimeMillis() - startTime;

        print(String.format(Messages.SCAN_TIME, estimatedTime / 1000d, estimatedTime));

        print("Finished scan at " + new Date());

        print("PostProcessing...");

        _postprocessingService.run(project);
    }

    /**
     * This method implements the actual extraction of features.<br>
     * Previously scanned file are processed line by line and checked for every
     * {@link TechnologyFeature}'s {@link Indicator} <br>
     * Assumes that a prescan was run before and elements to be scanned exist in
     * the database
     * 
     * @param toScan A list of components to scan.
     */
    private void scanProject(Project p)
    {
        print(String.format("Project: %s (%s)", p.getName(), p.getId()));

        Transaction tx = SessionService.getCurrentSession().beginTransaction();

        List<Element> el = _elementDAO.getElementsToScan(p);

        if (OptimizationRules.R_203_CONCURENT_PROCESSING)
        {
            _elementScanService = new ConcurrentElementScanService();
        }
        else
        {
            _elementScanService = new LinearElementScanService();
        }

        _elementScanService.scan(p, el);

        tx.commit();
    }

}
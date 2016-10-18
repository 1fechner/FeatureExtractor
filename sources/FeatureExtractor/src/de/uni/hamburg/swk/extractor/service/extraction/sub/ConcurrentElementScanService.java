package de.uni.hamburg.swk.extractor.service.extraction.sub;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.configuration.OptimizationRules;
import de.uni.hamburg.swk.extractor.database.SessionService;
import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;
import de.uni.hamburg.swk.extractor.database.entities.result.Element;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;
import de.uni.hamburg.swk.extractor.database.entities.result.ResultSet;
import de.uni.hamburg.swk.extractor.service.extraction.Rule;
import de.uni.hamburg.swk.extractor.service.extraction.RuleFactory;
import de.uni.hamburg.swk.extractor.service.extraction.sub.element.AbstractElementScanService;
import de.uni.hamburg.swk.extractor.utils.Constants;
import de.uni.hamburg.swk.extractor.utils.Messages;

public class ConcurrentElementScanService extends AbstractElementScanService implements Runnable
{
    private Project _project;
    private List<Element> _elementsToScan;
    private Map<TechnologyFeature, List<Indicator>> _featureIndicatorMap;
    private CountDownLatch _latch;

    @Override
    public void run()
    {
        for (Element e : _elementsToScan)
        {
            scan(e);
        }

        _latch.countDown();
    }

    public void scan(Project project, List<Element> elements)
    {
        int s = elements.size();

        print("Total no of elements to process: " + elements.size());

        CountDownLatch latch = new CountDownLatch(Constants.NO_THREADS);

        int[] indices = new int[Constants.NO_THREADS];

        while (s > 0)
        {
            for (int j = 0; j < Constants.NO_THREADS; ++j)
            {
                indices[j] += 1;
                s -= 1;

                if (s <= 0)
                    break;
            }
        }

        int offset = 0;

        for (int i = 0; i < Constants.NO_THREADS; ++i)
        {
            List<Element> elementsPart = new ArrayList<Element>();

            for (int j = offset; j < offset + indices[i]; ++j)
            {
                elementsPart.add(elements.get(j));
            }

            offset += indices[i];

            //TODO implement a runner to do the work, concurrent processing is not implemented
            ConcurrentElementScanService elementScanner = new ConcurrentElementScanService();

            Thread t = new Thread(elementScanner);
            t.setName("FeatureExtractor Thread " + i);
            t.start();
            print(String.format("Starting Thread '%s' (ID %s). Processing %s elements", t.getName(), t.getId(),
                    elementsPart.size()));
        }

        try
        {
            print("Waiting for all threads to finish...");
            latch.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private void scan(Element element)
    {
        int lineCounter = 1;
        List<String> lines = FilePreparationService.readFile(element.getPath());

        if (Configuration.isVerbose())
            System.out.println(String.format(Messages.SCAN_SCANNING, element.getName()));

        // Check each technology feature
        for (TechnologyFeature t : _featureIndicatorMap.keySet())
        {
            if (Configuration.isVerbose())
                System.out.println(String.format(Messages.SCAN_SCANNING_FEATURE, t.getName()));

            // If this feature does not have any indicators, skip it
            // (Nothing to find anyway)
            if (OptimizationRules.R_200_IGNORE_FEATURES_WITHOUT_INDICATORS && _featureIndicatorMap.get(t).isEmpty())
            {
                if (Configuration.isVerbose())
                    System.out.println(String.format(Messages.SCAN_SCANNING_NO_INDICATORS, t.getName()));
                continue;
            }

            // Define variables to calculate confidence
            int noIndicatorsFound = 0;
            Set<Integer> distIndicatorsFound = new HashSet<Integer>();
            float sum = 0.0f;

            lineCounter = 1;
            for (String s : lines)
            {
                // Scan
                for (Indicator indicator : _featureIndicatorMap.get(t))
                {
                    // Get rule for indicator type
                    Rule r = RuleFactory.getRuleFor(indicator.getType(), indicator.getIndicatorLanguage());

                    // Check if line matches the rule
                    if (r.check(s, indicator))
                    {
                        // Count this indicator for confidence
                        noIndicatorsFound += 1;
                        distIndicatorsFound.add(indicator.getId());

                        if (Configuration.isVerbose())
                            System.out.println(String.format(Messages.SCAN_SCANNING_INDICATOR_FOUND, indicator.getId(),
                                    indicator.getValue(), indicator.getType(), element.getName(), lineCounter));

                        // Add value for this indicator to sum. Weight from type
                        // is more relevant
                        sum += ((2 * indicator.getType().getValue() + indicator.getConfidence().getValue()) / 3);
                    }
                }

                lineCounter++;
            }

            // Divide by the number of indicators found to limit the value to 1
            sum = sum / noIndicatorsFound;

            // Multiply by percentage of feature found (e.g. 3/5 indicators
            // of feature found -> multiply by .6
            float confidence = sum * ((float) distIndicatorsFound.size() / (float) _featureIndicatorMap.get(t).size());

            // Only store result if something was found (at least one indicator)
            if (confidence > Configuration.getSelectedProject().getMinConfidence())
            {
                // Insert a new ResultSet
                ResultSet r = new ResultSet(t, element, confidence, _project);
                SessionService.getCurrentSession().save(r);

                if (Configuration.isVerbose())
                {
                    System.out.println(
                            String.format(Messages.SCAN_SCANNING_FOUND, Configuration.isVerbose() ? "\t\t  " : "",
                                    t.getName(), t.getBelongsTo().getName(), element.getName()));
                }
            }
        }

        // Store the results
        element.setScanned(true);
        SessionService.getCurrentSession().save(element);
    }

}

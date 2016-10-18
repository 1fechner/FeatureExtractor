package de.uni.hamburg.swk.extractor.service.extraction.sub;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.uni.hamburg.swk.extractor.configuration.Configuration;
import de.uni.hamburg.swk.extractor.configuration.OptimizationRules;
import de.uni.hamburg.swk.extractor.database.SessionService;
import de.uni.hamburg.swk.extractor.database.entities.ak.Indicator;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologyFeature;
import de.uni.hamburg.swk.extractor.database.entities.result.Element;
import de.uni.hamburg.swk.extractor.database.entities.result.Project;
import de.uni.hamburg.swk.extractor.database.entities.result.ResultSet;
import de.uni.hamburg.swk.extractor.gui.callback.CallbackRegistry;
import de.uni.hamburg.swk.extractor.gui.callback.CallbackType;
import de.uni.hamburg.swk.extractor.service.export.CSVExportService;
import de.uni.hamburg.swk.extractor.service.extraction.ExtractionService;
import de.uni.hamburg.swk.extractor.service.extraction.Rule;
import de.uni.hamburg.swk.extractor.service.extraction.RuleFactory;
import de.uni.hamburg.swk.extractor.service.extraction.sub.element.AbstractElementScanService;
import de.uni.hamburg.swk.extractor.utils.Constants;
import de.uni.hamburg.swk.extractor.utils.Messages;

public class LinearElementScanService extends AbstractElementScanService
{
    public LinearElementScanService()
    {
        super();
    }

    public void scan(Project p, List<Element> el)
    {
        print(String.format("Scanning %s elements", el.size()));
        int cnt = 0;

        CallbackRegistry.invoke(CallbackType.STATUS, new Object[] { "Processing..." }, ExtractionService.class);
        print("Processing...");

        float progress = 0.0f;
        
        if (OptimizationRules.R_204_EXPORT_RESULTS_CSV)
            CSVExportService.buffer("Package;File;Feature;Technology;Confidence");

        for (Element e : el)
        {
            scanElement(p, e);

            cnt += 1;
            progress = ((float) cnt / el.size()) * 100;

            // callback.exec();

            CallbackRegistry.invoke(CallbackType.PROGRESS, new Object[] { cnt, el.size() }, ExtractionService.class);

            if (OptimizationRules.R_000_GUI)
            {
                String status = String.format("Scanning '%s'...", e.getName());
                CallbackRegistry.invoke(CallbackType.STATUS, new Object[] { status }, ExtractionService.class);
                print(status);
            }
            else
            {
                if (el.size() < Constants.EXTRACTION_PRINT_CAP)
                    print(String.format("%s of %s Elements (%.2f%%) [%s] [%s]", cnt, el.size(), progress, e.getName(),
                            e.getPath()));
                else if (cnt % 5 == 0)
                    print(String.format("%s of %s Elements (%.2f%%)", cnt, el.size(), progress));
            }
        }
        
        if (OptimizationRules.R_204_EXPORT_RESULTS_CSV)
            CSVExportService.export();

        CallbackRegistry.invoke(CallbackType.STATUS, new Object[] { "Done" }, ExtractionService.class);
    }

    /**
     * Scans an element (A file) for all indicators
     * 
     * @param element The element to be scanned
     */
    private void scanElement(Project project, Element element)
    {
        int lineCounter = 1;
        List<String> lines = FilePreparationService.readFile(element.getPath());

        if (Configuration.isVerbose())
            print(String.format(Messages.SCAN_SCANNING, element.getName()));

        // Check each technology feature
        for (TechnologyFeature t : _featureIndicatorMap.keySet())
        {
            if (Configuration.isVerbose())
                print(String.format(Messages.SCAN_SCANNING_FEATURE, t.getName()));

            // If this feature does not have any indicators, skip it
            // (Nothing to find anyway)
            if (OptimizationRules.R_200_IGNORE_FEATURES_WITHOUT_INDICATORS && _featureIndicatorMap.get(t).isEmpty())
            {
                if (Configuration.isVerbose())
                    print(String.format(Messages.SCAN_SCANNING_NO_INDICATORS, t.getName()));
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
                            print(String.format(Messages.SCAN_SCANNING_INDICATOR_FOUND, indicator.getId(),
                                    indicator.getValue(), indicator.getType(), element.getName(), lineCounter, s));

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
                ResultSet r = new ResultSet(t, element, confidence, project);
                SessionService.getCurrentSession().save(r);

                if (Configuration.isVerbose())
                {
                    print(String.format(Messages.SCAN_SCANNING_FOUND, Configuration.isVerbose() ? "\t\t  " : "",
                            t.getName(), t.getBelongsTo().getName(), element.getName()));
                }

                if (OptimizationRules.R_204_EXPORT_RESULTS_CSV)
                {
                    CSVExportService.buffer(String.format("%s;%s;%s;%s;%s", element.getPackage(), element.getName(),
                            t.getName(), t.getBelongsTo().getName(), r.getConfidence()));
                }

                String name = String.format("%s (of '%s')", t.getName(), t.getBelongsTo().getName());

                // _noFeaturesFound += 1;

                if (!_distFeatures.containsKey(name))
                    _distFeatures.put(name, 1);
                else
                    _distFeatures.put(name, _distFeatures.get(name) + 1);
            }
        }

        // Store the results
        element.setScanned(true);
        SessionService.getCurrentSession().save(element);
    }
}

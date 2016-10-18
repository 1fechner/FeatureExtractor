package de.uni.hamburg.swk.extractor.service.alternatives;

import java.util.Comparator;
import java.util.List;

import de.uni.hamburg.swk.extractor.database.entities.ak.Feature;
import de.uni.hamburg.swk.extractor.database.entities.ak.TechnologySolution;
import de.uni.hamburg.swk.extractor.database.entities.alternatives.Alternative;

public class CoverageMatrixService
{
    public static void printFeatureCoverageMatrixFor(TechnologySolution technologySolution, List<Feature> features,
            List<Alternative> alternatives)
    {

        final Comparator<Feature> comp = (p1, p2) -> Integer.compare(p1.getName().length(), p2.getName().length());
        int maxWidth = features.stream().max(comp).get().getName().length();

        StringBuilder divider = new StringBuilder();

        divider.append(String.format("+ %1$" + maxWidth + "s-+", " ").replace(" ", "-"));

        String firstHalfLine = String.format("+     + %1$" + (maxWidth - 6) + "s +", "").replace(" ", "-");
        String secondHalfLine = firstHalfLine;

        for (int i = 0; i < alternatives.size(); ++i)
        {
            divider.append("-----+");
            secondHalfLine += "-----+";
        }

        StringBuilder bldrHead1 = new StringBuilder();
        StringBuilder bldrHead2 = new StringBuilder();
        bldrHead1.append(
                String.format("| %1$-" + maxWidth + "s |", "Alternatives for: " + technologySolution.getName()));
        bldrHead2.append(
                String.format("| %1$-" + maxWidth + "s |", String.format("Considering %s features", features.size())));

        System.out.println(firstHalfLine);

        for (int i = 0; i < alternatives.size(); ++i)
        {
            Alternative a = alternatives.get(i);

            System.out.println(String.format("| %1$-" + maxWidth + "s |",
                    String.format("%03d | %s", i, a.getTechnology().getName())));

            bldrHead1.append(String.format(" %03d ", i));
            bldrHead1.append("|");
            bldrHead2.append(String.format("%.3f", a.getCoverage()));
            bldrHead2.append("|");
        }

        System.out.println(secondHalfLine);

        System.out.println(bldrHead1);
        System.out.println(bldrHead2);
        System.out.println(divider);

        for (int i = 0; i < features.size(); ++i)
        {
            Feature f = features.get(i);

            StringBuilder bldrLine = new StringBuilder();

            bldrLine.append(String.format("| %1$-" + maxWidth + "s |", f.getName()));

            for (Alternative a : alternatives)
            {
                bldrLine.append(a.getFeaturesImplemeted().contains(f) ? "  X  " : "     ");
                bldrLine.append("|");
            }

            System.out.println(bldrLine);
        }

        System.out.println(divider);
        System.out.println("");
    }
}

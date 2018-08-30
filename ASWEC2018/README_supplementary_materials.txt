ASWEC 2018 - Supplementary Materials

We analyzed 5 different applications to identify features of the object-relational mapping tool Hibernate and the Java Persistence API (JPA).

The results for each tool are stored in CSV files, which are are located in the separate folders.

In the following we describe the content of the folder "Broadleaf (Common)".
There are 7 CSV-Files


1) broadleaf_(common)-Fri Aug 12 13/24/18 CEST 2016-000.csv

The file contains the following information.

- Package: Name of the package
- File: Name of the file
- Feature: Name of the feature, which has been identified
- Technology: Name of the technology the feature belongs to
- Confidence: Calculated confidence level. Further details see file 7)

The last column gives information about the usage of a feature.
If there is a N, then the feature is not in use.
If there is a Y, then the feature is in use.


--------------

2) - 6)
broadleaf_(common)-Fri Aug 12 13/24/18 CEST 2016-025.csv
broadleaf_(common)-Fri Aug 12 13/24/18 CEST 2016-027.csv
broadleaf_(common)-Fri Aug 12 13/24/18 CEST 2016-050.csv
broadleaf_(common)-Fri Aug 12 13/24/18 CEST 2016-075.csv
broadleaf_(common)-Fri Aug 12 13/24/18 CEST 2016-100.csv

Same like file number 1), but only showing the features with an confidence level same or equal to 25, 27, 50, 75, 100 


--------------

broadleaf_(common)-Sat Apr 07 11/35/58 CEST 2018-100.csv

This CSV file contains more details regarding the indicators.

- Package: Name of the package
- File: Name of the file
- Feature: Name of the feature, which has been identified
- Technology: Name of the technology the feature belongs to
- No. indicators found: Total number of indicators, which have been identified 
- Indicator Type: Category of the indicator like Import, Annotation, etc.
- Confidence: Confidence level of the indicators types or overall confidence level
    

    Confidence level of the indicators types:
    -- Import 0.3
    -- TypeUsage 0.9
    -- MethodCall 0.8
    -- Inheritance 0.6
    -- InterfaceImplementation 0.6
    -- Annotation 0.9
    -- Keyword 0.4
    -- RegEx 0.4
    
  Overall confidence level, which is calculated from:

    -- I: Confidence level of each indicator type listed above
    -- C: Individual confidence level for a specific indicator, currently unused and always 1
    -- n: Total number of identified indicators
    -- v: Total number of identified and different indicators
    -- i: Total number of defined and unique indicators of a feature

  The formula to calculate the confidence level: (SUM((2I+C)/3)/n * v/i


During the experiments we observed that the precision and recall has the best results if we count features with a confidence level starting from 0.5. 

The example, which is mentioned in the paper, is based on broadleaf_(common)-Fri Aug 12 13/24/18 CEST 2016-050.csv
- 735 files
- 27 different Hibernate features
- 236 true positives
- 0 false positives
- 3 false negatives
- precision 1.000
- recall of 0.986


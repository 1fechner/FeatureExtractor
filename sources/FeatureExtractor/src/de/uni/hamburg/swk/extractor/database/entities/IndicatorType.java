package de.uni.hamburg.swk.extractor.database.entities;

public enum IndicatorType
{
    // Concrete values differ from theoretical ones.
    // This seems to be due to the relevant values in ak
    Undefined(0.0f), // 0
    Implicit(0.2f), // 1
    SimpleContain(0.4f), // 2
    SimpleMatch(0.4f), // 3
    Importing(0.3f), // 4
    Inheriting(0.6f), // 5
    Implementing(0.6f), // 6
    TypeUsage(0.9f), // 7
    Calling(0.8f), // 8
    CallingParams(0.7f), // 9
    Annotation(0.9f), // 10
    Tag(0.5f); // 11

    private final float _confidence;

    private IndicatorType(float confidence)
    {
        _confidence = confidence;
    }

    public float getValue()
    {
        return _confidence;
    }
}
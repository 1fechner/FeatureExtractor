package de.uni.hamburg.swk.extractor.database.entities;

/**
 * Indicates how confident the extraction of a corresponding indicator some.
 * Some might cause the system to be more save about whether a feature was found
 * than others
 * 
 * @author tobias
 *
 */
public enum Confidence
{
    VeryLow(0.1f), Low(0.25f), Average(0.5f), High(0.75f), VeryHigh(1.0f);

    private final float _value;

    private Confidence(float value)
    {
        _value = value;
    }

    public float getValue()
    {
        return _value;
    }
}

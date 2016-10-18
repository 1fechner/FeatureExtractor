package de.uni.hamburg.swk.extractor.startup.mode;

/**
 * Abstract base class for each of the program's operating modes
 * 
 * @author tobias
 *
 */
public abstract class Mode
{
    /**
     * Execute the mode
     * 
     * @param args The CLI arguments given
     */
    public abstract void execute(String[] args);
}

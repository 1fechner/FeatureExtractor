package de.uni.hamburg.swk.extractor.gui.callback;

import java.util.HashMap;
import java.util.Map;

import de.uni.hamburg.swk.extractor.gui.callback.impl.CallbackOutput;
import de.uni.hamburg.swk.extractor.gui.callback.impl.CallbackProgress;
import de.uni.hamburg.swk.extractor.gui.callback.impl.CallbackSetProjectInfo;
import de.uni.hamburg.swk.extractor.gui.callback.impl.CallbackStatus;
import de.uni.hamburg.swk.extractor.gui.callback.impl.CallbackWorkFinished;

/**
 * A registry to create some kind of Event Bus Java does not provide per se.
 * <br>
 * {@link Callback}s can be registered at runtime. They are anonymously invoked,
 * according to their type an who send the invoke
 * 
 * @author tobias
 *
 */
@SuppressWarnings("rawtypes")
public class CallbackRegistry
{
    private static Map<Callback, Class> _callbacks = new HashMap<Callback, Class>();

    /**
     * Registers a {@link Callback} to the registry. <br>
     * The {@link Callback} cannot be invoked directly, but only by its type.
     * <br>
     * If a {@link Callback} is inserted again, the previous registration is
     * overwritten.
     * 
     * @param callback The {@link Callback} to be registered
     * @param sender The type of the sender
     */
    public static void register(Callback callback, Class sender)
    {
        _callbacks.put(callback, sender);
    }

    /**
     * Invokes all {@link Callback}s of the given type, send by an instance of
     * sender. <br>
     * {@link Callback}s of any type other than {@link CallbackType}.ANY are
     * invoked only if invoke is called with the respective class type
     * 
     * @param type The type to be invoked
     * @param args The args to be directed to the execution of the
     *            {@link Callback}
     * @param sender The sender
     */
    public static void invoke(CallbackType type, Object[] args, Class sender)
    {
        switch (type)
        {
            case OUTPUT:
                for (Callback c : _callbacks.keySet())
                {
                    if (c instanceof CallbackOutput && _callbacks.get(c) == sender)
                        c.exec(args);
                }
                break;
            case PROGRESS:
                for (Callback c : _callbacks.keySet())
                {
                    if (c instanceof CallbackProgress && _callbacks.get(c) == sender)
                        c.exec(args);
                }
                break;
            case STATUS:
                for (Callback c : _callbacks.keySet())
                {
                    if (c instanceof CallbackStatus && _callbacks.get(c) == sender)
                        c.exec(args);
                }
                break;
            case WORK_FINISHED:
                for (Callback c : _callbacks.keySet())
                {
                    if (c instanceof CallbackWorkFinished && _callbacks.get(c) == sender)
                        c.exec(args);
                }
                break;
            case PROJECT_SELECTED:
                for (Callback c : _callbacks.keySet())
                {
                    if (c instanceof CallbackSetProjectInfo && _callbacks.get(c) == sender)
                        c.exec(args);
                }
                break;
            case ANY:
            default:
                for (Callback c : _callbacks.keySet())
                {
                    c.exec(args);
                }
                break;
        }
    }

    /**
     * De-registers all {@link Callback}s in the registry
     */
    public static void clear()
    {
        _callbacks.clear();
    }
}

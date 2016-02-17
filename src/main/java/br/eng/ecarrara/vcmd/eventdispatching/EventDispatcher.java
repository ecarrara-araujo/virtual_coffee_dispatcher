package br.eng.ecarrara.vcmd.eventdispatching;

/**
 * Interface definition for an Event Dispatching mechanism.
 * The implementing class must worry about how the events will be stored, organized and processed.
 *
 */
public interface EventDispatcher {

    boolean PROCESSING_EVENTS = true;
    boolean NOT_PROCESSING_EVENTS = false;

    /**
     * Add an {@link Event} to be processed by this Dispatcher.
     * @param event
     *
     */
    void post(Event event);

    /**
     * The processing logic of this EventDispatcher that will be called for all the {@link Event}s
     * posted using post.
     * @param event
     */
    void dispatch(Event event);

    void startProcessingEvents();
    void stopProcessingEvents();
    boolean isProcessingEvents();
    int getNumberOfEventsOnQueue();
}

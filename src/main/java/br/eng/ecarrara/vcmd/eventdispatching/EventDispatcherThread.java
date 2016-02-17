package br.eng.ecarrara.vcmd.eventdispatching;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of {@link EventDispatcher} that stores the posted {@link Event}s in a
 * {@link LinkedBlockingQueue} and uses a simple background {@link Thread} that start processing
 * one {@link Event} at time after startProcessingEvents is called until stopProcessingEvents
 * is finally called.
 *
 */
public abstract class EventDispatcherThread extends Thread implements EventDispatcher {

    public abstract void dispatch(Event event);

    private static final long WAITING_EVENT_QUEUE_POOL_TIMEOUT_MS = 2000;
    private LinkedBlockingQueue<Event> eventsQueue;
    private volatile boolean processingEvents;

    public EventDispatcherThread() {
        this.processingEvents = NOT_PROCESSING_EVENTS;
        this.eventsQueue = new LinkedBlockingQueue<>();
    }

    @Override public void startProcessingEvents() {
        if(!isProcessingEvents()) {
            processingEvents = PROCESSING_EVENTS;
            this.start();
        }
    }

    @Override public void stopProcessingEvents() {
        processingEvents = NOT_PROCESSING_EVENTS;
    }

    @Override public boolean isProcessingEvents() {
        return this.processingEvents;
    }

    @Override public void post(Event event) {
        this.eventsQueue.add(event);
    }

    @Override public int getNumberOfEventsOnQueue() {
        return this.eventsQueue.size();
    }

    @Override public void run() {
        processEventQueue();
    }

    private void processEventQueue() {
        while(processingEvents) {
            Event event = pollEventFromQueue();
            if(event == null) { //no events to process
                continue;
            } else {
                dispatch(event);
            }
        }
    }

    private Event pollEventFromQueue() {
        Event event;
        try {
            event = this.eventsQueue
                    .poll(WAITING_EVENT_QUEUE_POOL_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(
                    "EventDispatcherTread interrupted while waiting events to process", e);
        }
        return event;
    }
}

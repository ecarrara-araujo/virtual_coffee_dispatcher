package br.eng.ecarrara.vcmd.eventdispatching;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.eng.ecarrara.vcmd.eventdispatching.mock.MockEvent;
import br.eng.ecarrara.vcmd.eventdispatching.mock.MockEventDispatcherThread;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import static br.eng.ecarrara.vcmd.eventdispatching.EventDispatcher.PROCESSING_EVENTS;
import static br.eng.ecarrara.vcmd.eventdispatching.EventDispatcher.NOT_PROCESSING_EVENTS;

public class EventDispatcherTest {

    private EventDispatcher eventDispatcher;
    private Event mockEvent;

    @Before public void setUp() {
        this.eventDispatcher = new MockEventDispatcherThread();
        eventDispatcher.startProcessingEvents();
        this.mockEvent = new MockEvent(this.eventDispatcher);
    }

    @After public void tearDown() {
        this.eventDispatcher.stopProcessingEvents();
    }

    @Test public void testSuccessfulEventProcessingStarted() {
        assertThat(eventDispatcher.isProcessingEvents(), is(PROCESSING_EVENTS));
    }

    @Test public void testSuccessfulEventProcessingStopped() {
        this.eventDispatcher.stopProcessingEvents();
        assertThat(eventDispatcher.isProcessingEvents(), is(NOT_PROCESSING_EVENTS));
    }

    @Test public void testSuccessfulEventPost() {
        final int NUMBER_OF_EVENTS_POSTED = 1;
        eventDispatcher.post(this.mockEvent);
        assertThat(eventDispatcher.getNumberOfEventsOnQueue(), is(NUMBER_OF_EVENTS_POSTED));
    }

    @Test (expected = NullPointerException.class)
    public void testNullPointerExceptionThrownWhenNullEventIsPosted() {
        eventDispatcher.post(null);
    }

}

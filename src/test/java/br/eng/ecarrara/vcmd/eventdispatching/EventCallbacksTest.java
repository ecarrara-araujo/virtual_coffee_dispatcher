package br.eng.ecarrara.vcmd.eventdispatching;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import br.eng.ecarrara.vcmd.eventdispatching.mock.MockErrorEvent;
import br.eng.ecarrara.vcmd.eventdispatching.mock.MockEvent;
import br.eng.ecarrara.vcmd.eventdispatching.mock.MockEventDispatcherThread;

public class EventCallbacksTest {

    private Event mockRequestEvent;
    private Event mockResultEvent;

    private EventDispatcher mockSenderEventDispatcher;
    private ErrorEvent mockErrorEvent;

    @Before public void setUp() {
        this.mockSenderEventDispatcher = new MockEventDispatcherThread();
        this.mockRequestEvent = new MockEvent(this.mockSenderEventDispatcher);
        this.mockResultEvent = new MockEvent(this.mockSenderEventDispatcher);
        this.mockErrorEvent = new MockErrorEvent(this.mockSenderEventDispatcher,
                this.mockRequestEvent, MockErrorEvent.ERROR_CODE_MOCK,
                MockErrorEvent.ERROR_MESSAGE_MOCK);
    }

    @Test public void testConsistentEventDispatcher() {
        assertThat(this.mockRequestEvent.getSender(), is(this.mockSenderEventDispatcher));
    }

    @Test public void testNotifySender() {
        final int NUMBER_OF_EVENTS_POSTED = 1;
        mockRequestEvent.notifySender(this.mockResultEvent);
        assertThat(mockRequestEvent.getSender().getNumberOfEventsOnQueue(),
                is(NUMBER_OF_EVENTS_POSTED));
    }

    @Test public void testNotifyErrorToSender() {
        final int NUMBER_OF_ERRORS_POSTED = 1;
        mockRequestEvent.notifyErrorToSender(mockErrorEvent);
        assertThat(mockRequestEvent.getSender().getNumberOfEventsOnQueue(),
                is(NUMBER_OF_ERRORS_POSTED));
    }

}

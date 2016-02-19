package br.eng.ecarrara.vcmd.eventdispatching;

import org.junit.Before;
import org.junit.Test;

import br.eng.ecarrara.vcmd.eventdispatching.mock.MockErrorEvent;
import br.eng.ecarrara.vcmd.eventdispatching.mock.MockEvent;

import static br.eng.ecarrara.vcmd.eventdispatching.EventDispatcher.NOT_PROCESSING_EVENTS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EventDispatchingSequenceTest {

    final static int DISPATCHER_WAIT_TIMEOUT_MS = 5000;

    private EventDispatcherThread requesterDispatcher;
    private EventDispatcherThread processorRequester;


    @Test public void testCompleteSuccessfulEventDispatchingSequence() throws InterruptedException {
        createRequesterThatExpectsAnOKReturn();
        createProcessorThatProcessNoOPAndReturnOK();
        startRequesterAndProcessor();
        postRequestEvent();
        waitForRequesterAndProcessorToFinish();
        assertThatRequesterAndProcessorAreNotProcessingEvents();
    }

    @Test public void testErrorEventDispatchingSequence() throws InterruptedException {
        createRequesterThatExpectsAnError();
        createProcessorThatProcessNoOPAndReturnError();
        startRequesterAndProcessor();
        postRequestEvent();
        waitForRequesterAndProcessorToFinish();
        assertThatRequesterAndProcessorAreNotProcessingEvents();
    }

    private void createRequesterThatExpectsAnOKReturn() {
        requesterDispatcher =  new EventDispatcherThread() {
            @Override public void dispatch(Event event) {
                assertThat(event.getCode(), is(MockEvent.EVENT_CODE_MOCK_RETURN_OK));
                this.stopProcessingEvents();
            }
        };
    }

    private void createProcessorThatProcessNoOPAndReturnOK() {
        processorRequester = new EventDispatcherThread() {
            @Override public void dispatch(Event event) {
                assertThat(event.getCode(), is(MockEvent.EVENT_CODE_MOCK_NO_OP));
                Event responseEvent = new MockEvent(this, MockEvent.EVENT_CODE_MOCK_RETURN_OK);
                event.notifySender(responseEvent);
                this.stopProcessingEvents();
            }
        };
    }

    private void createRequesterThatExpectsAnError() {
        requesterDispatcher = new EventDispatcherThread() {
            @Override public void dispatch(Event event) {
                assertThat((event instanceof ErrorEvent), is(true));
                ErrorEvent error = (ErrorEvent) event;
                assertThat(error.getCode(), is(MockErrorEvent.ERROR_CODE_MOCK));
                assertThat(error.getMessage(), is(MockErrorEvent.ERROR_MESSAGE_MOCK));
                this.stopProcessingEvents();
            }
        };
    }

    private void createProcessorThatProcessNoOPAndReturnError() {
        processorRequester = new EventDispatcherThread() {
            @Override public void dispatch(Event event) {
                assertThat(event.getCode(), is(MockEvent.EVENT_CODE_MOCK_NO_OP));
                ErrorEvent error = new MockErrorEvent(this, event,
                        MockErrorEvent.ERROR_CODE_MOCK, MockErrorEvent.ERROR_MESSAGE_MOCK);
                event.notifySender(error);
                this.stopProcessingEvents();
            }
        };
    }

    private void startRequesterAndProcessor() {
        requesterDispatcher.startProcessingEvents();
        processorRequester.startProcessingEvents();
    }

    private void postRequestEvent() {
        final Event requestEvent = new MockEvent(requesterDispatcher);
        processorRequester.post(requestEvent);
    }

    private void waitForRequesterAndProcessorToFinish()  throws InterruptedException {
        processorRequester.join(DISPATCHER_WAIT_TIMEOUT_MS);
        requesterDispatcher.join(DISPATCHER_WAIT_TIMEOUT_MS);
    }

    private void assertThatRequesterAndProcessorAreNotProcessingEvents() {
        assertThat(requesterDispatcher.isProcessingEvents(), is(NOT_PROCESSING_EVENTS));
        assertThat(processorRequester.isProcessingEvents(), is(NOT_PROCESSING_EVENTS));
    }



}

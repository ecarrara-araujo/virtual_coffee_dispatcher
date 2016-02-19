package br.eng.ecarrara.vcmd.eventdispatching.mock;

import br.eng.ecarrara.vcmd.eventdispatching.ErrorEvent;
import br.eng.ecarrara.vcmd.eventdispatching.Event;
import br.eng.ecarrara.vcmd.eventdispatching.EventDispatcher;

public class MockErrorEvent extends ErrorEvent {

    public static final int ERROR_CODE_MOCK = 999;
    public static final String ERROR_MESSAGE_MOCK = "Error message.";

    public MockErrorEvent(EventDispatcher sender, Event originatingEvent, int code, String message) {
        super(sender, originatingEvent, code, message);
    }

}

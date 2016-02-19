package br.eng.ecarrara.vcmd.eventdispatching.mock;

import br.eng.ecarrara.vcmd.eventdispatching.Event;
import br.eng.ecarrara.vcmd.eventdispatching.EventDispatcher;

public class MockEvent extends Event {

    public static final int EVENT_CODE_MOCK_NO_OP = 0;
    public static final int EVENT_CODE_MOCK_RETURN_OK = 1;

    public MockEvent(EventDispatcher mockSenderEventDispatcher, int code) {
        super(mockSenderEventDispatcher, code);
    }

    public MockEvent(EventDispatcher mockSenderEventDispatcher) {
        this(mockSenderEventDispatcher, EVENT_CODE_MOCK_NO_OP);
    }
}

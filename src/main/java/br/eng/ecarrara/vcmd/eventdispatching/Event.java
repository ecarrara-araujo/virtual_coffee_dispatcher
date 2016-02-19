package br.eng.ecarrara.vcmd.eventdispatching;

/**
 * Simple event object to be exchanged between dispatchers.
 * Here a action mechanism is used to identify what is going to be requested, or returned as status
 * and even an error.
 *
 */
public class Event {

    public static final int ACTION_CODE_NO_ACTION = -1;

    private EventDispatcher sender;
    private int code;

    /**
     *
     * @param sender
     * @param code Generic event code that can be used by an implementing dispatcher or sender
     *             to identify what needs to be done.
     */
    public Event(EventDispatcher sender, int code) {
        this.sender = sender;
        this.code = code;
    }

    public EventDispatcher getSender() {
        return sender;
    }

    public int getCode() {
        return code;
    }

    public void notifySender(Event event) {
        this.sender.post(event);
    }

    public void notifyErrorToSender(ErrorEvent event) {
        this.notifySender(event);
    }
}

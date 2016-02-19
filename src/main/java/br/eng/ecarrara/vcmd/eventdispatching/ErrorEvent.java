package br.eng.ecarrara.vcmd.eventdispatching;

/**
 * A specialization of {@link Event} that allow us to give a little bit more of background of
 * what happened.
 *
 */
public class ErrorEvent extends Event {

    private Event originatingEvent;
    private String message;

    public static boolean isError(Event event) {
        return event instanceof ErrorEvent;
    }

    public ErrorEvent(EventDispatcher sender, Event originatingEvent, int code, String message) {
        super(sender, code);
        this.originatingEvent = originatingEvent;
        this.message = message;
    }

    public Event getOriginatingEvent() {
        return originatingEvent;
    }

    public String getMessage() {
        return message;
    }
}

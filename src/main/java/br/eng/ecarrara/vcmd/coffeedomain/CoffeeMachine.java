package br.eng.ecarrara.vcmd.coffeedomain;

import br.eng.ecarrara.vcmd.eventdispatching.ErrorEvent;
import br.eng.ecarrara.vcmd.eventdispatching.Event;
import br.eng.ecarrara.vcmd.eventdispatching.EventDispatcherThread;

/**
 * Component that exemplifies the treatment of events it received from any other components/threads.
 * In this case we are using a coffee machine as an example, it could receive orders from
 * several different customers but will process one at time using a queue (depending on the place
 * we should get a bigger machine...).
 *
 */
public class CoffeeMachine extends EventDispatcherThread {

    public static final int PRODUCT_CODE_ESPRESSO = 1;
    public static final int PRODUCT_CODE_CAPPUCCINO = 2;
    public static final int PRODUCT_CODE_MACHIATTO = 3;
    public static final int PRODUCT_CODE_LATTE = 4;

    public static final int PRODUCT_READY = 0;
    public static final int SHUT_DOWN_ORDER = 999;

    public static final int ERROR_NO_INGREDIENTS = -1;
    public static final int ERROR_MACHINE_TOO_HOT = -2;
    public static final int ERROR_NO_CHANGE = -3;
    public static final int ERROR_UNKNOWN_ERROR = -4;

    private Event currentOrder;
    private int errorCode;

    @Override public void dispatch(Event event) {
        validateOrder(event);
        this.currentOrder = event;
        if(ErrorEvent.isError(event)) {
            // cant do nothing about it I am a machine. Next order plz.
            System.out.println("Order ignored: Error " + event.getCode());
            return;
        } else {
            prepareOrder();
        }
    }

    private void validateOrder(Event order) {
        if(order == null) {
            throw new NullPointerException("Order cannot be null at this point.");
        }
    }

    private void prepareOrder() {
        final int productCode = this.currentOrder.getCode();
        switch (productCode) {
            case PRODUCT_CODE_ESPRESSO:
                prepareEspresso();
                break;
            case PRODUCT_CODE_CAPPUCCINO:
                prepareCappuccino();
                break;
            case PRODUCT_CODE_MACHIATTO:
                prepareMachiatto();
                break;
            case PRODUCT_CODE_LATTE:
                prepareLatte();
                break;
            case SHUT_DOWN_ORDER:
                shutdown();
                break;
            default:
                rejectOrder();
        }
    }

    private void shutdown() {
        this.stopProcessingEvents();
        System.out.println("The machine was shut down.");
    }

    private void rejectOrder() {
        int errorCode = getErrorCode();
        System.out.println("Cannot produce order.");
        ErrorEvent error = new ErrorEvent(this, this.currentOrder, errorCode,
                "Cannot produce your order.");
        this.currentOrder.notifyErrorToSender(error);

    }

    private void prepareLatte() {
        System.out.println("Latte is under preparation... Latte ready.");
        deliveryProductToCustomer();
    }

    private void prepareMachiatto() {
        System.out.println("Machiatto is under preparation... Machiatto ready.");
        deliveryProductToCustomer();
    }

    private void prepareCappuccino() {
        System.out.println("Cappuccino is under preparation... Cappuccino ready.");
        deliveryProductToCustomer();
    }

    private void prepareEspresso() {
        System.out.println("Espresso is under preparation... Espresso ready.");
        deliveryProductToCustomer();
    }

    private void deliveryProductToCustomer() {
        Event productReady = new Event(this, PRODUCT_READY);
        this.currentOrder.notifySender(productReady);
    }

    public int getErrorCode() {
        return errorCode;
    }
}

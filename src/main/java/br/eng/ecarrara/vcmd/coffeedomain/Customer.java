package br.eng.ecarrara.vcmd.coffeedomain;

import br.eng.ecarrara.vcmd.eventdispatching.ErrorEvent;
import br.eng.ecarrara.vcmd.eventdispatching.Event;
import br.eng.ecarrara.vcmd.eventdispatching.EventDispatcherThread;

/**
 * Simple class to exemplify a component that requests a service from another.
 * In this case we are using a coffee consumer as an example.
 *
 */
public class Customer extends EventDispatcherThread {
    private String name;
    private int favoriteProduct;

    public Customer(String name, int favoriteProduct) {
        this.name = name;
        this.favoriteProduct = favoriteProduct;
    }

    public CoffeeMachineOrder getOrder() {
        CoffeeMachineOrder coffeeMachineOrder = new CoffeeMachineOrder(this, this.favoriteProduct);
        return  coffeeMachineOrder;
    }

    @Override public void dispatch(Event event) {
        validate(event);
        if(ErrorEvent.isError(event)) {
            complainAboutError();
        } else {
            consumeOrder();
        }
        leave();
    }

    private void validate(Event event) {
        if(event == null) {
            throw new NullPointerException("Say wat?");
        }
    }

    private void consumeOrder() {
        stopProcessingEvents();
        System.out.println(this.name + ": this is delicious.");
    }

    private void leave() {
        stopProcessingEvents();
        System.out.println(this.name + ": left.");
    }

    private void complainAboutError() {
        System.out.println("Can`t get my favorite drink. I am out of here...");
    }

}

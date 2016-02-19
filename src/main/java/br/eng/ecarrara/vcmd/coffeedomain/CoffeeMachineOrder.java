package br.eng.ecarrara.vcmd.coffeedomain;

import br.eng.ecarrara.vcmd.eventdispatching.Event;

public class CoffeeMachineOrder extends Event {

    /**
     * @param customer
     * @param orderCode   Generic event code that can be used by an implementing dispatcher or sender
     */
    public CoffeeMachineOrder(Customer customer, int orderCode) {
        super(customer, orderCode);
    }
}

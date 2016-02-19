package br.eng.ecarrara.vcmd;

import br.eng.ecarrara.vcmd.coffeedomain.CoffeeMachine;
import br.eng.ecarrara.vcmd.coffeedomain.Customer;

import static br.eng.ecarrara.vcmd.coffeedomain.CoffeeMachine.PRODUCT_CODE_CAPPUCCINO;
import static br.eng.ecarrara.vcmd.coffeedomain.CoffeeMachine.PRODUCT_CODE_ESPRESSO;
import static br.eng.ecarrara.vcmd.coffeedomain.CoffeeMachine.PRODUCT_CODE_MACHIATTO;
import static br.eng.ecarrara.vcmd.coffeedomain.CoffeeMachine.SHUT_DOWN_ORDER;

public class VirtualCoffeeMachineDispatcherMain {

    private static final int SOME_OTHER_PRODUCT = -9999;

    public static void main(String[] args) {
        System.out.println("Coffee Machine is now Working! Place your orders!");

        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.startProcessingEvents();

        Customer machineOperator =  new Customer("Operator", SHUT_DOWN_ORDER);
        Customer maria = new Customer("Maria", PRODUCT_CODE_CAPPUCCINO);
        maria.startProcessingEvents();
        Customer jose = new Customer("Jose", PRODUCT_CODE_ESPRESSO);
        jose.startProcessingEvents();
        Customer john = new Customer("Paul", PRODUCT_CODE_MACHIATTO);
        john.startProcessingEvents();
        Customer wololo = new Customer("wololo", SOME_OTHER_PRODUCT);
        wololo.startProcessingEvents();

        coffeeMachine.post(maria.getOrder());
        coffeeMachine.post(jose.getOrder());
        coffeeMachine.post(john.getOrder());
        coffeeMachine.post(wololo.getOrder());
        coffeeMachine.post(machineOperator.getOrder());

        // wait for a few seconds the machine to finish its job
        try {
            coffeeMachine.join(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

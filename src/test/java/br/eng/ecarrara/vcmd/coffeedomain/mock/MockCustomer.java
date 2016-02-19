package br.eng.ecarrara.vcmd.coffeedomain.mock;

import br.eng.ecarrara.vcmd.coffeedomain.CoffeeMachine;
import br.eng.ecarrara.vcmd.coffeedomain.Customer;

public class MockCustomer extends Customer {

    public MockCustomer() {
        this(CoffeeMachine.PRODUCT_CODE_ESPRESSO);
    }

    public MockCustomer(int favoriteProduct) {
        this("Mock Customer", favoriteProduct);
    }

    public MockCustomer(String name, int favoriteProduct) {
        super(name, favoriteProduct);
    }
}

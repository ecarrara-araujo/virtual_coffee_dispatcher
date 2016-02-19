package br.eng.ecarrara.vcmd.coffeedomain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import br.eng.ecarrara.vcmd.coffeedomain.mock.MockCustomer;
import br.eng.ecarrara.vcmd.eventdispatching.ErrorEvent;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CoffeeMachineTest {

    private final ByteArrayOutputStream machineMessages = new ByteArrayOutputStream();
    private CoffeeMachine coffeeMachine;

    @Before public void setUp() {
        this.coffeeMachine = new CoffeeMachine();
        System.setOut(new PrintStream(machineMessages));
    }

    @After public void tearDown() {
        System.setOut(null);
    }

    @Test(expected = NullPointerException.class)
    public void testDispatchWithNullEvent() throws Exception {
        this.coffeeMachine.dispatch(null);
    }

    @Test public void testDispatchErrorEvent() {
        final String errorMessage = "This is an error.";
        final int errorCode = -1;
        ErrorEvent errorEvent = new ErrorEvent(null, null, errorCode, errorMessage);
        this.coffeeMachine.dispatch(errorEvent);
        assertThat(machineMessages.toString().trim(), is("Order ignored: Error " + errorCode));
    }

    @Test public void testDispatchEspressoOrder() {
        testDispatchOrderExpectingMessage(CoffeeMachine.PRODUCT_CODE_ESPRESSO,
                "Espresso is under preparation... Espresso ready.");
    }

    @Test public void testDispatchCappuccinoOrder() {
        testDispatchOrderExpectingMessage(CoffeeMachine.PRODUCT_CODE_CAPPUCCINO,
                "Cappuccino is under preparation... Cappuccino ready.");
    }

    @Test public void testDispatchMachiattoOrder() {
        testDispatchOrderExpectingMessage(CoffeeMachine.PRODUCT_CODE_MACHIATTO,
                "Machiatto is under preparation... Machiatto ready.");
    }

    @Test public void testDispatchLatteOrder() {
        testDispatchOrderExpectingMessage(CoffeeMachine.PRODUCT_CODE_LATTE,
                "Latte is under preparation... Latte ready.");
    }

    @Test public void testDispatchUnknownOrder() {
        testDispatchOrderExpectingMessage(-999, "Cannot produce order.");
    }

    private void testDispatchOrderExpectingMessage(int productCode, String expectedMessage) {
        MockCustomer customer =  new MockCustomer(productCode);
        this.coffeeMachine.dispatch(customer.getOrder());
        assertThat(machineMessages.toString().trim(), is(expectedMessage));
    }
}
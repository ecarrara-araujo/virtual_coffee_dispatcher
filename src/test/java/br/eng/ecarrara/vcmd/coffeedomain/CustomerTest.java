package br.eng.ecarrara.vcmd.coffeedomain;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import br.eng.ecarrara.vcmd.coffeedomain.mock.MockCustomer;
import br.eng.ecarrara.vcmd.eventdispatching.ErrorEvent;
import br.eng.ecarrara.vcmd.eventdispatching.Event;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CustomerTest {
    private final ByteArrayOutputStream customerMessages = new ByteArrayOutputStream();
    private MockCustomer customer;

    @Before public void setUp() {
        this.customer = new MockCustomer();
        System.setOut(new PrintStream(customerMessages));
    }

    @Test(expected = NullPointerException.class)
    public void testDispatchWithNullEvent() throws Exception {
        this.customer.dispatch(null);
    }

    @Test public void testDispatchErrorEventCustomerShouldComplain() {
        final String errorMessage = "This is an error.";
        final int errorCode = -1;
        ErrorEvent errorEvent = new ErrorEvent(null, null, errorCode, errorMessage);
        this.customer.dispatch(errorEvent);
        assertThat(customerMessages.toString().trim(),
                is("Can`t get my favorite drink. I am out of here...\r\nMock Customer: left."));
    }

    @Test public void testDispatchOrderSuccessful() {
        Event productReady = new Event(null, CoffeeMachine.PRODUCT_READY);
        this.customer.dispatch(productReady);
        assertThat(customerMessages.toString().trim(),
                is("Mock Customer: this is delicious.\r\nMock Customer: left."));
    }
}
package br.eng.ecarrara.vcmd.eventdispatching;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventDispatcherTest.class,
        EventCallbacksTest.class,
        EventDispatchingSequenceTest.class
})
public class EventDispatchingTestSuite {
}

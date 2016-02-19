package br.eng.ecarrara.vcmd;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.eng.ecarrara.vcmd.eventdispatching.EventDispatchingTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventDispatchingTestSuite.class
})
public class FullTestSuite {
}

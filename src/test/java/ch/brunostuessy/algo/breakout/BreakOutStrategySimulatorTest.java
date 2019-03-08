package ch.brunostuessy.algo.breakout;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for BreakOutStrategySimulator.
 */
public class BreakOutStrategySimulatorTest {

    /**
     * Smoke test.
     */
    @Test
    public void testStrategySimulator()
    {
		try {
			BreakOutStrategySimulator.main(new String[] {});
		} catch (final Exception e) {
			Assert.fail();
		}
    }
}

package ch.brunostuessy.algo.breakout;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for BreakOutStrategySimulator.
 */
public class BreakOutStrategySimulatorTest {

    @Test
    public void testStrategySimulatorWithValidFile()
    {
		try {
			BreakOutStrategySimulator.main(new String[] {"EUR.USD.csv"});
		} catch (final Exception e) {
			Assert.fail();
		}
    }

    @Test
    public void testStrategySimulatorWithMissingFile()
    {
		try {
			BreakOutStrategySimulator.main(new String[] {"missing.csv"});
		} catch (final Exception e) {
			Assert.fail();
		}
    }

    @Test
    public void testStrategySimulatorWithoutFile()
    {
		try {
			BreakOutStrategySimulator.main(new String[] {});
		} catch (final Exception e) {
			Assert.fail();
		}
    }
    
}

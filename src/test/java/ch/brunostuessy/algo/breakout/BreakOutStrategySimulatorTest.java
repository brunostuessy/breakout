package ch.brunostuessy.algo.breakout;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for BreakOutStrategySimulator.
 */
public class BreakOutStrategySimulatorTest {

	@Test
	public void strategySimulatorWithValidFile() {
		try {
			BreakOutStrategySimulator.main(new String[] { "EUR.USD.csv" });
		} catch (final Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void strategySimulatorWithMissingFile() {
		try {
			BreakOutStrategySimulator.main(new String[] { "missing.csv" });
		} catch (final Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void strategySimulatorWithoutFile() {
		try {
			BreakOutStrategySimulator.main(new String[] {});
		} catch (final Exception e) {
			Assert.fail();
		}
	}

}

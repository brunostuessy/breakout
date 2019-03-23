package ch.brunostuessy.algo.breakout;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for BreakOutStrategySimulator.
 */
public class BreakOutStrategySimulatorTest {

	@Test
	public void strategySimulatorWithValidFile() {
		try {
			BreakOutStrategySimulator.main(new String[] { "EUR.USD.csv" });
		} catch (final Exception e) {
			Assertions.fail();
		}
	}

	@Test
	public void strategySimulatorWithMissingFile() {
		try {
			BreakOutStrategySimulator.main(new String[] { "missing.csv" });
		} catch (final Exception e) {
			Assertions.fail();
		}
	}

	@Test
	public void strategySimulatorWithoutFile() {
		try {
			BreakOutStrategySimulator.main(new String[] {});
		} catch (final Exception e) {
			Assertions.fail();
		}
	}

}

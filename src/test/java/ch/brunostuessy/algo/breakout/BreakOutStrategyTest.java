package ch.brunostuessy.algo.breakout;

import org.junit.Assert;
import org.junit.Test;

import ch.algotrader.enumeration.Direction;
import ch.algotrader.simulation.Simulator;
import ch.algotrader.simulation.SimulatorImpl;
import ch.brunostuessy.algo.strategy.StrategyRunner;
import ch.brunostuessy.algo.ta.BandOrientation;

/**
 * Unit tests for BreakOutStrategy.
 */
public class BreakOutStrategyTest {

	/**
	 * BDD test with window size 30.
	 */
	@Test
	public void strategyWithWindowSize30() {
		final Simulator simulator = new SimulatorImpl();
		final BreakOutStrategy breakOutStrategy = new BreakOutStrategy(simulator);

		final StrategyRunner<BandOrientation> runner = new StrategyRunner<BandOrientation>(breakOutStrategy, simulator,
				30);

		breakOutStrategy.onBegin(1000000);
		Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.INVALID);
		Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);
		try {
			runner.applyPrice(0.9271);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.INVALID);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);
			runner.applyPrice(0.9271);
			runner.applyPrice(0.9507);
			runner.applyPrice(0.9575);
			runner.applyPrice(0.9467);
			runner.applyPrice(0.9437);
			runner.applyPrice(0.9366);
			runner.applyPrice(0.9521);
			runner.applyPrice(0.9508);
			runner.applyPrice(0.9431);
			runner.applyPrice(0.9421);
			runner.applyPrice(0.936);
			runner.applyPrice(0.9424);
			runner.applyPrice(0.9346);
			runner.applyPrice(0.9383);
			runner.applyPrice(0.9346);
			runner.applyPrice(0.9239);
			runner.applyPrice(0.9244);
			runner.applyPrice(0.9243);
			runner.applyPrice(0.917);
			runner.applyPrice(0.9262);
			runner.applyPrice(0.9374);
			runner.applyPrice(0.9374);
			runner.applyPrice(0.9357);
			runner.applyPrice(0.938);
			runner.applyPrice(0.9311);
			runner.applyPrice(0.9285);
			runner.applyPrice(0.9182);
			runner.applyPrice(0.9253);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.INVALID);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);

			// fill window
			runner.applyPrice(0.9321);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.BELOWMIDDLE);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);

			runner.applyPrice(0.9205);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.BELOWMIDDLE);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);

			// cross below BB
			runner.applyPrice(0.8000);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.BELOWLOWER);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.LONG);

			// stay below SMA
			runner.applyPrice(0.9053);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.BELOWMIDDLE);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.LONG);

			// ******************************
			// cross above SMA and BB
			runner.applyPrice(1.2000);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.ABOVEUPPER);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.SHORT);
			// ******************************

			// stay above SMA
			runner.applyPrice(1.0500);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.ABOVEMIDDLE);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.SHORT);

			// cross below SMA
			runner.applyPrice(0.9104);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.BELOWMIDDLE);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);

			// cross below BB
			runner.applyPrice(0.7000);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.BELOWLOWER);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.LONG);

			// stay below SMA
			runner.applyPrice(0.9104);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.BELOWMIDDLE);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.LONG);

			// cross above SMA
			runner.applyPrice(1.06000);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.ABOVEMIDDLE);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);

			// cross above BB
			runner.applyPrice(1.12000);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.ABOVEUPPER);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.SHORT);

			// ******************************
			// cross below SMA and BB
			runner.applyPrice(0.7000);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.BELOWLOWER);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.LONG);
			// ******************************
		} finally {
			breakOutStrategy.onEnd();
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);
		}
	}

	/**
	 * BDD test without window.
	 */
	@Test
	public void strategyWithoutWindow() {
		final Simulator simulator = new SimulatorImpl();
		final BreakOutStrategy breakOutStrategy = new BreakOutStrategy(simulator);

		final StrategyRunner<BandOrientation> runner = new StrategyRunner<BandOrientation>(breakOutStrategy, simulator,
				0);

		breakOutStrategy.onBegin(1000000);
		Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.INVALID);
		try {
			runner.applyPrice(0.9271);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.ONMIDDLE);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);
			runner.applyPrice(0.9271);
			runner.applyPrice(0.9507);
			runner.applyPrice(0.9575);
			runner.applyPrice(0.9467);
			runner.applyPrice(0.9437);
			runner.applyPrice(0.9366);
			runner.applyPrice(0.9521);
			runner.applyPrice(0.9508);
			runner.applyPrice(0.9431);
			runner.applyPrice(0.9421);
			runner.applyPrice(0.936);
			runner.applyPrice(0.9424);
			runner.applyPrice(0.9346);
			runner.applyPrice(0.9383);
			runner.applyPrice(0.9346);
			runner.applyPrice(0.9239);
			runner.applyPrice(0.9244);
			runner.applyPrice(0.9243);
			runner.applyPrice(0.917);
			runner.applyPrice(0.9262);
			runner.applyPrice(0.9374);
			runner.applyPrice(0.9374);
			runner.applyPrice(0.9357);
			runner.applyPrice(0.938);
			runner.applyPrice(0.9311);
			runner.applyPrice(0.9285);
			runner.applyPrice(0.9182);
			runner.applyPrice(0.9253);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.BELOWMIDDLE);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);

			// fill window
			runner.applyPrice(0.9321);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.BELOWMIDDLE);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);

			runner.applyPrice(0.9205);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.BELOWMIDDLE);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);

			// cross below BB
			runner.applyPrice(0.8000);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.BELOWLOWER);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.LONG);

			// stay below SMA
			runner.applyPrice(0.9053);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.BELOWMIDDLE);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.LONG);

			// ******************************
			// cross above SMA and BB
			runner.applyPrice(1.2000);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.ABOVEUPPER);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.SHORT);
			// ******************************

			// stay above SMA
			runner.applyPrice(1.0500);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.ABOVEMIDDLE);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.SHORT);

			// cross below SMA
			runner.applyPrice(0.9104);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.BELOWMIDDLE);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);

			// cross below BB
			runner.applyPrice(0.7000);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.BELOWLOWER);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.LONG);

			// stay below SMA
			runner.applyPrice(0.9104);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.BELOWMIDDLE);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.LONG);

			// cross above SMA
			runner.applyPrice(1.06000);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.ABOVEMIDDLE);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);

			// cross above BB
			runner.applyPrice(1.12000);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.ABOVEUPPER);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.SHORT);

			// ******************************
			// cross below SMA and BB
			runner.applyPrice(0.7000);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.BELOWLOWER);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.LONG);
			// ******************************

			// cross above SMA and BB
			runner.applyPrice(1.2000);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.ABOVEUPPER);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.SHORT);
		} finally {
			breakOutStrategy.onEnd();
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);
		}
	}

	/**
	 * BDD test with invalid close price.
	 */
	@Test
	public void strategyLeavesMarketWithInvalidClose() {
		final Simulator simulator = new SimulatorImpl();
		final BreakOutStrategy breakOutStrategy = new BreakOutStrategy(simulator);

		final StrategyRunner<BandOrientation> runner = new StrategyRunner<BandOrientation>(breakOutStrategy, simulator,
				0);

		breakOutStrategy.onBegin(1000000);
		Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.INVALID);
		Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);
		try {
			runner.applyPrice(0.9271);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.ONMIDDLE);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);
			runner.applyPrice(0.9271);
			runner.applyPrice(0.9507);
			runner.applyPrice(0.9575);
			runner.applyPrice(0.9467);

			runner.applyPrice(0.1000);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.BELOWLOWER);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.LONG);

			runner.applyPrice(Double.NaN);
			Assert.assertTrue(breakOutStrategy.getBandOrientation() == BandOrientation.INVALID);
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);
		} finally {
			breakOutStrategy.onEnd();
			Assert.assertTrue(breakOutStrategy.getPositionDirection() == Direction.FLAT);
		}
	}
}

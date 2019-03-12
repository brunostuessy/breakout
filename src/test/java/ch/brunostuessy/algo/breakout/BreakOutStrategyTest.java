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
		Assert.assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
		Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
		try {
			runner.applyPrice(0.9271);
			Assert.assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
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
			Assert.assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// fill window
			runner.applyPrice(0.9321);
			Assert.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			runner.applyPrice(0.9205);
			Assert.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// cross below BB
			runner.applyPrice(0.8000);
			Assert.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// stay below SMA
			runner.applyPrice(0.9053);
			Assert.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// ******************************
			// cross above SMA and BB
			runner.applyPrice(1.2000);
			Assert.assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());
			// ******************************

			// stay above SMA
			runner.applyPrice(1.0500);
			Assert.assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());

			// cross below SMA
			runner.applyPrice(0.9104);
			Assert.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// cross below BB
			runner.applyPrice(0.7000);
			Assert.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// stay below SMA
			runner.applyPrice(0.9104);
			Assert.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// cross above SMA
			runner.applyPrice(1.06000);
			Assert.assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// cross above BB
			runner.applyPrice(1.12000);
			Assert.assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());

			// ******************************
			// cross below SMA and BB
			runner.applyPrice(0.7000);
			Assert.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());
			// ******************************
		} finally {
			breakOutStrategy.onEnd();
			Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
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
		Assert.assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
		Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
		try {
			runner.applyPrice(0.9271);
			Assert.assertEquals(BandOrientation.ONMIDDLE, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
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
			Assert.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// fill window
			runner.applyPrice(0.9321);
			Assert.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			runner.applyPrice(0.9205);
			Assert.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// cross below BB
			runner.applyPrice(0.8000);
			Assert.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// stay below SMA
			runner.applyPrice(0.9053);
			Assert.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// ******************************
			// cross above SMA and BB
			runner.applyPrice(1.2000);
			Assert.assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());
			// ******************************

			// stay above SMA
			runner.applyPrice(1.0500);
			Assert.assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());

			// cross below SMA
			runner.applyPrice(0.9104);
			Assert.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// cross below BB
			runner.applyPrice(0.7000);
			Assert.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// stay below SMA
			runner.applyPrice(0.9104);
			Assert.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// cross above SMA
			runner.applyPrice(1.06000);
			Assert.assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// cross above BB
			runner.applyPrice(1.12000);
			Assert.assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());

			// ******************************
			// cross below SMA and BB
			runner.applyPrice(0.7000);
			Assert.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());
			// ******************************

			// cross above SMA and BB
			runner.applyPrice(1.2000);
			Assert.assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());
		} finally {
			breakOutStrategy.onEnd();
			Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
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
		Assert.assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
		Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
		try {
			runner.applyPrice(0.9271);
			Assert.assertEquals(BandOrientation.ONMIDDLE, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
			runner.applyPrice(0.9271);
			runner.applyPrice(0.9507);
			runner.applyPrice(0.9575);
			runner.applyPrice(0.9467);

			runner.applyPrice(0.1000);
			Assert.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			runner.applyPrice(Double.NaN);
			Assert.assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
			Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
		} finally {
			breakOutStrategy.onEnd();
			Assert.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
		}
	}
}

package ch.brunostuessy.algo.breakout;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
	public void strategyWithWindowSize30UseLookback() {
		final Simulator simulator = new SimulatorImpl();
		final BreakOutStrategy breakOutStrategy = new BreakOutStrategy(simulator, 30, 2.0);

		final StrategyRunner<PriceWithStatistics, BandOrientation> runner = new StrategyRunner<PriceWithStatistics, BandOrientation>(
				breakOutStrategy, simulator, false);

		breakOutStrategy.onBegin(1000000);
		Assertions.assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
		Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
		try {
			runner.applyPrice(0.9271);
			Assertions.assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
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
			Assertions.assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// fill window
			runner.applyPrice(0.9321);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			runner.applyPrice(0.9205);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// cross below BB
			runner.applyPrice(0.8000);
			Assertions.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// stay below SMA
			runner.applyPrice(0.9053);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// ******************************
			// cross above SMA and BB
			runner.applyPrice(1.2000);
			Assertions.assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());
			// ******************************

			// stay above SMA
			runner.applyPrice(1.0500);
			Assertions.assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());

			// cross below SMA
			runner.applyPrice(0.9104);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// cross below BB
			runner.applyPrice(0.7000);
			Assertions.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// stay below SMA
			runner.applyPrice(0.9104);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// cross above SMA
			runner.applyPrice(1.06000);
			Assertions.assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// cross above BB
			runner.applyPrice(1.12000);
			Assertions.assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());

			// ******************************
			// cross below SMA and BB
			runner.applyPrice(0.7000);
			Assertions.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());
			// ******************************
		} finally {
			breakOutStrategy.onEnd();
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
		}
	}

	/**
	 * BDD test without window.
	 */
	@Test
	public void strategyWithoutWindowUseLookback() {
		final Simulator simulator = new SimulatorImpl();
		final BreakOutStrategy breakOutStrategy = new BreakOutStrategy(simulator, 0, 2.0);

		final StrategyRunner<PriceWithStatistics, BandOrientation> runner = new StrategyRunner<PriceWithStatistics, BandOrientation>(
				breakOutStrategy, simulator, false);

		breakOutStrategy.onBegin(1000000);
		Assertions.assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
		Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
		try {
			runner.applyPrice(0.9271);
			Assertions.assertEquals(BandOrientation.ONMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
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
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// fill window
			runner.applyPrice(0.9321);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			runner.applyPrice(0.9205);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// cross below BB
			runner.applyPrice(0.8000);
			Assertions.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// stay below SMA
			runner.applyPrice(0.9053);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// ******************************
			// cross above SMA and BB
			runner.applyPrice(1.2000);
			Assertions.assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());
			// ******************************

			// stay above SMA
			runner.applyPrice(1.0500);
			Assertions.assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());

			// cross below SMA
			runner.applyPrice(0.9104);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// cross below BB
			runner.applyPrice(0.7000);
			Assertions.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// stay below SMA
			runner.applyPrice(0.9104);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// cross above SMA
			runner.applyPrice(1.06000);
			Assertions.assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// cross above BB
			runner.applyPrice(1.12000);
			Assertions.assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());

			// ******************************
			// cross below SMA and BB
			runner.applyPrice(0.7000);
			Assertions.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());
			// ******************************

			// cross above SMA and BB
			runner.applyPrice(1.2000);
			Assertions.assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());
		} finally {
			breakOutStrategy.onEnd();
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
		}
	}

	/**
	 * BDD test with window size 30.
	 */
	@Test
	public void strategyWithWindowSize30UseLookahead() {
		final Simulator simulator = new SimulatorImpl();
		final BreakOutStrategy breakOutStrategy = new BreakOutStrategy(simulator, 30, 2.0);

		final StrategyRunner<PriceWithStatistics, BandOrientation> runner = new StrategyRunner<PriceWithStatistics, BandOrientation>(
				breakOutStrategy, simulator, true);

		breakOutStrategy.onBegin(1000000);
		Assertions.assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
		Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
		try {
			runner.applyPrice(0.9271);
			runner.applyPrice(0.9271);
			Assertions.assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
			// runner.applyPrice(0.9271);
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
			runner.applyPrice(0.9321);
			Assertions.assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// fill window
			// runner.applyPrice(0.9321);
			runner.applyPrice(0.9205);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// runner.applyPrice(0.9205);
			runner.applyPrice(0.8000);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// cross below BB
			// runner.applyPrice(0.8000);
			runner.applyPrice(0.9053);
			Assertions.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// stay below SMA
			// runner.applyPrice(0.9053);
			runner.applyPrice(1.2000);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// ******************************
			// cross above SMA and BB
			// runner.applyPrice(1.2000);
			runner.applyPrice(1.0500);
			Assertions.assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());
			// ******************************

			// stay above SMA
			// runner.applyPrice(1.0500);
			runner.applyPrice(0.9104);
			Assertions.assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());

			// cross below SMA
			// runner.applyPrice(0.9104);
			runner.applyPrice(0.7000);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// cross below BB
			// runner.applyPrice(0.7000);
			runner.applyPrice(0.9104);
			Assertions.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// stay below SMA
			// runner.applyPrice(0.9104);
			runner.applyPrice(1.06000);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// cross above SMA
			// runner.applyPrice(1.06000);
			runner.applyPrice(1.12000);
			Assertions.assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// cross above BB
			// runner.applyPrice(1.12000);
			runner.applyPrice(0.7000);
			Assertions.assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());

			// ******************************
			// cross below SMA and BB
			// runner.applyPrice(0.7000);
			runner.applyPrice(1.0000);
			Assertions.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());
			// ******************************
		} finally {
			breakOutStrategy.onEnd();
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
		}
	}

	/**
	 * BDD test without window.
	 */
	@Test
	public void strategyWithoutWindowUseLookahead() {
		final Simulator simulator = new SimulatorImpl();
		final BreakOutStrategy breakOutStrategy = new BreakOutStrategy(simulator, 0, 2.0);

		final StrategyRunner<PriceWithStatistics, BandOrientation> runner = new StrategyRunner<PriceWithStatistics, BandOrientation>(
				breakOutStrategy, simulator, true);

		breakOutStrategy.onBegin(1000000);
		Assertions.assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
		Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
		try {
			runner.applyPrice(0.9271);
			runner.applyPrice(0.9507);
			Assertions.assertEquals(BandOrientation.ONMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
			// runner.applyPrice(0.9507);
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
			runner.applyPrice(0.9321);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// fill window
			// runner.applyPrice(0.9321);
			runner.applyPrice(0.9205);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// runner.applyPrice(0.9205);
			runner.applyPrice(0.8000);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// cross below BB
			// runner.applyPrice(0.8000);
			runner.applyPrice(0.9053);
			Assertions.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// stay below SMA
			// runner.applyPrice(0.9053);
			runner.applyPrice(1.2000);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// ******************************
			// cross above SMA and BB
			// runner.applyPrice(1.2000);
			runner.applyPrice(1.0500);
			Assertions.assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());
			// ******************************

			// stay above SMA
			// runner.applyPrice(1.0500);
			runner.applyPrice(0.9104);
			Assertions.assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());

			// cross below SMA
			// runner.applyPrice(0.9104);
			runner.applyPrice(0.7000);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// cross below BB
			// runner.applyPrice(0.7000);
			runner.applyPrice(0.9104);
			Assertions.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// stay below SMA
			// runner.applyPrice(0.9104);
			runner.applyPrice(1.06000);
			Assertions.assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// cross above SMA
			// runner.applyPrice(1.06000);
			runner.applyPrice(1.12000);
			Assertions.assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());

			// cross above BB
			// runner.applyPrice(1.12000);
			runner.applyPrice(0.7000);
			Assertions.assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());

			// ******************************
			// cross below SMA and BB
			// runner.applyPrice(0.7000);
			runner.applyPrice(1.2000);
			Assertions.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());
			// ******************************

			// cross above SMA and BB
			// runner.applyPrice(1.2000);
			runner.applyPrice(1.0000);
			Assertions.assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.SHORT, breakOutStrategy.getPositionDirection());
		} finally {
			breakOutStrategy.onEnd();
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
		}
	}

	/**
	 * BDD test with invalid close price.
	 */
	@Test
	public void strategyIgnoresInvalidClose() {
		final Simulator simulator = new SimulatorImpl();
		final BreakOutStrategy breakOutStrategy = new BreakOutStrategy(simulator, 0, 2.0);

		final StrategyRunner<PriceWithStatistics, BandOrientation> runner = new StrategyRunner<PriceWithStatistics, BandOrientation>(
				breakOutStrategy, simulator, true);

		breakOutStrategy.onBegin(1000000);
		Assertions.assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
		Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
		try {
			runner.applyPrice(0.9271);

			runner.applyPrice(0.9271);
			Assertions.assertEquals(BandOrientation.ONMIDDLE, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
			runner.applyPrice(0.9271);
			runner.applyPrice(0.9507);
			runner.applyPrice(0.9575);
			runner.applyPrice(0.9467);

			runner.applyPrice(0.1000);
			runner.applyPrice(Double.POSITIVE_INFINITY);
			Assertions.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());

			// ignore non-finite price
			runner.applyPrice(0.2000);
			Assertions.assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			Assertions.assertEquals(Direction.LONG, breakOutStrategy.getPositionDirection());
		} finally {
			breakOutStrategy.onEnd();
			Assertions.assertEquals(Direction.FLAT, breakOutStrategy.getPositionDirection());
		}
	}
}

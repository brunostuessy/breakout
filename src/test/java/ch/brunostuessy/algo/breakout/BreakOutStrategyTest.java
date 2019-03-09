package ch.brunostuessy.algo.breakout;

import org.junit.Assert;
import org.junit.Test;

import ch.algotrader.enumeration.Direction;
import ch.algotrader.simulation.Simulator;
import ch.algotrader.simulation.SimulatorImpl;

/**
 * Unit tests for BreakOutStrategy.
 */
public class BreakOutStrategyTest {

    /**
     * BDD test with window size 30.
     */
    @Test
    public void testStrategyWithWindowSize30()
    {
		final Simulator simulator = new SimulatorImpl();
		final BreakOutStrategy breakOutStrategy = new BreakOutStrategy(simulator);

		final StrategyRunner runner = new StrategyRunner(breakOutStrategy, simulator, 30);
		
		breakOutStrategy.onBegin(1000000);
		Assert.assertTrue(breakOutStrategy.getPosition() == null || breakOutStrategy.getPosition().getDirection() == Direction.FLAT);
		try {
	        runner.runClose(0.9271);
			Assert.assertTrue(breakOutStrategy.getPosition() == null || breakOutStrategy.getPosition().getDirection() == Direction.FLAT);
	        runner.runClose(0.9271);
       		runner.runClose(0.9507);
			runner.runClose(0.9575);
			runner.runClose(0.9467);
			runner.runClose(0.9437);
			runner.runClose(0.9366);
			runner.runClose(0.9521);
			runner.runClose(0.9508);
			runner.runClose(0.9431);
			runner.runClose(0.9421);
			runner.runClose(0.936);
			runner.runClose(0.9424);
			runner.runClose(0.9346);
			runner.runClose(0.9383);
			runner.runClose(0.9346);
			runner.runClose(0.9239);
			runner.runClose(0.9244);
			runner.runClose(0.9243);
			runner.runClose(0.917);
			runner.runClose(0.9262);
			runner.runClose(0.9374);
			runner.runClose(0.9374);
			runner.runClose(0.9357);
			runner.runClose(0.938);
			runner.runClose(0.9311);
			runner.runClose(0.9285);
			runner.runClose(0.9182);
			runner.runClose(0.9253);
			Assert.assertTrue(breakOutStrategy.getPosition() == null || breakOutStrategy.getPosition().getDirection() == Direction.FLAT);

			runner.runClose(0.9321);
			Assert.assertTrue(breakOutStrategy.getPosition() == null || breakOutStrategy.getPosition().getDirection() == Direction.FLAT);

			runner.runClose(0.9205);
			Assert.assertTrue(breakOutStrategy.getPosition() == null || breakOutStrategy.getPosition().getDirection() == Direction.FLAT);

			// cross below BB
			runner.runClose(0.8000);
			Assert.assertTrue(breakOutStrategy.getPosition() != null && breakOutStrategy.getPosition().getDirection() == Direction.LONG);

			// stay below SMA
			runner.runClose(0.9053);
			Assert.assertTrue(breakOutStrategy.getPosition() != null && breakOutStrategy.getPosition().getDirection() == Direction.LONG);

			// ******************************
			// cross above SMA and BB
			runner.runClose(1.2000);
			Assert.assertTrue(breakOutStrategy.getPosition() != null && breakOutStrategy.getPosition().getDirection() == Direction.SHORT);
			// ******************************

			// stay above SMA
			runner.runClose(1.1000);
			Assert.assertTrue(breakOutStrategy.getPosition() != null && breakOutStrategy.getPosition().getDirection() == Direction.SHORT);

			// cross below SMA
			runner.runClose(0.9104);
			Assert.assertTrue(breakOutStrategy.getPosition() == null || breakOutStrategy.getPosition().getDirection() == Direction.FLAT);

			// cross below BB
			runner.runClose(0.7000);
			Assert.assertTrue(breakOutStrategy.getPosition() != null && breakOutStrategy.getPosition().getDirection() == Direction.LONG);

			// stay below SMA
			runner.runClose(0.9104);
			Assert.assertTrue(breakOutStrategy.getPosition() != null && breakOutStrategy.getPosition().getDirection() == Direction.LONG);

			// cross above SMA
			runner.runClose(1.06000);
			Assert.assertTrue(breakOutStrategy.getPosition() == null || breakOutStrategy.getPosition().getDirection() == Direction.FLAT);

			// cross above BB
			runner.runClose(1.12000);
			Assert.assertTrue(breakOutStrategy.getPosition() != null && breakOutStrategy.getPosition().getDirection() == Direction.SHORT);

			// ******************************
			// cross below SMA and BB
			runner.runClose(0.7000);
			Assert.assertTrue(breakOutStrategy.getPosition() != null && breakOutStrategy.getPosition().getDirection() == Direction.LONG);
			// ******************************
		} finally {
			breakOutStrategy.onEnd();
			Assert.assertTrue(breakOutStrategy.getPosition() == null || breakOutStrategy.getPosition().getDirection() == Direction.FLAT);
		}
    }

    /**
     * BDD test without window.
     */
    @Test
    public void testStrategyWithoutWindow()
    {
		final Simulator simulator = new SimulatorImpl();
		final BreakOutStrategy breakOutStrategy = new BreakOutStrategy(simulator);

		final StrategyRunner runner = new StrategyRunner(breakOutStrategy, simulator, 0);
		
		breakOutStrategy.onBegin(1000000);
		Assert.assertTrue(breakOutStrategy.getPosition() == null || breakOutStrategy.getPosition().getDirection() == Direction.FLAT);
		try {
	        runner.runClose(0.9271);
	        Assert.assertTrue(breakOutStrategy.getPosition() == null || breakOutStrategy.getPosition().getDirection() == Direction.FLAT);
	        runner.runClose(0.9271);
       		runner.runClose(0.9507);
			runner.runClose(0.9575);
			runner.runClose(0.9467);
			runner.runClose(0.9437);
			runner.runClose(0.9366);
			runner.runClose(0.9521);
			runner.runClose(0.9508);
			runner.runClose(0.9431);
			runner.runClose(0.9421);
			runner.runClose(0.936);
			runner.runClose(0.9424);
			runner.runClose(0.9346);
			runner.runClose(0.9383);
			runner.runClose(0.9346);
			runner.runClose(0.9239);
			runner.runClose(0.9244);
			runner.runClose(0.9243);
			runner.runClose(0.917);
			runner.runClose(0.9262);
			runner.runClose(0.9374);
			runner.runClose(0.9374);
			runner.runClose(0.9357);
			runner.runClose(0.938);
			runner.runClose(0.9311);
			runner.runClose(0.9285);
			runner.runClose(0.9182);
			runner.runClose(0.9253);
			Assert.assertTrue(breakOutStrategy.getPosition() == null || breakOutStrategy.getPosition().getDirection() == Direction.FLAT);

			runner.runClose(0.9321);
			Assert.assertTrue(breakOutStrategy.getPosition() == null || breakOutStrategy.getPosition().getDirection() == Direction.FLAT);

			runner.runClose(0.9205);
			Assert.assertTrue(breakOutStrategy.getPosition() == null || breakOutStrategy.getPosition().getDirection() == Direction.FLAT);

			// cross below BB
			runner.runClose(0.8000);
			Assert.assertTrue(breakOutStrategy.getPosition() != null && breakOutStrategy.getPosition().getDirection() == Direction.LONG);

			// stay below SMA
			runner.runClose(0.9053);
			Assert.assertTrue(breakOutStrategy.getPosition() != null && breakOutStrategy.getPosition().getDirection() == Direction.LONG);

			// ******************************
			// cross above SMA and BB
			runner.runClose(1.2000);
			Assert.assertTrue(breakOutStrategy.getPosition() != null && breakOutStrategy.getPosition().getDirection() == Direction.SHORT);
			// ******************************

			// stay above SMA
			runner.runClose(1.1000);
			Assert.assertTrue(breakOutStrategy.getPosition() != null && breakOutStrategy.getPosition().getDirection() == Direction.SHORT);

			// cross below SMA
			runner.runClose(0.9104);
			Assert.assertTrue(breakOutStrategy.getPosition() == null || breakOutStrategy.getPosition().getDirection() == Direction.FLAT);

			// cross below BB
			runner.runClose(0.7000);
			Assert.assertTrue(breakOutStrategy.getPosition() != null && breakOutStrategy.getPosition().getDirection() == Direction.LONG);

			// stay below SMA
			runner.runClose(0.9104);
			Assert.assertTrue(breakOutStrategy.getPosition() != null && breakOutStrategy.getPosition().getDirection() == Direction.LONG);

			// cross above SMA
			runner.runClose(1.0500);
			Assert.assertTrue(breakOutStrategy.getPosition() == null || breakOutStrategy.getPosition().getDirection() == Direction.FLAT);

			// cross above BB
			runner.runClose(1.1200);
			Assert.assertTrue(breakOutStrategy.getPosition() != null && breakOutStrategy.getPosition().getDirection() == Direction.SHORT);

			// ******************************
			// cross below SMA and BB
			runner.runClose(0.7000);
			Assert.assertTrue(breakOutStrategy.getPosition() != null && breakOutStrategy.getPosition().getDirection() == Direction.LONG);
			// ******************************


			// cross above SMA and BB
			runner.runClose(1.2000);
			Assert.assertTrue(breakOutStrategy.getPosition() != null && breakOutStrategy.getPosition().getDirection() == Direction.SHORT);
		} finally {
			breakOutStrategy.onEnd();
			Assert.assertTrue(breakOutStrategy.getPosition() == null || breakOutStrategy.getPosition().getDirection() == Direction.FLAT);
		}
    }

}

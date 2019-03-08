package ch.brunostuessy.algo.breakout;

import java.util.stream.DoubleStream;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math.stat.descriptive.StatisticalSummary;
import org.apache.commons.math.stat.descriptive.SummaryStatistics;

import ch.algotrader.simulation.Simulator;

/**
 * Context class to run a strategy.
 * With window size > 0 uses DescriptiveStatistics and calls strategy after window is full.
 * With window size 0 uses SummaryStatistics for continuous mode and calls strategy after first close price.
 * 
 * @author Bruno StüssiW
 *
 */
public final class StrategyRunner {

	private final Strategy strategy;
	private final Simulator simulator;
	private final int windowSize;
	
	private final DescriptiveStatistics windowCloseStats = new DescriptiveStatistics();
	private final SummaryStatistics infiniteCloseStats = new SummaryStatistics();

	
	public StrategyRunner(final Strategy strategy, final Simulator simulator, final int windowSize) {
		this.strategy = strategy;
		this.simulator = simulator;
		this.windowSize = windowSize;
		if (windowSize > 0) {
			windowCloseStats.setWindowSize(this.windowSize);
		}
	}

	
	/**
	 * Runs a strategy streaming close prices into it including maintaining current price of simulator
	 * and updating the close price statistics.
	 *  
	 * @param initialCashBalance
	 * @param strategy
	 * @param closePrices
	 * @param simulator
	 */
	public void runStrategy(final double initialCashBalance, final DoubleStream closePrices) {
		strategy.onBegin(initialCashBalance);
		try {
			closePrices.forEach(close -> runClose(close));
		} finally {
			strategy.onEnd();
		}		
	}


	protected void runClose(double close) {
		simulator.setCurrentPrice(close);

		updateCloseStatistics(close);
		if (!areCloseStatisticsAvailable()) { return; }
		
		strategy.onClose(close, getCloseStatistics());
	}

	
	private StatisticalSummary getCloseStatistics() {
		if (windowSize > 0) {
			return windowCloseStats;
		} else {
			return infiniteCloseStats;
		}
	}

	private void updateCloseStatistics(final double close) {
		if (windowSize > 0) {
			windowCloseStats.addValue(close);
		} else {
			infiniteCloseStats.addValue(close);
		}
	}

	protected boolean areCloseStatisticsAvailable() {
		if (windowSize > 0) {
			return windowCloseStats.getN() >= windowSize;
		} else {
			return infiniteCloseStats.getN() >= 1;
		}
	}
	
}

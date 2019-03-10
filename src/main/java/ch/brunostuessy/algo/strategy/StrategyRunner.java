package ch.brunostuessy.algo.strategy;

import java.util.Objects;
import java.util.stream.DoubleStream;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math.stat.descriptive.StatisticalSummary;
import org.apache.commons.math.stat.descriptive.SummaryStatistics;

import ch.algotrader.simulation.Simulator;

/**
 * Context class to run a strategy. With window size > 0 uses
 * DescriptiveStatistics and calls strategy after window is full. With window
 * size 0 uses SummaryStatistics for continuous mode and calls strategy after
 * first close price.
 * 
 * @author Bruno Stüssi
 *
 */
public final class StrategyRunner<S extends Enum<S>> {

	private final Strategy<S> strategy;
	private final Simulator simulator;
	private final int windowSize;

	private final DescriptiveStatistics windowCloseStats = new DescriptiveStatistics();
	private final SummaryStatistics infiniteCloseStats = new SummaryStatistics();

	public StrategyRunner(final Strategy<S> strategy, final Simulator simulator, final int windowSize) {
		Objects.requireNonNull(strategy, "strategy is null!");
		Objects.requireNonNull(simulator, "simulator is null!");
		this.strategy = strategy;
		this.simulator = simulator;
		this.windowSize = windowSize;
		if (windowSize > 0) {
			windowCloseStats.setWindowSize(this.windowSize);
		}
	}

	/**
	 * Runs a strategy mapping close prices to signals and streaming them into it,
	 * including maintaining current price of simulator and updating the close price
	 * statistics.
	 * 
	 * @param initialCashBalance
	 * @param strategy
	 * @param closePrices
	 * @param simulator
	 */
	public void runStrategy(final double initialCashBalance, final DoubleStream closePrices) {
		strategy.onBegin(initialCashBalance);
		try {
			applyClosePrices(closePrices);
		} finally {
			strategy.onEnd();
		}
	}

	/**
	 * Helper method intended for testing.
	 * 
	 * @param close
	 */
	public void applyClose(double close) {
		applyClosePrices(DoubleStream.of(close));
	}

	private void applyClosePrices(final DoubleStream closePrices) {
		closePrices.filter(close -> {
			simulator.setCurrentPrice(close);

			updateCloseStatistics(close);
			return areCloseStatisticsAvailable();
		}).mapToObj(close -> {
			return strategy.mapCloseToSignal(close, getCloseStatistics());
		}).distinct().forEachOrdered(signal -> {
			strategy.onSignal(signal);
		});
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

	private boolean areCloseStatisticsAvailable() {
		if (windowSize > 0) {
			return windowCloseStats.getN() >= windowSize;
		} else {
			return infiniteCloseStats.getN() >= 1;
		}
	}

}

package ch.brunostuessy.algo.strategy;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
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

	private final Simulator simulator;
	private final Strategy<S> strategy;

	private final int windowSize;
	private final DescriptiveStatistics windowPriceStats = new DescriptiveStatistics();
	private final SummaryStatistics infinitePriceStats = new SummaryStatistics();

	private final boolean useLookaheadPrice;

	private double lastPrice;
	private DistinctLastFilter<S> distinctLastSignalFilter;

	private final static class DistinctLastFilter<V> implements Predicate<V> {

		private final AtomicReference<V> lastValue = new AtomicReference<V>();

		@Override
		public boolean test(final V newValue) {
			final V oldValue = lastValue.getAndSet(newValue);
			return !Objects.equals(newValue, oldValue);
		}

	}

	public StrategyRunner(final Strategy<S> strategy, final Simulator simulator, final int windowSize,
			final boolean useLookaheadPrice) {
		Objects.requireNonNull(strategy, "strategy is null!");
		Objects.requireNonNull(simulator, "simulator is null!");
		this.strategy = strategy;
		this.simulator = simulator;
		this.windowSize = windowSize;
		if (windowSize > 0) {
			windowPriceStats.setWindowSize(this.windowSize);
		}
		this.useLookaheadPrice = useLookaheadPrice;

		lastPrice = Double.NaN;
		distinctLastSignalFilter = new DistinctLastFilter<S>();
		distinctLastSignalFilter.test(strategy.mapPriceToSignal(Double.NaN, null));
	}

	/**
	 * Runs a strategy mapping and reducing prices to signals and streaming them
	 * into it, including maintaining current price of simulator and updating the
	 * price statistics.
	 * 
	 * @param initialCashBalance
	 * @param prices
	 */
	public void runStrategy(final double initialCashBalance, final DoubleStream prices) {
		strategy.onBegin(initialCashBalance);
		try {
			applyPrices(prices);
		} finally {
			strategy.onEnd();
		}
	}

	/**
	 * Helper method intended for testing.
	 * 
	 * @param price
	 */
	public void applyPrice(double price) {
		try (final DoubleStream prices = DoubleStream.of(price)) {
			applyPrices(prices);
		}
	}

	private void applyPrices(final DoubleStream prices) {
		prices.peek(price -> {
			simulator.setCurrentPrice(price);
		}).map(price -> { // use lookahead or lookback price
			if (useLookaheadPrice) {
				final double previousPrice = lastPrice;
				lastPrice = price;
				return previousPrice;
			} else {
				return price;
			}
		}).peek(price -> {
			updatePriceStatistics(price);
		}).filter(price -> {
			return arePriceStatisticsAvailable();
		}).mapToObj(price -> {
			return strategy.mapPriceToSignal(price, getPriceStatistics());
		}).filter(signal -> {
			return distinctLastSignalFilter.test(signal);
		}).forEachOrdered(signal -> {
			strategy.onSignal(signal);
		});
	}

	private StatisticalSummary getPriceStatistics() {
		if (windowSize > 0) {
			return windowPriceStats;
		} else {
			return infinitePriceStats;
		}
	}

	private void updatePriceStatistics(final double price) {
		if (!Double.isFinite(price)) {
			return;
		}

		if (windowSize > 0) {
			windowPriceStats.addValue(price);
		} else {
			infinitePriceStats.addValue(price);
		}
	}

	private boolean arePriceStatisticsAvailable() {
		if (windowSize > 0) {
			return windowPriceStats.getN() >= windowSize;
		} else {
			return infinitePriceStats.getN() >= 1;
		}
	}

}

package ch.brunostuessy.algo.strategy;

import java.util.Objects;
import java.util.stream.DoubleStream;

import ch.algotrader.simulation.Simulator;
import ch.brunostuessy.algo.function.DistinctUntilChangedFilter;

/**
 * Context class to run a strategy.
 *
 * @author Bruno Stüssi
 *
 * @param <P> the type representing the price statistics
 * @param <S> the type representing a signal
 */
public final class StrategyRunner<P, S> {

	private final Simulator simulator;
	private final Strategy<P, S> strategy;

	private final boolean useLookaheadPrice;

	private double lastPrice;
	private DistinctUntilChangedFilter<S> distinctUntilChangedSignalFilter;

	public StrategyRunner(final Strategy<P, S> strategy, final Simulator simulator, final boolean useLookaheadPrice) {
		Objects.requireNonNull(strategy, "strategy is null!");
		Objects.requireNonNull(simulator, "simulator is null!");
		this.strategy = strategy;
		this.simulator = simulator;
		this.useLookaheadPrice = useLookaheadPrice;

		lastPrice = Double.NaN;
		distinctUntilChangedSignalFilter = new DistinctUntilChangedFilter<S>();
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
		}).mapToObj(price -> {
			return strategy.mapPriceToPriceStats(price);
		}).map(priceStats -> {
			return strategy.mapPriceStatsToSignal(priceStats);
		}).filter(signal -> {
			return distinctUntilChangedSignalFilter.test(signal);
		}).forEachOrdered(signal -> {
			strategy.onSignal(signal);
		});
	}

}

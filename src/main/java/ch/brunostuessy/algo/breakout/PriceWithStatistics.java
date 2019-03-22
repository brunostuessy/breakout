package ch.brunostuessy.algo.breakout;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math.stat.descriptive.StatisticalSummary;
import org.apache.commons.math.stat.descriptive.SummaryStatistics;

/**
 * A price statistics maintaining the last price and its statistics.
 * 
 * With window size > 0 uses DescriptiveStatistics and validates when window is
 * full. With window size 0 uses SummaryStatistics for continuous mode and
 * validates after first price.
 * 
 * @author Bruno Stüssi
 *
 */
public final class PriceWithStatistics {

	private double lastValue;

	private final int windowSize;
	private final DescriptiveStatistics windowPriceStats;
	private final SummaryStatistics infinitePriceStats;

	public PriceWithStatistics(final int windowSize) {
		lastValue = Double.NaN;
		this.windowSize = windowSize;
		windowPriceStats = this.windowSize > 0 ? new DescriptiveStatistics(this.windowSize) : null;
		infinitePriceStats = this.windowSize > 0 ? null : new SummaryStatistics();
	}

	public double getLast() {
		return lastValue;
	}

	public StatisticalSummary getStatistics() {
		if (windowSize > 0) {
			return windowPriceStats;
		} else {
			return infinitePriceStats;
		}
	}

	public boolean isValid() {
		if (windowSize > 0) {
			return windowPriceStats.getN() >= windowSize;
		} else {
			return infinitePriceStats.getN() >= 1;
		}
	}

	public void addValue(double value) {
		if (!Double.isFinite(value)) {
			return;
		}

		lastValue = value;
		if (windowSize > 0) {
			windowPriceStats.addValue(value);
		} else {
			infinitePriceStats.addValue(value);
		}
	}

}

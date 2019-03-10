package ch.brunostuessy.algo.ta;

import org.apache.commons.math.stat.descriptive.StatisticalSummary;

public final class TAUtils {

	public static BandOrientation calculateBollingerBandOrientation(final double price,
			final StatisticalSummary priceStats) {
		if (!Double.isFinite(price) || priceStats == null || priceStats.getN() < 1) {
			return BandOrientation.INVALID;
		}

		final double movingAverage = priceStats.getMean();
		final double factor = 2.0;
		final double stddev = priceStats.getStandardDeviation();
		final double bollingerUpper = movingAverage + factor * stddev;
		final double bollingerLower = movingAverage - factor * stddev;

		if (price < bollingerLower) {
			return BandOrientation.BELOWLOWER;
		} else if (price > bollingerUpper) {
			return BandOrientation.ABOVEUPPER;
		} else if (price < movingAverage) {
			return BandOrientation.BELOWMIDDLE;
		} else if (price > movingAverage) {
			return BandOrientation.ABOVEMIDDLE;
		} else {
			return BandOrientation.ONMIDDLE;
		}
	}

	private TAUtils() {
	}
}

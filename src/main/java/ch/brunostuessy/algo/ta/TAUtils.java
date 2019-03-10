package ch.brunostuessy.algo.ta;

import org.apache.commons.math.stat.descriptive.StatisticalSummary;

public final class TAUtils {

	public static BandOrientation calculateBollingerBandOrientation(final double value, final StatisticalSummary valueStats) {
		if (value == Double.NaN || valueStats == null || valueStats.getN() < 1) {
			return BandOrientation.UNKNOWN;
		}

		final double movingAverage = valueStats.getMean();
		final double factor = 2.0;
		final double stddev = valueStats.getStandardDeviation();
		final double bollingerUpper = movingAverage + factor * stddev;
		final double bollingerLower = movingAverage - factor * stddev;

		if (value < bollingerLower) {
			return BandOrientation.BELOWLOWER;
		} else if (value > bollingerUpper) {
			return BandOrientation.ABOVEUPPER;
		} else if (value < movingAverage) {
			return BandOrientation.BELOWMIDDLE;
		} else if (value > movingAverage) {
			return BandOrientation.ABOVEMIDDLE;
		} else {
			return BandOrientation.ONMIDDLE;
		}
	}

	private TAUtils() {}
}

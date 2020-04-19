package ch.brunostuessy.algo.breakout;

import ch.brunostuessy.algo.strategy.PositionSignal;
import ch.brunostuessy.algo.strategy.Strategy;
import ch.brunostuessy.algo.ta.BandOrientation;
import ch.brunostuessy.algo.ta.TAUtils;

/**
 * Implements a BreakOut Strategy against a Bollinger Band.
 * 
 * @author Bruno Stüssi
 *
 */
public final class BreakOutStrategy implements Strategy<PriceWithStatistics, BandOrientation> {

	private final PriceWithStatistics priceWithStatistics;
	private final double stddevFactor;

	private BandOrientation bandOrientation;

	public BreakOutStrategy(final int windowSize, final double stddevFactor) {
		priceWithStatistics = new PriceWithStatistics(windowSize);
		this.stddevFactor = stddevFactor;
		bandOrientation = mapPriceStatsToSignal(priceWithStatistics);
	}

	/**
	 * Called every time when a new reference price is available. Converts the price
	 * to a price statistics. For this strategy the reference price is supposed to
	 * be the close price of a candle.
	 * 
	 * @param price
	 */
	@Override
	public PriceWithStatistics mapPriceToPriceStats(final double price) {
		priceWithStatistics.addValue(price);
		return priceWithStatistics;
	}

	/**
	 * Called every time when a new reference price is available. Converts the price
	 * statistics to a signal. For this strategy the reference price is supposed to
	 * be the close price of a candle.
	 * 
	 * @param priceStats
	 */
	@Override
	public BandOrientation mapPriceStatsToSignal(final PriceWithStatistics priceStats) {
		bandOrientation = priceStats.isValid()
				? TAUtils.calculateBollingerBandOrientation(priceStats.getLast(), priceStats.getStatistics(),
						stddevFactor)
				: BandOrientation.INVALID;

		return bandOrientation;
	}

	/**
	 * Called every time when a new signal is available. Converts the signal to a
	 * position signal.
	 * 
	 * @param signal
	 */
	@Override
	public PositionSignal mapSignalToPositionSignal(final BandOrientation signal) {
		PositionSignal positionSignal;

		switch (bandOrientation) {
		case BELOWLOWER:
			positionSignal = PositionSignal.OPENLONG;
			break;
		case ABOVEUPPER:
			positionSignal = PositionSignal.OPENSHORT;
			break;
		case BELOWMIDDLE:
			positionSignal = PositionSignal.CLOSESHORT;
			break;
		case ABOVEMIDDLE:
			positionSignal = PositionSignal.CLOSELONG;
			break;
		case ONMIDDLE:
			// edge case, do nothing
			positionSignal = PositionSignal.NONE;
			break;
		case INVALID:
		default:
			positionSignal = PositionSignal.INVALID;
		}

		return positionSignal;
	}

	protected BandOrientation getBandOrientation() {
		return bandOrientation;
	}

}

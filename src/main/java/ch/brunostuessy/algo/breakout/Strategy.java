package ch.brunostuessy.algo.breakout;

import org.apache.commons.math.stat.descriptive.StatisticalSummary;

/**
 * Interface to define the calling contract for a strategy.
 * 
 * @author Bruno Stüssi
 *
 */
public interface Strategy {

	/**
	 * Called once at the beginning to initialize cash balance.
	 * 
	 * @param initialCashBalance
	 */
	public void onBegin(final double initialCashBalance);

	/**
	 * Called every time when a new close price is available. Here signal detection
	 * and order and position management is supposed to take place in a continuous
	 * manner.
	 * 
	 * @param close
	 * @param closeStats
	 */
	public void onClose(final double close, final StatisticalSummary closeStats);

	/**
	 * Called once at the end to cleanup like closing eventual positions.
	 */
	public void onEnd();

}

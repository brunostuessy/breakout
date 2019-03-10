package ch.brunostuessy.algo.strategy;

import org.apache.commons.math.stat.descriptive.StatisticalSummary;

/**
 * Interface to define the calling contract for a strategy.
 * 
 * @author Bruno Stüssi
 *
 */
public interface Strategy<T extends Enum<T>> {

	/**
	 * Called every time when a new close price is available. Converts a close price
	 * to a signal.
	 * 
	 * @param close
	 * @param closeStats
	 */
	public T mapCloseToSignal(final double close, final StatisticalSummary closeStats);

	/**
	 * Called once at the beginning to initialize cash balance.
	 * 
	 * @param initialCashBalance
	 */
	public void onBegin(final double initialCashBalance);

	/**
	 * Called every time when a new signal is available. Here order and position
	 * management is supposed to take place in a continuous manner.
	 * 
	 * @param signal
	 */
	public void onSignal(final T signal);

	/**
	 * Called once at the end to cleanup like closing eventual positions.
	 */
	public void onEnd();

}

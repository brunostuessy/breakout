package ch.brunostuessy.algo.strategy;

/**
 * Interface to define the calling contract for a strategy.
 *
 * @author Bruno Stüssi
 *
 * @param <P> the type representing the price statistics
 * @param <S> the type representing a signal
 */
public interface Strategy<P, S> {

	/**
	 * Called every time when a new reference price is available. Converts the price
	 * to a price statistics.
	 * 
	 * @param price
	 */
	public P mapPriceToPriceStats(final double price);

	/**
	 * Called every time when a new reference price is available. Converts the price
	 * statistics to a signal.
	 * 
	 * @param priceStats
	 */
	public S mapPriceStatsToSignal(final P priceStats);

	/**
	 * Called every time when a new signal is available. Converts the signal to a
	 * position signal.
	 * 
	 * @param signal
	 */
	public PositionSignal mapSignalToPositionSignal(final S signal);

}

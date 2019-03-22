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
	 * Called once at the beginning to initialize cash balance.
	 * 
	 * @param initialCashBalance
	 */
	public void onBegin(final double initialCashBalance);

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
	 * Called every time when a new signal is available. Here order and position
	 * management is supposed to take place in a continuous manner.
	 * 
	 * @param signal
	 */
	public void onSignal(final S signal);

	/**
	 * Called once at the end to cleanup like closing eventual positions.
	 */
	public void onEnd();

}

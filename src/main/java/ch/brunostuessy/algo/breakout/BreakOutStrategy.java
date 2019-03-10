package ch.brunostuessy.algo.breakout;

import java.util.Objects;

import org.apache.commons.math.stat.descriptive.StatisticalSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.algotrader.entity.Position;
import ch.algotrader.entity.trade.MarketOrder;
import ch.algotrader.enumeration.Direction;
import ch.algotrader.enumeration.Side;
import ch.algotrader.simulation.Simulator;
import ch.brunostuessy.algo.strategy.Strategy;
import ch.brunostuessy.algo.ta.BandOrientation;
import ch.brunostuessy.algo.ta.TAUtils;

/**
 * Implements a BreakOut Strategy against a Bollinger Band.
 * 
 * @author Bruno Stüssi
 *
 */
public final class BreakOutStrategy implements Strategy<BandOrientation> {

	private static Logger logger = LogManager.getLogger(BreakOutStrategy.class.getName());

	private final Simulator simulator;

	private BandOrientation bandOrientation;

	public BreakOutStrategy(final Simulator simulator) {
		Objects.requireNonNull(simulator, "simulator is null!");
		this.simulator = simulator;
		bandOrientation = mapPriceToSignal(Double.NaN, null);
	}

	/**
	 * Called every time when a new reference price is available. Converts a
	 * reference price to a signal. For this strategy the reference price is
	 * supposed to be the close price of a candle.
	 * 
	 * @param price
	 * @param priceStats
	 */
	@Override
	public BandOrientation mapPriceToSignal(final double price, final StatisticalSummary priceStats) {
		return TAUtils.calculateBollingerBandOrientation(price, priceStats);
	}

	/**
	 * Called once at the beginning to initialize cash balance.
	 * 
	 * @param initialCashBalance
	 */
	@Override
	public void onBegin(final double initialCashBalance) {
		simulator.setCashBalance(initialCashBalance);
	}

	/**
	 * Called every time when a new signal is available. Here order and position
	 * management is supposed to take place in a continuous manner. Position is
	 * first closed if any and then opened to support price spikes, e.g. close SHORT
	 * and open LONG in one step.
	 * 
	 * @param signal
	 */
	@Override
	public void onSignal(final BandOrientation signal) {
		bandOrientation = signal;

		switch (getBandOrientation()) {
		case BELOWLOWER:
			onCloseBelowBollingerLower();
			break;
		case ABOVEUPPER:
			onCloseAboveBollingerUpper();
			break;
		case BELOWMIDDLE:
			onCloseBelowBollingerMiddle();
			break;
		case ABOVEMIDDLE:
			onCloseAboveBollingerMiddle();
			break;
		case ONMIDDLE:
			// edge case, do nothing
			break;
		case INVALID:
			onLeaveMarket();
			break;
		default:
		}
	}

	/**
	 * Called once at the end to close eventual positions.
	 */
	@Override
	public void onEnd() {
		onLeaveMarket();
	}

	/**
	 * Opens LONG position if none including closing any SHORT position ahead.
	 */
	private void onCloseBelowBollingerLower() {
		Direction positionDirection = getPositionDirection();
		if (positionDirection == Direction.SHORT) {
			closePosition(Side.BUY);
			positionDirection = getPositionDirection();
		}
		if (positionDirection == Direction.FLAT) {
			openPosition(Side.BUY);
		}
	}

	/**
	 * Opens SHORT position if none including closing any LONG position ahead.
	 */
	private void onCloseAboveBollingerUpper() {
		Direction positionDirection = getPositionDirection();
		if (positionDirection == Direction.LONG) {
			closePosition(Side.SELL);
			positionDirection = getPositionDirection();
		}
		if (positionDirection == Direction.FLAT) {
			openPosition(Side.SELL);
		}
	}

	/**
	 * Closes LONG position if any.
	 */
	private void onCloseAboveBollingerMiddle() {
		final Direction positionDirection = getPositionDirection();
		if (positionDirection == Direction.LONG) {
			closePosition(Side.SELL);
		}
	}

	/**
	 * Closes SHORT position if any.
	 */
	private void onCloseBelowBollingerMiddle() {
		final Direction positionDirection = getPositionDirection();
		if (positionDirection == Direction.SHORT) {
			closePosition(Side.BUY);
		}
	}

	/**
	 * Closes any position if any.
	 */
	private void onLeaveMarket() {
		final Direction positionDirection = getPositionDirection();
		if (positionDirection == Direction.SHORT) {
			closePosition(Side.BUY);
		} else if (positionDirection == Direction.LONG) {
			closePosition(Side.SELL);
		}
	}

	protected BandOrientation getBandOrientation() {
		return bandOrientation;
	}

	protected Direction getPositionDirection() {
		final Position position = simulator.getPosition();
		return position != null ? position.getDirection() : Direction.FLAT;
	}

	private void openPosition(final Side side) {
		final long quantity = Math.round(simulator.getCashBalance() - 0.5); // round down
		if (quantity > 0) {
			simulator.sendOrder(new MarketOrder(side, quantity));
		}
	}

	private void closePosition(final Side side) {
		final Position position = simulator.getPosition();
		final long quantity = position != null ? Math.abs(position.getQuantity()) : 0;
		if (quantity > 0) {
			simulator.sendOrder(new MarketOrder(side, quantity));
			logger.info("closed position: cash balance is " + simulator.getCashBalance());
		}
	}

}

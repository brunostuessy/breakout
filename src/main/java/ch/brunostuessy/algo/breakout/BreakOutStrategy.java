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

/**
 * Implements a BreakOut Strategy against a Bollinger Band.
 * 
 * @author Bruno Stüssi
 *
 */
public final class BreakOutStrategy implements Strategy {

	private static Logger logger = LogManager.getLogger(BreakOutStrategy.class.getName());

	private final Simulator simulator;

	private BollingerLocation bollingerLocation = BollingerLocation.ONMIDDLE;

	public BreakOutStrategy(final Simulator simulator) {
		Objects.requireNonNull(simulator, "simulator is null!");
		this.simulator = simulator;
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
	 * Called every time when a new close price is available. Detects signals and
	 * performs order management. Position is first closed if any and then opened to
	 * support price spikes, e.g. close SHORT and open LONG in one step.
	 * 
	 * @param close
	 * @param closeStats
	 */
	@Override
	public void onClose(final double close, final StatisticalSummary closeStats) {
		if (closeStats.getN() < 1) {
			return;
		}

		updateBollingerLocation(close, closeStats);

		switch (getBollingerLocation()) {
		case BELOWLOWER:
			onCloseBelowBollingerLower();
			break;
		case ABOVEUPPER:
			onCloseAboveBollingerUpper();
			break;
		case BELOWMIDDLE:
			onCloseWithinBollingerBelowMovingAverage();
			break;
		case ABOVEMIDDLE:
			onCloseWithinBollingerAboveMovingAverage();
			break;
		case ONMIDDLE:
			onCloseWithinBollingerOnMovingAverage();
			break;
		default:
		}
	}

	/**
	 * Called once at the end to close eventual positions.
	 */
	@Override
	public void onEnd() {
		final Direction positionDirection = getPositionDirection();
		if (positionDirection == Direction.SHORT) {
			closePosition(Side.BUY);
		} else if (positionDirection == Direction.LONG) {
			closePosition(Side.SELL);
		}
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
	private void onCloseWithinBollingerAboveMovingAverage() {
		final Direction positionDirection = getPositionDirection();
		if (positionDirection == Direction.LONG) {
			closePosition(Side.SELL);
		}
	}

	private void onCloseWithinBollingerOnMovingAverage() {
	}

	/**
	 * Closes SHORT position if any.
	 */
	private void onCloseWithinBollingerBelowMovingAverage() {
		final Direction positionDirection = getPositionDirection();
		if (positionDirection == Direction.SHORT) {
			closePosition(Side.BUY);
		}
	}

	protected BollingerLocation getBollingerLocation() {
		return bollingerLocation;
	}

	private void updateBollingerLocation(final double close, final StatisticalSummary closeStats) {
		final double movingAverage = closeStats.getMean();
		final double factor = 2.0;
		final double stddev = closeStats.getStandardDeviation();
		final double bollingerUpper = movingAverage + factor * stddev;
		final double bollingerLower = movingAverage - factor * stddev;

		if (close < bollingerLower) {
			bollingerLocation = BollingerLocation.BELOWLOWER;
		} else if (close > bollingerUpper) {
			bollingerLocation = BollingerLocation.ABOVEUPPER;
		} else if (close < movingAverage) {
			bollingerLocation = BollingerLocation.BELOWMIDDLE;
		} else if (close > movingAverage) {
			bollingerLocation = BollingerLocation.ABOVEMIDDLE;
		} else {
			bollingerLocation = BollingerLocation.ONMIDDLE;
		}
	}

	protected Direction getPositionDirection() {
		final Position position = simulator.getPosition();
		return position != null ? position.getDirection() : Direction.FLAT;
	}

	private void openPosition(final Side side) {
		final long cashBalance = Math.round(simulator.getCashBalance() - 0.5);
		if (cashBalance > 0) {
			simulator.sendOrder(new MarketOrder(side, cashBalance));
		}
	}

	private void closePosition(final Side side) {
		final Position position = simulator.getPosition();
		simulator.sendOrder(new MarketOrder(side, Math.abs(position.getQuantity())));
		logger.info("closed position: cash balance is " + simulator.getCashBalance());
	}

}

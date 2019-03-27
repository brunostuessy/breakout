package ch.brunostuessy.algo.strategy;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.algotrader.entity.Position;
import ch.algotrader.entity.trade.MarketOrder;
import ch.algotrader.enumeration.Direction;
import ch.algotrader.enumeration.Side;
import ch.algotrader.simulation.Simulator;
import reactor.core.publisher.Flux;

/**
 * Context class to run a strategy.
 *
 * @author Bruno Stüssi
 *
 * @param <P> the type representing the price statistics
 * @param <S> the type representing a signal
 */
public final class StrategyRunner<P, S> {

	private static Logger logger = LogManager.getLogger(StrategyRunner.class.getName());

	private final Simulator simulator;
	private final Strategy<P, S> strategy;

	private final boolean useLookaheadPrice;

	private AtomicReference<Double> lastPrice = new AtomicReference<>();

	public StrategyRunner(final Strategy<P, S> strategy, final Simulator simulator, final boolean useLookaheadPrice) {
		Objects.requireNonNull(strategy, "strategy is null!");
		Objects.requireNonNull(simulator, "simulator is null!");
		this.strategy = strategy;
		this.simulator = simulator;
		this.useLookaheadPrice = useLookaheadPrice;

		lastPrice.set(Double.NaN);
	}

	/**
	 * Runs a strategy mapping and reducing prices to signals and streaming them
	 * into it, including maintaining current price of simulator and updating the
	 * price statistics.
	 * 
	 * @param initialCashBalance
	 * @param prices
	 */
	public void runStrategy(final double initialCashBalance, final Flux<Double> prices) {
		prepareStrategy(initialCashBalance, prices).subscribe();
	}

	/**
	 * Builds the price stream execution pipeline. Position is first closed if any
	 * and then opened to support price spikes, e.g. close SHORT and open LONG in
	 * one step. At the end eventual positions are closed.
	 * 
	 * @param initialCashBalance
	 * @param prices
	 */
	public Flux<PositionSignal> prepareStrategy(final double initialCashBalance, final Flux<Double> prices) {
		simulator.setCashBalance(initialCashBalance);

		final Flux<Double> marketPrices = prices.doOnNext(price -> {
			simulator.setCurrentPrice(price);
		});
		final Flux<Double> tradePrices = !useLookaheadPrice ? marketPrices : marketPrices.map(price -> {
			return lastPrice.getAndSet(price);
		});
		return tradePrices.map(price -> {
			return strategy.mapPriceToPriceStats(price);
		}).map(priceStats -> {
			return strategy.mapPriceStatsToSignal(priceStats);
		}).distinctUntilChanged().map(signal -> {
			return strategy.mapSignalToPositionSignal(signal);
		}).doOnNext(positionSignal -> {
			doApplyPositionSignal(positionSignal);
		}).doFinally(signalType -> {
			doCloseAllPositions();
		});
	}

	private void doApplyPositionSignal(final PositionSignal positionSignal) {
		switch (positionSignal) {
		case OPENLONG:
			doOpenLongPosition();
			break;
		case OPENSHORT:
			doOpenShortPosition();
			break;
		case CLOSESHORT:
			doCloseShortPosition();
			break;
		case CLOSELONG:
			doCloseLongPosition();
			break;
		case NONE:
			break;
		case INVALID:
			doCloseAllPositions();
			break;
		default:
		}
	}

	public Direction getPositionDirection() {
		final Position position = simulator.getPosition();
		return position != null ? position.getDirection() : Direction.FLAT;
	}

	/**
	 * Opens LONG position if none including closing any SHORT position ahead.
	 */
	private void doOpenLongPosition() {
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
	private void doOpenShortPosition() {
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
	private void doCloseLongPosition() {
		final Direction positionDirection = getPositionDirection();
		if (positionDirection == Direction.LONG) {
			closePosition(Side.SELL);
		}
	}

	/**
	 * Closes SHORT position if any.
	 */
	private void doCloseShortPosition() {
		final Direction positionDirection = getPositionDirection();
		if (positionDirection == Direction.SHORT) {
			closePosition(Side.BUY);
		}
	}

	/**
	 * Closes any position if any.
	 */
	private void doCloseAllPositions() {
		final Direction positionDirection = getPositionDirection();
		if (positionDirection == Direction.SHORT) {
			closePosition(Side.BUY);
		} else if (positionDirection == Direction.LONG) {
			closePosition(Side.SELL);
		}
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

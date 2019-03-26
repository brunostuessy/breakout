package ch.brunostuessy.algo.breakout;

import java.io.IOException;
import java.util.stream.DoubleStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.algotrader.simulation.Simulator;
import ch.algotrader.simulation.SimulatorImpl;
import ch.brunostuessy.algo.provider.CSVClosePriceDoubleStreamProvider;
import ch.brunostuessy.algo.strategy.Strategy;
import ch.brunostuessy.algo.strategy.StrategyRunner;
import ch.brunostuessy.algo.ta.BandOrientation;
import reactor.core.publisher.Flux;

/**
 * Runs BreakOutStrategy with close prices loaded from file EUR.USD.csv.
 * 
 * @author Bruno Stüssi
 *
 */
public final class BreakOutStrategySimulator {

	private static Logger logger = LogManager.getLogger(BreakOutStrategySimulator.class.getName());

	public static void main(String[] args) {
		final Simulator simulator = new SimulatorImpl();

		final Strategy<PriceWithStatistics, BandOrientation> breakOutStrategy = new BreakOutStrategy(30, 2.0);

		final double initialCashBalance = 1000000;
		final String candleFilePath = args != null && args.length == 1 ? args[0] : null;
		try (final DoubleStream closePrices = new CSVClosePriceDoubleStreamProvider().closePrices(candleFilePath)) {
			new StrategyRunner<PriceWithStatistics, BandOrientation>(breakOutStrategy, simulator, false)
					.runStrategy(initialCashBalance, Flux.fromStream(closePrices.boxed()));
		} catch (final Error | IOException | RuntimeException e) {
			logger.error("exception caught:", e);
		}
	}

	private BreakOutStrategySimulator() {
	}

}

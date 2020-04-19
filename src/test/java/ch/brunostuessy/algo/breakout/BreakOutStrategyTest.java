package ch.brunostuessy.algo.breakout;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import ch.algotrader.enumeration.Direction;
import ch.algotrader.simulation.SimulatorImpl;
import ch.brunostuessy.algo.strategy.StrategyRunner;
import ch.brunostuessy.algo.ta.BandOrientation;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

/**
 * Unit tests for BreakOutStrategy.
 */
public class BreakOutStrategyTest {

	/**
	 * BDD test with window size 30.
	 */
	@Test
	public void strategyWithWindowSize30UseLookback() {
		final BreakOutStrategy breakOutStrategy = new BreakOutStrategy(30, 2.0);

		final StrategyRunner<PriceWithStatistics, BandOrientation> runner = new StrategyRunner<>(
				breakOutStrategy, new SimulatorImpl(), false);

		assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
		assertEquals(Direction.FLAT, runner.getPositionDirection());

		final TestPublisher<Double> pricer = TestPublisher.create();
		StepVerifier.create(runner.prepareStrategy(1000000, pricer.flux().timeout(Duration.of(100, ChronoUnit.MILLIS)))) 

		.then(() -> pricer.next(0.9271))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.FLAT, runner.getPositionDirection());
    	})
		.then(() -> pricer.next(0.9271))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9575))
		.then(() -> pricer.next(0.9467))
		.then(() -> pricer.next(0.9437))
		.then(() -> pricer.next(0.9366))
		.then(() -> pricer.next(0.9521))
		.then(() -> pricer.next(0.9508))
		.then(() -> pricer.next(0.9431))
		.then(() -> pricer.next(0.9421))
		.then(() -> pricer.next(0.936))
		.then(() -> pricer.next(0.9424))
		.then(() -> pricer.next(0.9346))
		.then(() -> pricer.next(0.9383))
		.then(() -> pricer.next(0.9346))
		.then(() -> pricer.next(0.9239))
		.then(() -> pricer.next(0.9244))
		.then(() -> pricer.next(0.9243))
		.then(() -> pricer.next(0.917))
		.then(() -> pricer.next(0.9262))
		.then(() -> pricer.next(0.9374))
		.then(() -> pricer.next(0.9374))
		.then(() -> pricer.next(0.9357))
		.then(() -> pricer.next(0.938))
		.then(() -> pricer.next(0.9311))
		.then(() -> pricer.next(0.9285))
		.then(() -> pricer.next(0.9182))
		.then(() -> pricer.next(0.9253))

		// fill window
		.then(() -> pricer.next(0.9321))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.FLAT, runner.getPositionDirection());
    	})

		.then(() -> pricer.next(0.9205))

		// cross below BB
		.then(() -> pricer.next(0.8000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})

		// stay below SMA
		.then(() -> pricer.next(0.9053))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})

		// ******************************
		// cross above SMA and BB
		.then(() -> pricer.next(1.2000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.SHORT, runner.getPositionDirection());
    	})
		// ******************************

		// stay above SMA
		.then(() -> pricer.next(1.0500))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.SHORT, runner.getPositionDirection());
    	})

		// cross below SMA
		.then(() -> pricer.next(0.9104))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.FLAT, runner.getPositionDirection());
    	})

		// cross below BB
		.then(() -> pricer.next(0.7000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})

		// stay below SMA
		.then(() -> pricer.next(0.9104))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})

		// cross above SMA
		.then(() -> pricer.next(1.06000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.FLAT, runner.getPositionDirection());
    	})

		// cross above BB
		.then(() -> pricer.next(1.12000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.SHORT, runner.getPositionDirection());
    	})

		// ******************************
		// cross below SMA and BB
		.then(() -> pricer.next(0.7000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})
		// ******************************

	    .then(() -> pricer.complete())
	    .verifyComplete();

		assertEquals(Direction.FLAT, runner.getPositionDirection());
	}

	/**
	 * BDD test without window.
	 */
	@Test
	public void strategyWithoutWindowUseLookback() {
		final BreakOutStrategy breakOutStrategy = new BreakOutStrategy(0, 2.0);

		final StrategyRunner<PriceWithStatistics, BandOrientation> runner = new StrategyRunner<>(
				breakOutStrategy, new SimulatorImpl(), false);

		assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
		assertEquals(Direction.FLAT, runner.getPositionDirection());

		final TestPublisher<Double> pricer = TestPublisher.create();
		StepVerifier.create(runner.prepareStrategy(1000000, pricer.flux().timeout(Duration.of(100, ChronoUnit.MILLIS)))) 

		.then(() -> pricer.next(0.9507))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ONMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.FLAT, runner.getPositionDirection());
    	})
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))

		// fill window
		.then(() -> pricer.next(0.9321))

		.then(() -> pricer.next(0.9205))

		// cross below BB
		.then(() -> pricer.next(0.8000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})

		// stay below SMA
		.then(() -> pricer.next(0.9053))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})

		// ******************************
		// cross above SMA and BB
		.then(() -> pricer.next(1.2000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.SHORT, runner.getPositionDirection());
    	})
		// ******************************

		// stay above SMA
		.then(() -> pricer.next(1.0500))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.SHORT, runner.getPositionDirection());
    	})

		// cross below SMA
		.then(() -> pricer.next(0.9104))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.FLAT, runner.getPositionDirection());
    	})

		// cross below BB
		.then(() -> pricer.next(0.7000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})

		// stay below SMA
		.then(() -> pricer.next(0.9104))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})

		// cross above SMA
		.then(() -> pricer.next(1.06000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.FLAT, runner.getPositionDirection());
    	})

		// cross above BB
		.then(() -> pricer.next(1.12000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.SHORT, runner.getPositionDirection());
    	})

		// ******************************
		// cross below SMA and BB
		.then(() -> pricer.next(0.7000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})
		// ******************************

		// cross above SMA and BB
		.then(() -> pricer.next(1.2000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.SHORT, runner.getPositionDirection());
    	})

	    .then(() -> pricer.complete())
	    .verifyComplete();

		assertEquals(Direction.FLAT, runner.getPositionDirection());
	}

	/**
	 * BDD test with window size 30.
	 */
	@Test
	public void strategyWithWindowSize30UseLookahead() {
		final BreakOutStrategy breakOutStrategy = new BreakOutStrategy(30, 2.0);

		final StrategyRunner<PriceWithStatistics, BandOrientation> runner = new StrategyRunner<>(
				breakOutStrategy, new SimulatorImpl(), true);

		assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
		assertEquals(Direction.FLAT, runner.getPositionDirection());

		final TestPublisher<Double> pricer = TestPublisher.create();
		StepVerifier.create(runner.prepareStrategy(1000000, pricer.flux().timeout(Duration.of(100, ChronoUnit.MILLIS)))) 

		.then(() -> pricer.next(0.9271))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.FLAT, runner.getPositionDirection());
    	})
		.then(() -> pricer.next(0.9271))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9575))
		.then(() -> pricer.next(0.9467))
		.then(() -> pricer.next(0.9437))
		.then(() -> pricer.next(0.9366))
		.then(() -> pricer.next(0.9521))
		.then(() -> pricer.next(0.9508))
		.then(() -> pricer.next(0.9431))
		.then(() -> pricer.next(0.9421))
		.then(() -> pricer.next(0.936))
		.then(() -> pricer.next(0.9424))
		.then(() -> pricer.next(0.9346))
		.then(() -> pricer.next(0.9383))
		.then(() -> pricer.next(0.9346))
		.then(() -> pricer.next(0.9239))
		.then(() -> pricer.next(0.9244))
		.then(() -> pricer.next(0.9243))
		.then(() -> pricer.next(0.917))
		.then(() -> pricer.next(0.9262))
		.then(() -> pricer.next(0.9374))
		.then(() -> pricer.next(0.9374))
		.then(() -> pricer.next(0.9357))
		.then(() -> pricer.next(0.938))
		.then(() -> pricer.next(0.9311))
		.then(() -> pricer.next(0.9285))
		.then(() -> pricer.next(0.9182))
		.then(() -> pricer.next(0.9253))
		.then(() -> pricer.next(0.9321))

		// fill window
		// .then(() -> pricer.next(0.9321))
		.then(() -> pricer.next(0.9205))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.FLAT, runner.getPositionDirection());
    	})

		// .then(() -> pricer.next(0.9205))
		.then(() -> pricer.next(0.8000))

		// cross below BB
		// .then(() -> pricer.next(0.8000))
		.then(() -> pricer.next(0.9053))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})

		// stay below SMA
		// .then(() -> pricer.next(0.9053))
		.then(() -> pricer.next(1.2000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})

		// ******************************
		// cross above SMA and BB
		// .then(() -> pricer.next(1.2000))
		.then(() -> pricer.next(1.0500))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.SHORT, runner.getPositionDirection());
    	})
		// ******************************

		// stay above SMA
		// .then(() -> pricer.next(1.0500))
		.then(() -> pricer.next(0.9104))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.SHORT, runner.getPositionDirection());
    	})

		// cross below SMA
		// .then(() -> pricer.next(0.9104))
		.then(() -> pricer.next(0.7000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.FLAT, runner.getPositionDirection());
    	})

		// cross below BB
		// .then(() -> pricer.next(0.7000))
		.then(() -> pricer.next(0.9104))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})

		// stay below SMA
		// .then(() -> pricer.next(0.9104))
		.then(() -> pricer.next(1.06000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})

		// cross above SMA
		// .then(() -> pricer.next(1.06000))
		.then(() -> pricer.next(1.12000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.FLAT, runner.getPositionDirection());
    	})

		// cross above BB
		// .then(() -> pricer.next(1.12000))
		.then(() -> pricer.next(0.7000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.SHORT, runner.getPositionDirection());
    	})

		// ******************************
		// cross below SMA and BB
		// .then(() -> pricer.next(0.7000))
		.then(() -> pricer.next(1.0000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})
		// ******************************

	    .then(() -> pricer.complete())
	    .verifyComplete();

		assertEquals(Direction.FLAT, runner.getPositionDirection());
	}

	/**
	 * BDD test without window.
	 */
	@Test
	public void strategyWithoutWindowUseLookahead() {
		final BreakOutStrategy breakOutStrategy = new BreakOutStrategy(0, 2.0);

		final StrategyRunner<PriceWithStatistics, BandOrientation> runner = new StrategyRunner<>(
				breakOutStrategy, new SimulatorImpl(), true);

		assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
		assertEquals(Direction.FLAT, runner.getPositionDirection());
		
		final TestPublisher<Double> pricer = TestPublisher.create();
		StepVerifier.create(runner.prepareStrategy(1000000, pricer.flux().timeout(Duration.of(100, ChronoUnit.MILLIS)))) 
		
		.then(() -> pricer.next(0.9507))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.FLAT, runner.getPositionDirection());
    	})
		.then(() -> pricer.next(0.9507))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ONMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.FLAT, runner.getPositionDirection());
    	})
		// .then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))
		.then(() -> pricer.next(0.9507))

		// fill window
		// .then(() -> pricer.next(0.9321))
		.then(() -> pricer.next(0.9205))

		// .then(() -> pricer.next(0.9205))
		.then(() -> pricer.next(0.8000))

		// cross below BB
		// .then(() -> pricer.next(0.8000))
		.then(() -> pricer.next(0.9053))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})

		// stay below SMA
		// .then(() -> pricer.next(0.9053))
		.then(() -> pricer.next(1.2000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})

		// ******************************
		// cross above SMA and BB
		// .then(() -> pricer.next(1.2000))
		.then(() -> pricer.next(1.0500))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.SHORT, runner.getPositionDirection());
    	})
		// ******************************

		// stay above SMA
		// .then(() -> pricer.next(1.0500))
		.then(() -> pricer.next(0.9104))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.SHORT, runner.getPositionDirection());
    	})

		// cross below SMA
		// .then(() -> pricer.next(0.9104))
		.then(() -> pricer.next(0.7000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.FLAT, runner.getPositionDirection());
    	})

		// cross below BB
		// .then(() -> pricer.next(0.7000))
		.then(() -> pricer.next(0.9104))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})

		// stay below SMA
		// .then(() -> pricer.next(0.9104))
		.then(() -> pricer.next(1.06000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})

		// cross above SMA
		// .then(() -> pricer.next(1.06000))
		.then(() -> pricer.next(1.12000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ABOVEMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.FLAT, runner.getPositionDirection());
    	})

		// cross above BB
		// .then(() -> pricer.next(1.12000))
		.then(() -> pricer.next(0.7000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.SHORT, runner.getPositionDirection());
    	})

		// ******************************
		// cross below SMA and BB
		// .then(() -> pricer.next(0.7000))
		.then(() -> pricer.next(1.2000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})
		// ******************************

		// cross above SMA and BB
		// .then(() -> pricer.next(1.2000))
		.then(() -> pricer.next(1.0000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ABOVEUPPER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.SHORT, runner.getPositionDirection());
    	})

	    .then(() -> pricer.complete())
	    .verifyComplete();

		assertEquals(Direction.FLAT, runner.getPositionDirection());
	}

	/**
	 * BDD test with invalid close price.
	 */
	@Test
	public void strategyIgnoresInvalidClose() {
		final BreakOutStrategy breakOutStrategy = new BreakOutStrategy(0, 2.0);

		final StrategyRunner<PriceWithStatistics, BandOrientation> runner = new StrategyRunner<>(
				breakOutStrategy, new SimulatorImpl(), true);

		assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
		assertEquals(Direction.FLAT, runner.getPositionDirection());

		final TestPublisher<Double> pricer = TestPublisher.create();
		StepVerifier.create(runner.prepareStrategy(1000000, pricer.flux().timeout(Duration.of(100, ChronoUnit.MILLIS)))) 

	    .then(() -> pricer.next(0.9271))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.INVALID, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.FLAT, runner.getPositionDirection());
    	})
	    .then(() -> pricer.next(0.9271))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.ONMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.FLAT, runner.getPositionDirection());
    	})
	    .then(() -> pricer.next(0.9261, 0.9251, 0.9241))
	    .then(() -> pricer.next(0.1000, Double.POSITIVE_INFINITY))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWMIDDLE, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.FLAT, runner.getPositionDirection());
    	})
		// ignore non-finite price
	    .then(() -> pricer.next(0.2000))
	    .assertNext(ac -> {
			assertEquals(BandOrientation.BELOWLOWER, breakOutStrategy.getBandOrientation());
			assertEquals(Direction.LONG, runner.getPositionDirection());
    	})
	    .then(() -> pricer.complete())
	    .verifyComplete();
		
		assertEquals(Direction.FLAT, runner.getPositionDirection());
}

}

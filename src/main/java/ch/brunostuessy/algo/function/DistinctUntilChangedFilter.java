package ch.brunostuessy.algo.function;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

/**
 * A stateful filter predicate to test if a new value is distinct from the last
 * one. The initial value is null.
 * 
 * @author Bruno Stüssi
 *
 * @param <V>
 */
public final class DistinctUntilChangedFilter<V> implements Predicate<V> {

	private final AtomicReference<V> lastValue = new AtomicReference<V>();

	@Override
	public boolean test(final V newValue) {
		final V oldValue = lastValue.getAndSet(newValue);
		return !Objects.equals(newValue, oldValue);
	}

}
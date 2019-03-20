package com.ocba.collector_util.collectorImpl;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.ocba.collector_util.collectorInterface.CollectorFunctionConditions;
import com.ocba.collector_util.collectorInterface.MultiMapCollectors;

public class MultiMapCollectorsImpl<E, K, V> {
	private CollectorFunctionConditions condition;

	public MultiMapCollectorsImpl(CollectorFunctionConditions condition) {
		this.condition = condition;
	}

	/**
	 * key: feildName, value: feildName
	 *
	 * @param keyFeildName
	 * @param valueFeildName
	 * @param elements
	 * @return
	 */
	public InnerMultiMapCollectors setCondition(String keyFeildName, String valueFeildName, Iterable<E> elements) {
		return new InnerMultiMapCollectors(this.setKeyFunction(keyFeildName), this.setValueFunction(valueFeildName),
				elements);
	}

	/**
	 * key: feildName, value: function
	 *
	 * @param keyFeildName
	 * @param valueFunction
	 * @param elements
	 * @return
	 */
	public InnerMultiMapCollectors setCondition(String keyFeildName, Function<E, V> valueFunction,
			Iterable<E> elements) {
		return new InnerMultiMapCollectors(this.setKeyFunction(keyFeildName), this.setValueFunction(valueFunction),
				elements);
	}

	/**
	 * key: function, value: feildName
	 *
	 * @param keyFunction
	 * @param valueFeildName
	 * @param elements
	 * @return
	 */
	public InnerMultiMapCollectors setCondition(Function<E, K> keyFunction, String valueFeildName,
			Iterable<E> elements) {
		return new InnerMultiMapCollectors(this.setKeyFunction(keyFunction), this.setValueFunction(valueFeildName),
				elements);
	}

	/**
	 * key: function, value: function
	 *
	 * @param keyFunction
	 * @param valueFunction
	 * @param elements
	 * @return
	 */
	public InnerMultiMapCollectors setCondition(Function<E, K> keyFunction, Function<E, V> valueFunction,
			Iterable<E> elements) {
		return new InnerMultiMapCollectors(this.setKeyFunction(keyFunction), this.setValueFunction(valueFunction),
				elements);
	}

	/**
	 * key: function
	 *
	 * @param keyFunction
	 * @param elements
	 * @return
	 */
	public InnerMultiMapCollectors setCondition(Function<E, K> keyFunction, Iterable<E> elements) {
		return new InnerMultiMapCollectors(this.setKeyFunction(keyFunction), elements);
	}

	/**
	 * key: feildName
	 *
	 * @param keyFeildName
	 * @param elements
	 * @return
	 */
	public InnerMultiMapCollectors setCondition(String keyFeildName, Iterable<E> elements) {
		return new InnerMultiMapCollectors(this.setKeyFunction(keyFeildName), elements);
	}

	private Function<E, K> setKeyFunction(Function<E, K> keyFunction) {
		return condition.getFunction(keyFunction);
	}

	private Function<E, V> setValueFunction(Function<E, V> valueFunction) {
		return condition.getFunction(valueFunction);
	}

	private Function<E, K> setKeyFunction(String keyFeildName) {
		return condition.<E, K>getFunction(keyFeildName);
	}

	private Function<E, V> setValueFunction(String valueFeildName) {
		return condition.<E, V>getFunction(valueFeildName);
	}

	private Function<E, E> setValueFunction() {
		return condition.<E>getFunction();
	}

	public class InnerMultiMapCollectors implements MultiMapCollectors<E, K, V> {

		private Function<E, K> keyFunction;
		private Function<E, V> valueFunction;
		private Function<E, E> sameTypeValueFunction;
		private Iterable<E> elements;

		public InnerMultiMapCollectors(Function<E, K> keyFunction, Function<E, V> valueFunction, Iterable<E> elements) {
			this.keyFunction = keyFunction;
			this.valueFunction = valueFunction;
			this.elements = elements;
		}

		public InnerMultiMapCollectors(Function<E, K> keyFunction, Iterable<E> elements) {
			this.keyFunction = keyFunction;
			this.sameTypeValueFunction = setValueFunction();
			this.elements = elements;
		}

		@Override
		public Map<K, V> keyToValuesMultimap() {
			if (this.keyFunction == null || this.valueFunction == null) {
				return Maps.newLinkedHashMap();
			}
			ImmutableListMultimap<K, E> keysToElements = Multimaps
					.index(Iterables.filter(this.elements, Predicates.notNull()), this.keyFunction);
			ListMultimap<K, V> keysToValuesLazy = Multimaps.transformValues(keysToElements, this.valueFunction);
			Map<K, V> result = Maps.newLinkedHashMap();
			ImmutableListMultimap<K, V> immutableListMultimap = ImmutableListMultimap.copyOf(keysToValuesLazy);
			for (Entry<K, V> imEntry : immutableListMultimap.entries()) {
				result.put(imEntry.getKey(), imEntry.getValue());
			}
			return result;
		}

		@Override
		public Multimap<K, V> keyToValuesAsMultimap() {
			if (this.keyFunction == null || this.valueFunction == null)
				throw new NullPointerException("has to set keyFunction or valueFunction. ");
			ImmutableListMultimap<K, E> keysToElements = Multimaps
					.index(Iterables.filter(this.elements, Predicates.notNull()), this.keyFunction);
			ListMultimap<K, V> keysToValuesLazy = Multimaps.transformValues(keysToElements, this.valueFunction);
			return ImmutableListMultimap.copyOf(keysToValuesLazy);
		}

		@Override
		public Multimap<K, E> keyToValueObjectAsMultimap() {
			if (this.keyFunction == null || this.sameTypeValueFunction == null)
				throw new NullPointerException("has to set keyFunction or sameTypeValueFunction. ");
			ImmutableListMultimap<K, E> keysToElements = Multimaps
					.index(Iterables.filter(this.elements, Predicates.notNull()), this.keyFunction);
			ListMultimap<K, E> keysToValuesLazy = Multimaps.transformValues(keysToElements, this.sameTypeValueFunction);
			return ImmutableListMultimap.copyOf(keysToValuesLazy);
		}
	}

}

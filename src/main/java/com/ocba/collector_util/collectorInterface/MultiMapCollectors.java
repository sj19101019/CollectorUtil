package com.ocba.collector_util.collectorInterface;

import java.util.Map;

import com.google.common.collect.Multimap;

public interface MultiMapCollectors<E, K, V> {
	public Map<K, V> keyToValuesMultimap();

	public Multimap<K, V> keyToValuesAsMultimap();

	public Multimap<K, E> keyToValueObjectAsMultimap();
}

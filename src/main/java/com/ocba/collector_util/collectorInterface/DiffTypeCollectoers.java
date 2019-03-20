package com.ocba.collector_util.collectorInterface;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DiffTypeCollectoers<F, T> {

	public List<T> filterSameFieldToList();

	public Map<T, List<F>> keyToValuesMultimap();

	public Set<T> filterSameFieldToSet();

	public Map<T, F> filterIterableToMap();
}

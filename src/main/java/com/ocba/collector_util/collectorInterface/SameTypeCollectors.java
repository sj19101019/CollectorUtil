package com.ocba.collector_util.collectorInterface;

import java.util.List;
import java.util.Set;

public interface SameTypeCollectors<T> {

	public List<T> filterSameValueToList();

	public Set<T> filterSameValueToSet();

	public T filterSameValueToObjectByFirstMatch();
}

package com.ocba.collector_util.collectorInterface;

import java.util.List;
import java.util.Set;

public interface SameTypeCollectors<T> {

	public List<T> filterSameVauleToList();

	public Set<T> filterSameVauleToSet();

	public T filterSameVauleToObjectByFirstMatch();
}

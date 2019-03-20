package com.ocba.collector_util.collectorInterface;

import java.util.List;

public interface StreamFunciton<T, E> {
	public T reduce(T prev, E current, int index, List<E> stream);
}

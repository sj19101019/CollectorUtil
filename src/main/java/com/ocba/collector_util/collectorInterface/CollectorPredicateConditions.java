package com.ocba.collector_util.collectorInterface;

import java.util.Set;

import com.google.common.base.Predicate;

public interface CollectorPredicateConditions {

	public <T> Predicate<T> getPredicate(Predicate<T> predicate);

	public <T> Predicate<T> getPredicate(String fieldName, Object value);

	public <E, T> Predicate<T> getPredicate(String fieldName, Set<E> value);
}

package com.ocba.collector_util.collectorInterface;

import com.google.common.base.Function;

public interface CollectorFunctionConditions {
	public <F, T> Function<F, T> getFunction(Function<F, T> function);

	public <F, T> Function<F, T> getFunction(String fieldName);

	public abstract <F, T> Function<F, T> getFunction(String fieldName, Object notValue);

	public abstract <F, T> Function<F, T> getFunction(Class<T> clazz, String... fieldNames);

	public abstract <F, T> Function<F, T> getFunction(Class<T> clazz);

	public abstract <F, T> Function<F, T> getFunction(final Class<T> clazz, final String[] originalFieldNames,
			final String[] targetFieldNames);

	public <T> Function<T, T> getFunction();
}

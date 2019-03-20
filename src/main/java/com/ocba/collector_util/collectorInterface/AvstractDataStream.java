package com.ocba.collector_util.collectorInterface;

public abstract class AvstractDataStream<T, E> {

	public abstract T reduce(StreamFunciton<T, E> function);

	public abstract T reduce(StreamFunciton<T, E> function, T startValue);

	public abstract T reduceRight(StreamFunciton<T, E> function);

	public abstract T reduceRight(StreamFunciton<T, E> function, T startValue);
}

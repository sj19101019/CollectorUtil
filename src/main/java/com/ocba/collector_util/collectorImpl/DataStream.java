package com.ocba.collector_util.collectorImpl;

import java.util.List;

import com.google.common.base.Optional;
import com.ocba.collector_util.collectorInterface.AvstractDataStream;
import com.ocba.collector_util.collectorInterface.StreamFunciton;



public class DataStream<T, E> extends AvstractDataStream<T, E> {
	private List<E> stream;

	public DataStream(List<E> stream) {
		if (!Optional.fromNullable(stream).isPresent()) {
			throw new NullPointerException("stream can't not be null.");
		}
		this.stream = stream;
	}

	@Override
	public T reduce(StreamFunciton<T, E> function) {
		return this.reduce(function, null);
	}


	@Override
	public T reduce(StreamFunciton<T, E> function, T startValue) {
		if (function == null) {
			throw new NullPointerException("StreamFunciton can't not be null.");
		}

		T prev = startValue;
		int total = stream.size();
		for (int index = 0; index < total; index++) {
			E current = stream.get(index);
			prev = function.reduce(prev, current, index, stream);
		}

		return prev;
	}

	@Override
	public T reduceRight(StreamFunciton<T, E> function) {
		return this.reduceRight(function, null);
	}

	@Override
	public T reduceRight(StreamFunciton<T, E> function, T startValue) {
		if (function == null) {
			throw new NullPointerException("StreamFunciton can't not be null.");
		}

		T prev = startValue;
		int total = stream.size();
		for (int index = (total - 1); index > -1; index--) {
			E current = stream.get(index);
			prev = function.reduce(prev, current, index, stream);
		}

		return prev;
	}
}

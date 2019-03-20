package com.ocba.collector_util.collectorImpl;

import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.google.common.base.Predicate;
import com.ocba.collector_util.collectorInterface.CollectorPredicateConditions;

public class CollectorPredicateConditionsImpl implements CollectorPredicateConditions {

	/**
	 * 設定搜尋函式
	 *
	 * @param fieldName
	 * @param value
	 */
	@Override
	public <T> Predicate<T> getPredicate(final String fieldName, final Object value) {
		return new Predicate<T>() {

			@Override
			public boolean apply(T paramT) {
				try {
					return ObjectUtils.equals(value, FieldUtils.readField(paramT, fieldName, true));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				return false;
			}
		};
	}

	/**
	 * 設定搜尋函式
	 *
	 * @param fieldName
	 * @param value
	 */
	@Override
	public <E, T> Predicate<T> getPredicate(final String fieldName, final Set<E> value) {
		return new Predicate<T>() {

			@Override
			public boolean apply(T paramT) {
				try {
					return value.contains(FieldUtils.readField(paramT, fieldName, true));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				return false;
			}
		};
	}

	@Override
	public <T> Predicate<T> getPredicate(Predicate<T> predicate) {
		return predicate;
	}

}

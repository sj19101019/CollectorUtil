package com.ocba.collector_util.collectorImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ocba.collector_util.collectorInterface.CollectorPredicateConditions;
import com.ocba.collector_util.collectorInterface.SameTypeCollectors;

public class SameTypeCollectorsImpl<T> {
	private CollectorPredicateConditions condition;

	public SameTypeCollectorsImpl(CollectorPredicateConditions condition) {
		this.condition = condition;
	}

	/**
	 * 設定搜尋條件與對像
	 *
	 * @param fieldName
	 * @param value
	 * @param datas
	 */
	public InnerSameTypeCollectors setCondition(String fieldName, Object value, List<T> datas) {
		return new InnerSameTypeCollectors(this.setPredicate(fieldName, value), datas);
	}

	/**
	 * 設定搜尋條件與對像
	 *
	 * @param predicate
	 * @param datas
	 */
	public InnerSameTypeCollectors setCondition(Predicate<T> predicate, List<T> datas) {
		return new InnerSameTypeCollectors(this.setPredicate(predicate), datas);
	}

	/**
	 * 設定搜尋條件與對像
	 *
	 * @param fieldName
	 * @param value
	 * @param datas
	 */
	public <E> InnerSameTypeCollectors setCondition(String fieldName, Set<E> value, List<T> datas) {
		return new InnerSameTypeCollectors(this.setPredicate(fieldName, value), datas);
	}

	/**
	 * 設定搜尋函式
	 *
	 * @param fieldName
	 * @param value
	 */
	private Predicate<T> setPredicate(Predicate<T> predicate) {
		return condition.<T> getPredicate(predicate);
	}

	/**
	 * 設定搜尋函式
	 *
	 * @param fieldName
	 * @param value
	 */
	private Predicate<T> setPredicate(String fieldName, Object value) {
		return condition.<T> getPredicate(fieldName, value);
	}

	/**
	 * 設定搜尋函式
	 *
	 * @param fieldName
	 * @param value
	 */
	private <E> Predicate<T> setPredicate(String fieldName, Set<E> value) {
		return condition.<E, T> getPredicate(fieldName, value);
	}

	public class InnerSameTypeCollectors implements SameTypeCollectors<T> {
		private Predicate<T> predicate;
		private List<T> datas;

		public InnerSameTypeCollectors(Predicate<T> predicate, List<T> datas) {
			this.predicate = predicate;
			this.datas = datas;
		}

		/**
		 * 取得搜尋結果陣列
		 */
		@Override
		public List<T> filterSameVauleToList() {
			if (this.predicate == null || this.datas == null) {
				return new ArrayList<>();
			}

			return Lists.newLinkedList(
					Iterables.filter(Iterables.filter(this.datas, this.predicate), Predicates.notNull()));
		}

		/**
		 * 取得搜尋結果Set
		 */
		@Override
		public Set<T> filterSameVauleToSet() {
			if (this.predicate == null || this.datas == null) {
				return new HashSet<>();
			}

			return Sets.newLinkedHashSet(
					Iterables.filter(Iterables.filter(this.datas, this.predicate), Predicates.notNull()));
		}

		/**
		 * 取得搜索結果物件
		 */
		@Override
		public T filterSameVauleToObjectByFirstMatch() {
			if (this.predicate == null || this.datas == null) {
				return null;
			}
			return FluentIterable.from(this.datas).firstMatch(this.predicate).orNull();
		}

	}

}

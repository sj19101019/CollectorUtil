package com.ocba.collector_util.collectorImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import com.ocba.collector_util.collectorInterface.CollectorFunctionConditions;
import com.ocba.collector_util.collectorInterface.DiffTypeCollectoers;


public class DiffTypeCollectorsImpl<F, T> {

	private CollectorFunctionConditions condition;

	public DiffTypeCollectorsImpl(CollectorFunctionConditions condition) {
		this.condition = condition;
	}

	/**
	 * 設定取得同一欄位所有的值
	 *
	 * @param function
	 *            搜尋方法
	 * @param datas
	 *            被搜尋陣列
	 */
	public InnerDiffTypeCollectorsImpl setCondition(Function<F, T> function, Iterable<F> datas) {
		return new InnerDiffTypeCollectorsImpl(this.setFunction(function), datas);
	}

	/**
	 * 設定取得同一欄位所有的值
	 *
	 * @param fieldName
	 *            欄位名稱
	 * @param datas
	 *            被搜尋陣列
	 */
	public InnerDiffTypeCollectorsImpl setCondition(String fieldName, Iterable<F> datas) {
		return new InnerDiffTypeCollectorsImpl(this.setFunction(fieldName), datas);
	}

	/**
	 * 設定取得多數欄位所有的值
	 *
	 * @param datas
	 *            被搜尋陣列
	 * @param fieldNames
	 *            欄位名稱(若無指定欄位名稱預設全部)
	 */
	public InnerDiffTypeCollectorsImpl setCondition(Class<T> clazz, Iterable<F> datas, String... fieldNames) {
		if (fieldNames.length != 0) {
			return new InnerDiffTypeCollectorsImpl(this.setFunction(clazz, fieldNames), datas);
		} else {
			return new InnerDiffTypeCollectorsImpl(this.setFunction(clazz), datas);
		}
	}

	/**
	 * 設定取得欄位指定欄位附值
	 *
	 * @param clazz
	 * @param datas
	 * @param originalFieldNames
	 * @param targetFieldNames
	 */
	public InnerDiffTypeCollectorsImpl setCondition(Class<T> clazz, Iterable<F> datas, String[] originalFieldNames,
			String[] targetFieldNames) {
		return new InnerDiffTypeCollectorsImpl(this.setFunction(clazz, originalFieldNames, targetFieldNames), datas);
	}

	/**
	 * 設定取得同一欄位&不等於此值的值
	 *
	 * @param fieldName
	 *            欄位名稱
	 * @param datas
	 *            被搜尋陣列
	 * @param notValue
	 *            不等於的值
	 */
	public InnerDiffTypeCollectorsImpl setCondition(String fieldName, Iterable<F> datas, Object notValue) {
		return new InnerDiffTypeCollectorsImpl(this.setFunction(fieldName, notValue), datas);
	}

	private Function<F, T> setFunction(Function<F, T> function) {
		return condition.getFunction(function);
	}

	/**
	 * set function by fieldName get value
	 *
	 * @param fieldName
	 */
	private Function<F, T> setFunction(String fieldName) {
		return condition.getFunction(fieldName);
	}

	/**
	 * 設定取得同一欄位&不等於此值的值
	 *
	 * @param fieldName
	 * @param notValue
	 */
	private Function<F, T> setFunction(String fieldName, Object notValue) {
		return condition.getFunction(fieldName, notValue);
	}

	/**
	 * 設定取得多數欄位所有的值
	 *
	 * @param fieldNames
	 */
	private Function<F, T> setFunction(final Class<T> clazz, final String... fieldNames) {
		return condition.getFunction(clazz, fieldNames);
	}

	/**
	 * 設定取得多數欄位附值
	 *
	 * @param clazz
	 * @param originalFieldNames
	 * @param targetFieldNames
	 */
	private Function<F, T> setFunction(final Class<T> clazz, final String[] originalFieldNames,
			final String[] targetFieldNames) {
		return condition.getFunction(clazz, originalFieldNames, targetFieldNames);
	}

	/**
	 * 複製值進給予的class
	 *
	 * @param clazz
	 */
	private Function<F, T> setFunction(final Class<T> clazz) {
		return condition.getFunction(clazz);
	}

	public class InnerDiffTypeCollectorsImpl implements DiffTypeCollectoers<F, T> {
		private Function<F, T> function;
		private Iterable<F> datas;

		public InnerDiffTypeCollectorsImpl(Function<F, T> function, Iterable<F> datas) {
			this.function = function;
			this.datas = datas;
		}

		/**
		 * 取得相同欄位所有資料
		 *
		 * @return
		 */
		@Override
		public List<T> filterSameFieldToList() {
			if (this.function == null || this.datas == null) {
				return new ArrayList<>();
			}
			return Lists.newLinkedList(
					Iterables.filter(Iterables.transform(this.datas, this.function), Predicates.notNull()));
		}

		/**
		 * 將陣列轉成map
		 *
		 * @param elements
		 * @param fieldName
		 * @return
		 */
		@Override
		public Map<T, List<F>> keyToValuesMultimap() {
			if (this.function == null || this.datas == null) {
				return new LinkedHashMap<>();
			}
			ImmutableListMultimap<T, F> keysToElements = Multimaps
					.index(Iterables.filter(this.datas, Predicates.notNull()), this.function);
			return Multimaps.asMap(LinkedListMultimap.create(keysToElements));
		}

		/**
		 * 取得相同欄位所有資料
		 *
		 * @return
		 */
		@Override
		public Set<T> filterSameFieldToSet() {
			if (this.function == null || this.datas == null) {
				return Sets.newLinkedHashSet();
			}
			return Sets.newLinkedHashSet(
					Iterables.filter(Iterables.transform(this.datas, this.function), Predicates.notNull()));
		}

		/**
		 * 將Iterable轉換成Map
		 *
		 * @return
		 */
		@Override
		public Map<T, F> filterIterableToMap() {
			if (this.function == null || this.datas == null) {
				return Maps.newLinkedHashMap();
			}
			return Maps.uniqueIndex(this.datas, this.function);
		}
	}

}

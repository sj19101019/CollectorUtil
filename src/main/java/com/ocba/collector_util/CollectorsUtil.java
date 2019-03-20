package com.ocba.collector_util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.ocba.collector_util.collectorImpl.CollectorFunctionConditionsImpl;
import com.ocba.collector_util.collectorImpl.CollectorPredicateConditionsImpl;
import com.ocba.collector_util.collectorImpl.DataStream;
import com.ocba.collector_util.collectorImpl.DiffTypeCollectorsImpl;
import com.ocba.collector_util.collectorImpl.MultiMapCollectorsImpl;
import com.ocba.collector_util.collectorImpl.SameTypeCollectorsImpl;
import com.ocba.collector_util.collectorInterface.StreamFunciton;

public class CollectorsUtil {

	/**
	 * 取得相同欄位所有資料
	 *
	 * @param function
	 *            欲取出值的function
	 * @param datas
	 *            欲取出值的陣列
	 * @return List<T> 取出值的陣列
	 */
	public static <T, F> List<T> filterSameFieldToList(Function<F, T> function, Iterable<F> datas) {
		return new DiffTypeCollectorsImpl<F, T>(new CollectorFunctionConditionsImpl()).setCondition(function, datas)
				.filterSameFieldToList();
	}

	/**
	 * 取得相同欄位所有資料
	 *
	 * @param fieldName
	 *            欲取出欄位的名稱
	 * @param datas
	 *            欲取出值的陣列
	 * @return List<T> 取出欄位所有的值
	 */
	public static <T, F> List<T> filterSameFieldToList(String fieldName, List<F> datas) {
		return new DiffTypeCollectorsImpl<F, T>(new CollectorFunctionConditionsImpl()).setCondition(fieldName, datas)
				.filterSameFieldToList();
	}

	/**
	 * 取得相同欄位所有資料(多欄位)
	 *
	 * @param resultType
	 *            回傳的物件型別
	 * @param datas
	 *            欲取出值的陣列
	 * @param fieldNames
	 *            欲取出欄位的名稱(multi) (若無填寫欄位，預設全部欄位賦值)
	 * @return List<T> 所給型態之陣列回傳
	 */
	public static <T, F> List<T> filterMultiSameFieldToList(Class<T> resultType, List<F> datas, String... fieldNames) {
		return new DiffTypeCollectorsImpl<F, T>(new CollectorFunctionConditionsImpl())
				.setCondition(resultType, datas, fieldNames).filterSameFieldToList();
	}

	/**
	 * 取得相同欄位所有資料(多欄位)
	 *
	 * @param resultType
	 *            回傳的物件型別
	 * @param datas
	 *            欲取出值的陣列
	 * @param originalFieldNames
	 *            欲取出值陣列的欄位名稱
	 * @param targetFieldNames
	 *            欲附值的物件欄位名稱
	 * @return List<T> 所給型態之陣列回傳
	 */
	public static <T, F> List<T> filterMultiSameFieldToList(Class<T> resultType, List<F> datas,
			String[] originalFieldNames, String[] targetFieldNames) {
		return new DiffTypeCollectorsImpl<F, T>(new CollectorFunctionConditionsImpl())
				.setCondition(resultType, datas, originalFieldNames, targetFieldNames).filterSameFieldToList();
	}

	/**
	 * 取得相同欄位所有資料，排除某個值
	 *
	 * @param fieldName
	 *            欲取出欄位的名稱
	 * @param datas
	 *            欲取出值的陣列
	 * @param notValue
	 *            欲排除的值
	 * @return List<T> 取出欄位所有的值
	 */
	public static <T, F> List<T> filterSameFieldToListNotContain(String fieldName, List<F> datas, Object notValue) {
		return new DiffTypeCollectorsImpl<F, T>(new CollectorFunctionConditionsImpl())
				.setCondition(fieldName, datas, notValue).filterSameFieldToList();
	}

	/**
	 * 取得相同條件的陣列回傳
	 *
	 * @param predicate
	 *            自訂判斷條件
	 * @param datas
	 *            欲判斷陣列
	 * @return List<F> 取出符合條件所有的值
	 */
	public static <F> List<F> filterSameValueToList(Predicate<F> predicate, List<F> datas) {
		return new SameTypeCollectorsImpl<F>(new CollectorPredicateConditionsImpl()).setCondition(predicate, datas)
				.filterSameValueToList();
	}

	/**
	 * 取得相同條件的陣列回傳
	 *
	 * @param fieldName
	 *            欲判斷的欄位名稱
	 * @param datas
	 *            欲取出值的陣列
	 * @param value
	 *            欲取出此欄位與此值相同的物件
	 * @return List<F> 回傳符合條件的第一筆物件
	 */
	public static <F> List<F> filterSameValueToList(String fieldName, List<F> datas, Object value) {
		return new SameTypeCollectorsImpl<F>(new CollectorPredicateConditionsImpl())
				.setCondition(fieldName, value, datas).filterSameValueToList();
	}

	/**
	 * 取得相同條件的第一個物件回傳
	 *
	 * @param predicate
	 *            自訂判斷條件
	 * @param datas
	 *            欲搜查陣列
	 * @return <F> 回傳符合條件的第一筆物件
	 */
	public static <F> F filterSameValueToObjectByFirstMatch(Predicate<F> predicate, List<F> datas) {
		return new SameTypeCollectorsImpl<F>(new CollectorPredicateConditionsImpl()).setCondition(predicate, datas)
				.filterSameValueToObjectByFirstMatch();
	}

	/**
	 * 取得相同條件的第一個物件回傳
	 *
	 * @param fieldName
	 *            欲取出的欄位名稱
	 * @param value
	 *            欲取出此欄位與此值相同的物件
	 * @param datas
	 *            欲取出值的陣列
	 * @return <F> 回傳與欄位相同值的物件
	 */
	public static <F> F filterSameValueToObjectByFirstMatch(String fieldName, List<F> datas, Object value) {
		return new SameTypeCollectorsImpl<F>(new CollectorPredicateConditionsImpl())
				.setCondition(fieldName, value, datas).filterSameValueToObjectByFirstMatch();
	}

	/**
	 * 取得相同條件的陣列回傳
	 *
	 * @param fieldName
	 *            欲取出的欄位名稱
	 * @param value
	 *            欲取出此欄位與此值相同的Set
	 * @param datas
	 *            欲取出值的陣列
	 * @return List<F> 取得此欄位中與Set相同值的物件回傳陣列
	 */
	public static <F, T> List<F> filterSameValueToList(String fieldName, List<F> datas, Set<T> value) {
		return new SameTypeCollectorsImpl<F>(new CollectorPredicateConditionsImpl())
				.setCondition(fieldName, value, datas).filterSameValueToList();
	}

	/**
	 * 取得相同欄位所有資料
	 *
	 * @param function
	 *            欲取出值的function
	 * @param datas
	 *            欲取出值的陣列
	 * @return Set<T> 取出值的陣列
	 */
	public static <T, F> Set<T> filterSameFieldToSet(Function<F, T> function, List<F> datas) {
		return new DiffTypeCollectorsImpl<F, T>(new CollectorFunctionConditionsImpl()).setCondition(function, datas)
				.filterSameFieldToSet();
	}

	/**
	 * 取得相同欄位所有資料
	 *
	 * @param fieldName
	 *            欲取出欄位的名稱
	 * @param datas
	 *            欲取出值的陣列
	 * @return Set<T> 取出欄位所有的值
	 */
	public static <T, F> Set<T> filterSameFieldToSet(String fieldName, List<F> datas) {
		return new DiffTypeCollectorsImpl<F, T>(new CollectorFunctionConditionsImpl()).setCondition(fieldName, datas)
				.filterSameFieldToSet();
	}

	/**
	 * 取得相同欄位所有資料(多欄位)
	 *
	 * @param datas
	 *            欲取出值的陣列
	 * @param fieldNames
	 *            欲取出欄位的名稱(multi) (若無填寫欄位，預設全部欄位賦值)
	 * @return Set<T> 所給型態之陣列
	 */
	public static <T, F> Set<T> filterMultiSameFieldToSet(Class<T> resultType, List<F> datas, String... fieldNames) {
		return new DiffTypeCollectorsImpl<F, T>(new CollectorFunctionConditionsImpl())
				.setCondition(resultType, datas, fieldNames).filterSameFieldToSet();
	}

	/**
	 * 取得相同欄位所有資料(多欄位)
	 *
	 * @param resultType
	 *            回傳的物件型別
	 * @param datas
	 *            欲取出值的陣列
	 * @param originalFieldNames
	 *            欲取出值陣列的欄位名稱
	 * @param targetFieldNames
	 *            欲附值的物件欄位名稱
	 * @return Set<T> 所給型態之陣列回傳
	 */
	public static <T, F> Set<T> filterMultiSameFieldToSet(Class<T> resultType, List<F> datas,
			String[] originalFieldNames, String[] targetFieldNames) {
		return new DiffTypeCollectorsImpl<F, T>(new CollectorFunctionConditionsImpl())
				.setCondition(resultType, datas, originalFieldNames, targetFieldNames).filterSameFieldToSet();
	}

	/**
	 * 取得相同欄位所有資料，排除某個值
	 *
	 * @param fieldName
	 *            欲取出欄位的名稱
	 * @param datas
	 *            欲取出值的陣列
	 * @param notValue
	 *            欲排除的值
	 * @return Set<T> 取出欄位所有的值
	 */
	public static <T, F> Set<T> filterSameFieldToSetNotContain(String fieldName, List<F> datas, Object notValue) {
		return new DiffTypeCollectorsImpl<F, T>(new CollectorFunctionConditionsImpl())
				.setCondition(fieldName, datas, notValue).filterSameFieldToSet();
	}

	/**
	 * 取得相同條件的陣列回傳
	 *
	 * @param fieldName
	 *            欲取出的欄位名稱
	 * @param value
	 *            欲取出此欄位與此值相同的物件
	 * @param datas
	 *            欲取出值的陣列
	 * @return Set<F> 回傳與欄位相同值的陣列
	 */
	public static <F> Set<F> filterSameValueToSet(String fieldName, List<F> datas, Object value) {
		return new SameTypeCollectorsImpl<F>(new CollectorPredicateConditionsImpl())
				.setCondition(fieldName, value, datas).filterSameValueToSet();
	}

	/**
	 * 取得相同條件的陣列回傳
	 *
	 * @param fieldName
	 *            欲取出的欄位名稱
	 * @param value
	 *            欲取出此欄位與此值相同的Set
	 * @param datas
	 *            欲取出值的陣列
	 * @return Set<F> 取得此欄位中與Set相同值的物件回傳陣列
	 */
	public static <F, T> Set<F> filterSameValueToSet(String fieldName, List<F> datas, Set<T> value) {
		return new SameTypeCollectorsImpl<F>(new CollectorPredicateConditionsImpl())
				.setCondition(fieldName, value, datas).filterSameValueToSet();
	}

	/**
	 * 將陣列轉成map
	 *
	 * @param fieldName
	 *            Key值欄位名稱
	 * @param datas
	 *            欲轉成Map的Iterable
	 * @return Map<T, List<F>> 回傳以此欄位為Key的Map
	 */
	public static <F, T> Map<T, List<F>> filterSameValueToMap(String fieldName, Iterable<F> datas) {
		return new DiffTypeCollectorsImpl<F, T>(new CollectorFunctionConditionsImpl()).setCondition(fieldName, datas)
				.keyToValuesMultimap();
	}

	/**
	 * 將陣列轉成map
	 *
	 * @param keyFunction
	 *            Key值的Function
	 * @param datas
	 *            欲轉成Map的Iterable
	 * @return Map<T, List<F>> 回傳以此條件為Key的Map
	 */
	public static <F, T> Map<T, List<F>> filterSameValueToMap(Function<F, T> keyFunction, Iterable<F> datas) {
		return new DiffTypeCollectorsImpl<F, T>(new CollectorFunctionConditionsImpl()).setCondition(keyFunction, datas)
				.keyToValuesMultimap();
	}

	/**
	 * 將陣列轉換成 Map
	 *
	 * @param elements
	 *            欲轉換陣列
	 * @param keyFunction
	 *            key的function
	 * @param valueFunction
	 *            value的function
	 * @return Map<K, V> 回傳以欄位為keyFunction & valueFunction的Map
	 */
	public static <E, K, V> Map<K, V> keyToValuesMultimap(Iterable<E> elements, Function<E, K> keyFunction,
			Function<E, V> valueFunction) {
		return new MultiMapCollectorsImpl<E, K, V>(new CollectorFunctionConditionsImpl())
				.setCondition(keyFunction, valueFunction, elements).keyToValuesMultimap();
	}

	/**
	 * 將陣列轉換成 Map
	 *
	 * @param elements
	 *            欲轉換陣列
	 * @param keyFeildName
	 *            欲轉換成key的欄位名稱
	 * @param valueFeildName
	 *            欲轉換成value的欄位名稱
	 * @return Map<K, V> 回傳以欄位為keyFeildName & valueFeildName的Map
	 */
	public static <E, K, V> Map<K, V> keyToValuesMultimap(Iterable<E> elements, String keyFeildName,
			String valueFeildName) {
		return new MultiMapCollectorsImpl<E, K, V>(new CollectorFunctionConditionsImpl())
				.setCondition(keyFeildName, valueFeildName, elements).keyToValuesMultimap();
	}

	/**
	 * 將陣列轉換成 Map
	 *
	 * @param elements
	 *            欲轉換陣列
	 * @param keyFeildName
	 *            欲轉換成key的欄位名稱
	 * @param valueFunction
	 *            value的function
	 * @return Map<K, V> 回傳以欄位為keyFeildName & value的function的Map
	 */
	public static <E, K, V> Map<K, V> keyToValuesMultimap(Iterable<E> elements, String keyFeildName,
			Function<E, V> valueFunction) {
		return new MultiMapCollectorsImpl<E, K, V>(new CollectorFunctionConditionsImpl())
				.setCondition(keyFeildName, valueFunction, elements).keyToValuesMultimap();
	}

	/**
	 * 將陣列轉換成 Map
	 *
	 * @param elements
	 *            欲轉換陣列
	 * @param keyFunction
	 *            key的function
	 * @param valueFeildName
	 *            欲轉換成value的欄位名稱
	 * @return Map<K, V> 回傳以欄位為key的function & valueFeildName的Map
	 */
	public static <E, K, V> Map<K, V> keyToValuesMultimap(Iterable<E> elements, Function<E, K> keyFunction,
			String valueFeildName) {
		return new MultiMapCollectorsImpl<E, K, V>(new CollectorFunctionConditionsImpl())
				.setCondition(keyFunction, valueFeildName, elements).keyToValuesMultimap();
	}

	/**
	 * 將陣列轉換成 Multimap，Multimap特性為: 允許key相同的Map，若key相同，則合並成集合(Collection)儲存
	 *
	 * @param elements
	 *            欲轉換陣列
	 * @param keyFunction
	 *            key的function
	 * @param valueFeildName
	 *            欲轉換成value的欄位名稱
	 * @return
	 */
	public static <E, K, V> Multimap<K, V> keyToValuesAsMultimap(Iterable<E> elements, Function<E, K> keyFunction,
			String valueFeildName) {
		return new MultiMapCollectorsImpl<E, K, V>(new CollectorFunctionConditionsImpl())
				.setCondition(keyFunction, valueFeildName, elements).keyToValuesAsMultimap();
	}

	/**
	 * 將陣列轉換成 Multimap，Multimap特性為: 允許key相同的Map，若key相同，則合並成集合(Collection)儲存
	 *
	 * @param elements
	 *            欲轉換陣列
	 * @param keyFunction
	 *            key的function
	 * @param valueFunction
	 *            value的function
	 * @return
	 */
	public static <E, K, V> Multimap<K, V> keyToValuesAsMultimap(Iterable<E> elements, Function<E, K> keyFunction,
			Function<E, V> valueFunction) {
		return new MultiMapCollectorsImpl<E, K, V>(new CollectorFunctionConditionsImpl())
				.setCondition(keyFunction, valueFunction, elements).keyToValuesAsMultimap();
	}

	/**
	 * 將陣列轉換成 Multimap，Multimap特性為: 允許key相同的Map，若key相同，則合並成集合(Collection)儲存
	 *
	 * @param elements
	 *            欲轉換陣列
	 * @param keyFeildName
	 *            欲轉換成key的欄位名稱
	 * @param valueFunction
	 *            value的function
	 * @return
	 */
	public static <E, K, V> Multimap<K, V> keyToValuesAsMultimap(Iterable<E> elements, String keyFeildName,
			Function<E, V> valueFunction) {
		return new MultiMapCollectorsImpl<E, K, V>(new CollectorFunctionConditionsImpl())
				.setCondition(keyFeildName, valueFunction, elements).keyToValuesAsMultimap();
	}

	/**
	 * 將陣列轉換成 Multimap，Multimap特性為: 允許key相同的Map，若key相同，則合並成集合(Collection)儲存
	 *
	 * @param elements
	 *            欲轉換陣列
	 * @param keyFeildName
	 *            欲轉換成key的欄位名稱
	 * @param valueFeildName
	 *            欲轉換成value的欄位名稱
	 * @return
	 */
	public static <E, K, V> Multimap<K, V> keyToValuesAsMultimap(Iterable<E> elements, String keyFeildName,
			String valueFeildName) {
		return new MultiMapCollectorsImpl<E, K, V>(new CollectorFunctionConditionsImpl())
				.setCondition(keyFeildName, valueFeildName, elements).keyToValuesAsMultimap();
	}

	/**
	 * 將陣列轉換成 Multimap，Multimap特性為: 允許key相同的Map，若key相同，則合並成集合(Collection)儲存
	 *
	 * @param elements
	 *            欲轉換陣列
	 * @param keyFeildName
	 *            欲轉換成key的欄位名稱
	 * @return
	 */
	public static <E, K, V> Multimap<K, E> keyToValuesAsMultimap(Iterable<E> elements, String keyFeildName) {
		return new MultiMapCollectorsImpl<E, K, V>(new CollectorFunctionConditionsImpl())
				.setCondition(keyFeildName, elements).keyToValueObjectAsMultimap();
	}

	/**
	 * 將陣列轉換成 Multimap，Multimap特性為: 允許key相同的Map，若key相同，則合並成集合(Collection)儲存
	 *
	 * @param elements
	 *            欲轉換陣列
	 * @param keyFunction
	 *            key的function
	 * @return
	 */
	public static <E, K, V> Multimap<K, E> keyToValuesAsMultimap(Iterable<E> elements, Function<E, K> keyFunction) {
		return new MultiMapCollectorsImpl<E, K, V>(new CollectorFunctionConditionsImpl())
				.setCondition(keyFunction, elements).keyToValueObjectAsMultimap();
	}

	/**
	 * 取得指定條件的Map物件
	 *
	 * @param function
	 *            欲取出值的function
	 * @param datas
	 *            欲取出值的陣列
	 * @return 指定條件的Map物件
	 */
	public static <T, F> Map<T, F> filterIterableToMap(Function<F, T> function, Iterable<F> datas) {
		return new DiffTypeCollectorsImpl<F, T>(new CollectorFunctionConditionsImpl()).setCondition(function, datas)
				.filterIterableToMap();
	}

	/**
	 * 取得指定欄位的Map物件
	 *
	 * @param fieldName
	 *            欲取出欄位的名稱
	 * @param datas
	 *            欲取出值的陣列
	 * @return 指定欄位的Map物件
	 */
	public static <T, F> Map<T, F> filterIterableToMap(String fieldName, Iterable<F> datas) {
		return new DiffTypeCollectorsImpl<F, T>(new CollectorFunctionConditionsImpl()).setCondition(fieldName, datas)
				.filterIterableToMap();
	}

	/**
	 * 歷遍所有陣列做加總累積(index 0 -> last)
	 *
	 * @param function
	 *            累加function
	 * @param data
	 *            欲累加陣列
	 * @return
	 */
	public static <T, E> T reduce(StreamFunciton<T, E> function, List<E> data) {
		return new DataStream<T, E>(data).reduce(function);
	}

	/**
	 * 歷遍所有陣列做加總累積(index 0 -> last)
	 *
	 * @param function
	 *            累加function
	 * @param data
	 *            欲累加陣列
	 * @param startValue
	 *            累加起始值
	 * @return
	 */
	public static <T, E> T reduce(StreamFunciton<T, E> function, List<E> data, T startValue) {
		return new DataStream<T, E>(data).reduce(function, startValue);
	}

	/**
	 * 歷遍所有陣列做加總累積(index: last -> 0)
	 *
	 * @param function
	 *            累加function
	 * @param data
	 *            欲累加陣列
	 * @return
	 */
	public static <T, E> T reduceRight(StreamFunciton<T, E> function, List<E> data) {
		return new DataStream<T, E>(data).reduceRight(function);
	}

	/**
	 * 歷遍所有陣列做加總累積(index: last -> 0)
	 *
	 * @param function
	 *            累加function
	 * @param data
	 *            欲累加陣列
	 * @param startValue
	 *            累加起始值
	 * @return
	 */
	public static <T, E> T reduceRight(StreamFunciton<T, E> function, List<E> data, T startValue) {
		return new DataStream<T, E>(data).reduceRight(function, startValue);
	}

	/**
	 * 將陣列轉換成Table儲存方式
	 *
	 * @param rowName
	 *            欲轉換成row的欄位名稱
	 * @param columName
	 *            欲轉換成colum的欄位名稱
	 * @param datas
	 *            欲轉換之陣列
	 * @return
	 */
	public static <R, C, V> Table<R, C, List<V>> filterListToTable(String rowName, String columName, List<V> datas) {
		Table<R, C, List<V>> table = HashBasedTable.create();
		Map<C, List<V>> map = filterSameValueToMap(columName, datas);
		for (Entry<C, List<V>> entry1 : map.entrySet()) {
			Map<R, List<V>> map2 = filterSameValueToMap(rowName, entry1.getValue());
			for (Entry<R, List<V>> entry2 : map2.entrySet()) {
				table.put(entry2.getKey(), entry1.getKey(), entry2.getValue());
			}
		}
		return table;
	}

	/**
	 * 將陣列轉換成Table儲存方式
	 *
	 * @param rowFunciton
	 *            欲轉換成row的function
	 * @param columFunciton
	 *            欲轉換成colum的function
	 * @param datas
	 *            欲轉換之陣列
	 * @return
	 */
	public static <R, C, V> Table<R, C, List<V>> filterListToTable(Function<V, R> rowFunciton,
			Function<V, C> columFunciton, List<V> datas) {
		Table<R, C, List<V>> table = HashBasedTable.create();
		Map<C, List<V>> map = filterSameValueToMap(columFunciton, datas);
		for (Entry<C, List<V>> entry1 : map.entrySet()) {
			Map<R, List<V>> map2 = filterSameValueToMap(rowFunciton, entry1.getValue());
			for (Entry<R, List<V>> entry2 : map2.entrySet()) {
				table.put(entry2.getKey(), entry1.getKey(), entry2.getValue());
			}
		}
		return table;
	}

	/**
	 * 取得某欄位符合值之index
	 *
	 * @param data
	 *            欲搜尋之陣列
	 * @param fieldName
	 *            與搜尋之欄位
	 * @param key
	 *            欲搜尋之值
	 * @return
	 * @throws IllegalAccessException
	 */
	public static <T, E> int binarySearch(List<E> data, String fieldName, String key) throws IllegalAccessException {
		int low = 0;
		int high = data.size() - 1;
		int mid;

		while (low <= high) {
			mid = (low + high) / 2;
			String target = String.valueOf(FieldUtils.readField(data.get(mid), fieldName, true));
			if (target.compareTo(key) < 0) {
				low = mid + 1;
			} else if (target.compareTo(key) > 0) {
				high = mid - 1;
			} else {
				return mid;
			}
		}
		return -1;
	}
}

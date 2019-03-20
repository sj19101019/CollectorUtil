package com.ocba.collector_util.collectorImpl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Function;
import com.ocba.collector_util.collectorInterface.CollectorFunctionConditions;

public class CollectorFunctionConditionsImpl implements CollectorFunctionConditions {

	private static Log log = LogFactory.getLog(CollectorFunctionConditionsImpl.class);

	@Override
	public <F, T> Function<F, T> getFunction(Function<F, T> function) {
		return function;
	}

	/**
	 * set function by fieldName get value
	 *
	 * @param fieldName
	 */
	@Override
	public <F, T> Function<F, T> getFunction(final String fieldName) {
		return new Function<F, T>() {

			@Override
			@SuppressWarnings("unchecked")
			public T apply(F arg0) {
				try {
					return (T) FieldUtils.readField(arg0, fieldName, true);
				} catch (IllegalAccessException e) {
					log.error(e.getMessage(), e);
				}
				return null;
			}
		};
	}

	/**
	 * 設定取得同一欄位&不等於此值的值
	 *
	 * @param fieldName
	 * @param notValue
	 */
	@Override
	public <F, T> Function<F, T> getFunction(final String fieldName, final Object notValue) {
		return new Function<F, T>() {
			@Override
			@SuppressWarnings("unchecked")
			public T apply(F arg0) {
				try {
					if (!ObjectUtils.equals(FieldUtils.readField(arg0, fieldName, true), notValue)) {
						return (T) FieldUtils.readField(arg0, fieldName, true);
					}
				} catch (IllegalAccessException e) {
					log.error(e.getMessage(), e);
				}
				return null;
			}
		};
	}

	/**
	 * 設定取得多數欄位所有的值
	 *
	 * @param datas
	 *            被搜尋陣列
	 * @param fieldNames
	 *            欄位名稱(若無指定欄位名稱預設全部)
	 */
	@Override
	public <F, T> Function<F, T> getFunction(final Class<T> clazz, final String... fieldNames) {
		return new Function<F, T>() {
			@Override
			public T apply(F paramF) {
				try {
					T obj = clazz.newInstance();
					for (String fieldName : fieldNames) {
						try {
							FieldUtils.writeField(obj, fieldName, FieldUtils.readField(paramF, fieldName, true), true);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					return obj;
				} catch (InstantiationException | IllegalAccessException e1) {
					log.error(e1.getMessage(), e1);
				}

				return null;
			}
		};
	}

	/**
	 * 複製值進給予的class
	 *
	 * @param clazz
	 */
	@Override
	public <F, T> Function<F, T> getFunction(final Class<T> clazz) {
		return new Function<F, T>() {
			@Override
			public T apply(F paramF) {
				try {
					T obj = clazz.newInstance();
					try {
						BeanUtils.copyProperties(obj, paramF);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
					return obj;

				} catch (InstantiationException | IllegalAccessException e1) {
					log.error(e1.getMessage(), e1);
				}

				return null;
			}
		};
	}

	@Override
	public <F, T> Function<F, T> getFunction(final Class<T> clazz, final String[] originalFieldNames,
			final String[] targetFieldNames) {
		return new Function<F, T>() {
			@Override
			public T apply(F paramF) {
				try {
					T obj = clazz.newInstance();
					for (int i = 0; i < originalFieldNames.length; i++) {
						String originalFieldName = originalFieldNames[i];
						String targetFieldName = targetFieldNames[i];
						try {
							FieldUtils.writeField(obj, targetFieldName,
									FieldUtils.readField(paramF, originalFieldName, true), true);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					return obj;
				} catch (InstantiationException | IllegalAccessException e1) {
					log.error(e1.getMessage(), e1);
				}

				return null;
			}
		};
	}

	@Override
	public <T> Function<T, T> getFunction() {
		return new Function<T, T>() {
			@Override
			public T apply(T input) {
				return input;
			}
		};
	}
}

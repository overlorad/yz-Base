package com.yzfar.www.base.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 集合工具
 */
public final class ListUtil {

	private ListUtil() {
	}

	public static <E> ArrayList<E> newArrayList() {
		return new ArrayList<>();
	}

	@SafeVarargs
	public static <E> ArrayList<E> newArrayList(E... e) {
		return new ArrayList<>(Arrays.asList(e));
	}

	public static <E, V> HashMap<E, V> newHashMap() {
		return new HashMap<>();
	}

	public static <E, V> ConcurrentHashMap<E, V> newConcurrentHashMap() {
		return new ConcurrentHashMap<>();
	}

	public static <E, V> ConcurrentHashMap<E, V> newConcurrentHashMap(int initialCapacity) {
		return new ConcurrentHashMap<>(initialCapacity);
	}

	public static <E, V> ConcurrentHashMap<E, V> newConcurrentHashMap(int initialCapacity, float loadFactor) {
		return new ConcurrentHashMap<E, V>(initialCapacity, loadFactor);
	}

	public static <E> Set<E> newSynchronizedSet() {
		return Collections.synchronizedSet(new HashSet<>());
	}

	@SafeVarargs
	public static <E> Set<E> newSynchronizedSet(E... e) {
		return Collections.synchronizedSet(new HashSet<>(Arrays.asList(e)));
	}

	public static <E> Set<E> newSynchronizedSet(int initialCapacity, float loadFactor) {
		return Collections.synchronizedSet(new HashSet<>(initialCapacity, loadFactor));
	}

	public static <E, V> Map<E, V> unmodifiableMap(Map<E, V> map) {
		return Collections.unmodifiableMap(map);
	}

	public static <E> List<E> unmodifiableList(List<E> list) {
		return Collections.unmodifiableList(list);
	}

}

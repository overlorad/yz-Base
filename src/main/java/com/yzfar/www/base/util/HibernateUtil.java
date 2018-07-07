package com.yzfar.www.base.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @version:1.0.0
 * @Description: （Hibernate工具）
 * @Author: chengpeng
 * @Date: 18:55 2018/5/25
 */
public interface HibernateUtil {
    <T> Collection<T> getAll(Class<T> clazz);

    <T> boolean deleteById(Class<T> clazz, Serializable id);

    <T> boolean deleteByIds(Class<T> clazz, Serializable[] ids);

    <T> boolean deletes(Collection<T> objList);

    <T> boolean deleteAll(Class<T> clazz);

    Serializable save(Object obj);

    <T> boolean saves(Collection<T> objList);

    boolean upData(Object obj);

    <T> boolean upData(Collection<T> objList);

    <T> T get(Class<T> clazz, Serializable id);

    int[] executeSql(String... sql);

    Map<String, Object> queryForMap(String sql);
}

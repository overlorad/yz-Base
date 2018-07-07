package com.yzfar.www.base.support.Hibernate;

/**
 * @version:1.0.0
 * @Description: （描述）
 * @Author: chengpeng
 * @Date: 19:23 2018/5/25
 */
public class HandleDataSource {
    private static final ThreadLocal<String> holder = new ThreadLocal<String>();

    public static void putDataSource(String datasource) {
        holder.set(datasource);
    }

    public static String getDataSource() {
        return (String) holder.get();
    }

    public static void clear() {
        holder.remove();
    }
}

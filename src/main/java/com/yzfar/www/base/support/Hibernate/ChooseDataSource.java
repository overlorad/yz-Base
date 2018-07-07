package com.yzfar.www.base.support.Hibernate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version:1.0.0
 * @Description: （描述）
 * @Author: chengpeng
 * @Date: 19:21 2018/5/25
 */
public class ChooseDataSource extends AbstractRoutingDataSource {
    public static Map<String, List<String>> METHODTYPE = new HashMap<String, List<String>>();

    protected Object determineCurrentLookupKey() {
        return HandleDataSource.getDataSource();
    }

    public void setMethodType(Map<String, String> map) {
        for (String key : map.keySet()) {
            List<String> v = new ArrayList<String>();
            String[] types = ((String) map.get(key)).split(",");
            for (String type : types) {
                if (StringUtils.isNotBlank(type)) {
                    v.add(type);
                }
            }
            METHODTYPE.put(key, v);
        }
    }
}

package com.yzfar.www.base.support.Hibernate.impl;

import com.yzfar.www.base.util.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @version:1.0.0
 * @Description: （描述）
 * @Author: chengpeng
 * @Date: 18:57 2018/5/25
 */
public class HibernateUtilImpl implements HibernateUtil {
    protected Logger logger = LogManager.getLogger();
    private SessionFactory sessionFactory;
    private JdbcTemplate jdbcTemplate;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> Collection<T> getAll(Class<T> clazz) {
        List<T> list = sessionFactory.getCurrentSession().createQuery("from " + clazz.getName(), clazz).list();
        logger.info("query all【{}】", clazz);
        return list;
    }

    @Override
    public <T> boolean deleteById(Class<T> clazz, Serializable id) {
        Session session = sessionFactory.getCurrentSession();
        T t = session.get(clazz, id);
        if (t == null) {
            logger.error("delete {}【{}】 fail,obj not exist", clazz.getName(), id);
            return false;
        }
        try {
            session.delete(t);
            logger.info("delete {}【{}】 ", clazz.getName(), id);
            return true;
        } catch (Exception e) {
            logger.error("delete {}【{}】 fail,{}", clazz.getName(), id, e.getMessage());
            return false;
        }
    }

    @Override
    public <T> boolean deleteByIds(Class<T> clazz, Serializable[] ids) {
        if (ids == null || ids.length == 0) {
            logger.error("deletes 【{}】 isEmpty ", clazz.getName());
            return false;
        }
        Session session = sessionFactory.getCurrentSession();
        for (Serializable id : ids) {
            T t = session.get(clazz, id);
            if (t != null) {
                session.delete(t);
            }
        }
        logger.info("deletes {}【{}】 ", clazz.getName(), Arrays.toString(ids));
        return true;
    }

    @Override
    public <T> boolean deletes(Collection<T> objList) {
        if (objList == null || objList.isEmpty()) {
            logger.error("deletes isEmpty ");
            return false;
        }
        Session session = sessionFactory.getCurrentSession();
        for (T t : objList) {
            session.delete(t);
        }
        logger.info("deletes success ");
        return true;
    }

    @Override
    public <T> boolean deleteAll(Class<T> clazz) {
        return deletes(getAll(clazz));
    }

    @Override
    public Serializable save(Object obj) {
        Session session = sessionFactory.getCurrentSession();
        try {
            Serializable id = session.save(obj);
            logger.info("save 【{}】", obj);
            return id;
        } catch (Exception e) {
            logger.error("save 【{}】 fail", obj);
            return null;
        }
    }

    @Override
    public <T> boolean saves(Collection<T> objList) {
        Session session = sessionFactory.getCurrentSession();
        for (Object t : objList) {
            session.save(t);
        }
        logger.info("saves 【{}】", objList);
        return true;
    }

    @Override
    public boolean upData(Object obj) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.update(obj);
            logger.info("update 【{}】", obj);
            return true;
        } catch (Exception e) {
            logger.error("update 【{}】 fail:{}", obj, e.getMessage());
            return false;
        }
    }

    @Override
    public <T> boolean upData(Collection<T> objList) {
        Session session = sessionFactory.getCurrentSession();
        for (Object t : objList) {
            session.update(t);
        }
        logger.info("updates 【{}】", objList);
        return true;
    }

    @Override
    public <T> T get(Class<T> clazz, Serializable id) {
        logger.info("get {}【{}】 ", clazz, id);
        return sessionFactory.getCurrentSession().get(clazz, id);
    }

    @Override
    public int[] executeSql(String... sql) {
        logger.info("executeSql 【{}】 ", sql.length);
        return jdbcTemplate.batchUpdate(sql);
    }

    @Override
    public Map<String, Object> queryForMap(String sql) {
        logger.info("queryForMap 【{}】 ", sql);
        return jdbcTemplate.queryForMap(sql);
    }
}

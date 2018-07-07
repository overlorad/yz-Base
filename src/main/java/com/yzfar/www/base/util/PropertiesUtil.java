package com.yzfar.www.base.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @version:1.0.0
 * @Description: （描述）
 * @Author: chengpeng
 * @Date: 9:49 2018/5/28
 */
public class PropertiesUtil {
    protected static Logger logger = LogManager.getLogger();

    /**
     * 保存
     */
    public static boolean saveProperties(Properties pro, String path) {
        if (pro == null || path == null) {
            return false;
        }
        PathUtil.mkDirFile(path);
        File file = new File(path);
        if (file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return false;
            }
        }
        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(file);
            pro.store(fo, null);
            logger.info("保存配置文件【{}】到【{}】", pro, path);
            return true;
        } catch (Exception e) {
            logger.error("保存配置文件【{}】到【{}】失败", pro, path);
            return false;
        } finally {
            if (fo != null) {
                try {
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据路径获取
     */
    public static Properties getProperties(String path) {
        if (path == null) {
            return null;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            logger.error("获取配置文件【{}】失败{}", path, e.getMessage());
            return null;
        }
        Properties prop = new Properties();
        try {
            prop.load(fis);
            logger.info("获取配置文件【{}】", path);
            return prop;
        } catch (IOException e) {
            logger.error("获取配置文件【{}】失败{}", path, e.getMessage());
            return null;
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                return null;
            }
        }
    }

    /**
     * 更新
     */
    public static void writeProperties(String profilepath, String key, String value) {
        Properties props = new Properties();
        BufferedInputStream fis = null;
        OutputStream fos = null;
        try {
            fis = new BufferedInputStream(new FileInputStream(profilepath));
            props.load(fis);
            props.put(key, value);
            fos = new FileOutputStream(profilepath);
            props.store(fos, "updata " + key);
            logger.info("更新配置文件【{}】", profilepath);
        } catch (Exception e) {
            logger.error("更新配置文件【{}】失败", profilepath);
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除
     */
    public static void removeProperties(String profilepath, String key) {
        Properties props = new Properties();
        BufferedInputStream fis = null;
        OutputStream fos = null;
        try {
            fis = new BufferedInputStream(new FileInputStream(profilepath));
            props.load(fis);
            props.remove(key);
            fos = new FileOutputStream(profilepath);
            props.store(fos, "Delete " + key);
            logger.info("删除配置文件【{}】：【{}】", profilepath, key);
        } catch (Exception e) {
            logger.error("删除配置文件【{}】：【{}】失败--->{}", profilepath, key, e.getMessage());
        } finally {
            try {
                fos.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
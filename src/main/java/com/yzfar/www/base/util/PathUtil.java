package com.yzfar.www.base.util;

import java.io.File;

/**
 * @version:1.0.0
 * @Description: （路径）
 * @Author: chengpeng
 * @Date: 18:33 2018/5/25
 */
public final class PathUtil {
    private PathUtil() {
    }
    /**
     * 项目路径
     */
    public static String getProjectPath() {
        String osName = System.getProperties().getProperty("os.name");
        if (osName.startsWith("Windows")) {
            return System.getProperty("user.dir") + File.separator;
        } else {
            return File.separator + System.getProperty("user.dir") + File.separator;
        }
    }

    /**
     * 创建目录
     */
    public static void mkDirFile(String filePath) {
        File file = new File(filePath);
        File fileParent = file.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
    }
}

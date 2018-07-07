package com.yzfar.www.base.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @version:1.0.0
 * @Description: （描述）
 * @Author: chengpeng
 * @Date: 9:57 2018/5/28
 */
public final class XmlUtil {
    private XmlUtil() {
    }

    protected static Logger log = LogManager.getLogger();

    /**
     * 保存xml文件,返回路径
     */
    public static <T> String saveToXml(Class<T> clazz, T t, String path) {
        FileWriter writer = null;
        if (path == null) {
            return null;
        }
        PathUtil.mkDirFile(path);
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(clazz);
            Marshaller marshal = context.createMarshaller();
            marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshal.setProperty(Marshaller.JAXB_ENCODING, "GBK");
            writer = new FileWriter(path);
            marshal.marshal(t, writer);
            log.info("保存文件:{}", path);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("保存xml文件失败{}", e);
            return null;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    return null;
                }
            }
        }
        return path;
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T getXMLData(Class<T> Clazz, String filePath) {
        if (filePath == null || !filePath.endsWith(".xml")) {
            return null;
        }
        File f = new File(filePath);
        if (!f.exists()) {
            return null;
        }
        FileReader reader = null;
        try {
            JAXBContext context = JAXBContext.newInstance(Clazz);
            Unmarshaller unmarshal = context.createUnmarshaller();
            Marshaller marshal = context.createMarshaller();
            marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshal.setProperty(Marshaller.JAXB_ENCODING, "GBK");
            reader = new FileReader(filePath);
            return (T) unmarshal.unmarshal(reader);
        } catch (Exception e) {
            log.error("{}XML文件解析错误:{}", filePath, e);
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("xml文件流关闭失败");
                    return null;
                }
            }
        }
    }
}

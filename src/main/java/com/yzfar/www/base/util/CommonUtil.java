package com.yzfar.www.base.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @version:1.0.0
 * @Description: （通用工具）
 * @Author: chengpeng
 * @Date: 19:38 2018/5/25
 */
public class CommonUtil {
    protected static Logger logger = LogManager.getLogger();

    public static void loopSystem() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        logger.error("loop fail{}", e);
                        return;
                    }
                }
            }
        }).start();
    }

    public static boolean validatePort(int port) {
        logger.info("开始检查端口{}", port);
        if (portOccupy(port)) {
            logger.info("已经开启一个实例");
            return true;
        }
        return false;
    }

    /**
     * 端口时候被占用，会一直占用某个端口
     */
    private static boolean portOccupy(int port) {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            return true;
        }
        new ServerThread(socket).start();
        return false;
    }

    static class ServerThread extends Thread {
        ServerSocket socket;

        ServerThread(ServerSocket sk) {
            this.socket = sk;
        }

        @Override
        public void run() {
            Socket accept = null;
            while (true) {
                try {
                    accept = socket.accept();
                } catch (IOException e) {
                    try {
                        accept.close();
                    } catch (IOException e1) {

                    }
                }
            }
        }
    }


}

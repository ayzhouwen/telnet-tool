package com.jcca.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShellUtil {

    private final static Logger logger = LoggerFactory.getLogger(ShellUtil.class);

    public static String execShell(String shell) throws IOException, InterruptedException {
        StringBuilder resultStringBuffer = new StringBuilder();
        String lineToRead;
        Process proc = Runtime.getRuntime().exec(shell);
        InputStream inputStream = proc.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
        // save first line
        if ((lineToRead = bufferedReader.readLine()) != null) {
            resultStringBuffer.append(lineToRead);
        }
        // save next lines
        while ((lineToRead = bufferedReader.readLine()) != null) {
            resultStringBuffer.append("\r\n");
            resultStringBuffer.append(lineToRead);
        }
        // Always reading STDOUT first, then STDERR, exitValue last
        proc.waitFor(); // wait for reading STDOUT and STDERR over
        int exitValue = proc.exitValue();
        if (exitValue != 0) {
            throw new RuntimeException(String.format("执行脚本【%s】失败,没有获取到结果！", shell));
        }
        if (logger.isInfoEnabled()) {
           // logger.info("执行命令【{}】,执行结果 {}", shell, resultStringBuffer.toString());
        }
        return resultStringBuffer.toString();
    }

}


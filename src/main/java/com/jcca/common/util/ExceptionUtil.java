package com.jcca.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ExceptionUtil {


    public static String printStackTraceToString(Throwable e) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        e.printStackTrace(new java.io.PrintWriter(buf, true));
        String expMessage = buf.toString();
        try {
            buf.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return expMessage;
    }


}

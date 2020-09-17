package com.jcca.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtl {
    private static String driver = "oracle.jdbc.driver.OracleDriver"; //驱动

    public static Connection creatConnection(String ip,int port,String instance,String name,String password) throws ClassNotFoundException, SQLException {
        Connection conn;
        String url = "jdbc:oracle:thin:@//"+ip+":"+port+"/"+instance; //连接字符串
        Class.forName(driver);
        DriverManager.setLoginTimeout(1);
        conn = DriverManager.getConnection(url, name, password);
        return conn;
    }
}

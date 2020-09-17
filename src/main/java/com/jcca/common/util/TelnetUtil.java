package com.jcca.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.telnet.TelnetClient;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class TelnetUtil {
    private TelnetClient telnet = new TelnetClient("VT100");

    private InputStream in;

    private PrintStream out;

    private static final String DEFAULT_AIX_PROMPT = "#";
    private static final String ENTER_COMMAND_ARROW = ">";
    private static final String ENTER_COMMAND_BRACKETS = "]";
    private static final String ENTER="\n";


    /**
     * telnet 端口
     */
    private String port;

    /**
     * 用户名
     */
    private String user;

    /**
     * 登录密码
     */
    private String password;


    /**
     * 进入特权模式密码
     */
    private String password2;

    /**
     * 接口名
     */
    private String interfaceName;

    /**
     * IP 地址
     */
    private String ip;

    public TelnetUtil(String ip, String user, String password,String password2,String port,String interfaceName) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password = password;
        this.password2=password2;
        this.interfaceName=interfaceName;
    }



    /**
     * @return boolean 连接成功返回true，否则返回false
     */
    public boolean connect() {

        boolean isConnect = true;

        try {
            telnet.connect(ip, Integer.parseInt(port));
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());
            telnet.setKeepAlive(true);
            write(password);
            String msg=readUntil(ENTER_COMMAND_ARROW);
            log.info(msg);
            write("en");
            msg=readUntil("\n");
            write(password2);
            log.info(msg);
            msg=readUntil("\n");
            log.info(msg);
        } catch (Exception e) {
            isConnect = false;
            log.error("获取路由器数据异常:",e);
            return isConnect;
        }
        return isConnect;
    }

    public String getInterfaceData(){
        write("sh int "+interfaceName);
        String msg=readUntil("--More--");
        log.info("获取到的接口数据:"+msg);
        return msg;
    }


    public void su(String user, String password) {
        try {
            write("su" + " - " + user);
            readUntil("Password:");
            write(password);
            readUntil(DEFAULT_AIX_PROMPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readUntil(String pattern) {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            char ch = (char) in.read();
            while (true) {
                //System.out.print(ch);// ---需要注释掉
                sb.append(ch);
                if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        return sb.toString();
                    }
                }
                ch = (char) in.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void write(String value) {
        try {
            out.println(value);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String sendCommand(String command) {
        try {
            write(command);
            return readUntil(DEFAULT_AIX_PROMPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void disconnect() {
        try {
            telnet.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getNowDate() {
        this.connect();
        String nowDate = this.sendCommand("date|awk '{print $2,$3,$4}'");
        String[] temp = nowDate.split("\r\n");
        // 去除命令字符串
        if (temp.length > 1) {
            nowDate = temp[0];
        } else {
            nowDate = "";
        }
        this.disconnect();
        return nowDate;
    }

    public static void main(String[] args) {
//        TelnetUtil telnet = new TelnetUtil("192.168.59.1", "cisco", "Pwd@1234","cisco","23","Serial0/1/0:0");
//        telnet.connect();
//        String data=telnet.getInterfaceData();
//
//        telnet.disconnect();

        String str=" 5 minute input rate 0 bits/sec, 0 packets/sec\n" +
                "  5 minute output rate 62000 bits/sec, 62 packets/sec\n" +
                "     252549 packets input, 25395332 bytes, 0 no buffer\n" +
                "     Received 42746 broadcasts (0 IP multicasts)\n" +
                "     5 runts, 0 giants, 0 throttles \n" +
                "     14 input errors, 14 CRC, 0 frame, 0 overrun, 0 ignored, 9 abort\n" +
                "     1321820 packets output, 153215018 bytes, 0 underruns\n" +
                "     0 output errors, 0 collisions, 0 interface resets\n" +
                "     0 unknown protocol drops\n" +
                "     0 output buffer failures, 0 output buffers swapped out";
        Pattern pattern = Pattern.compile("packets input,\\s+\\d+");
        Matcher matcher = pattern.matcher(str);
        // 遍例所有匹配的序列
//        while (matcher.find()) {
//            System.out.print("Start index: " + matcher.start());
//            System.out.print(" End index: " + matcher.end() + " ");
//            System.out.println(matcher.group());
//        }
        matcher.find();
        String data=matcher.group();
         pattern = Pattern.compile("\\d+");
         matcher = pattern.matcher(data);
        matcher.find();
        matcher.group();


    }
}

package com.jcca.logic.middle.collect.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.jcca.common.util.MyIdUtil;
import com.jcca.common.util.TelnetUtil;
import com.jcca.data.dao.common.entity.InterfaceMonitor;
import com.jcca.data.dao.common.mapper.InterfaceMonitorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 采集交换机速率
 */
@Service
@Slf4j
public class CollectInterfaceImpl {
    /**
     * 是否暂停
     */
    private  volatile  boolean pause=false;

    @Value("${route.ip}")
    private  String ip;

    @Value("${route.telnet-port}")
    private  String telnetPort;

    @Value("${route.user}")
    private  String user;


    @Value("${route.password}")
    private  String password;

    @Value("${route.password2}")
    private  String password2;


    @Value("${route.interface-name}")
    private  String interfaceName;

    // 采集间隔时间
    @Value("${route.interval-ms}")
    private  long intervalMs;

    private Thread selfThread=null;
    @Autowired
    private InterfaceMonitorMapper interfaceMonitorMapper;

    public void init(){
        selfThread=new Thread(()->{
            while (true){
                try {

                    if (!pause){
                        TelnetUtil telnet = new TelnetUtil(ip, user, password,password2,telnetPort,interfaceName);
                        //  System.setOut(new PrintStream("D:/telnet.txt"));
                        telnet.connect();
                        String data=telnet.getInterfaceData();
                        long inBytes= analysisData("packets input,",data);
                        long outBytes= analysisData("packets output,",data);
                        telnet.disconnect();
                        InterfaceMonitor interfaceMonitor=new InterfaceMonitor();
                        interfaceMonitor.setIp(ip);
                        interfaceMonitor.setCollectDate(DateUtil.format(new Date(), "yyyyMMddHHmmss"));
                        interfaceMonitor.setId(MyIdUtil.getIncId());
                        interfaceMonitor.setInBytes(inBytes);
                        interfaceMonitor.setOutBytes(outBytes);
                        interfaceMonitor.setInterfaceName(interfaceName);
                        interfaceMonitor.setIntervalMs(intervalMs);
                        interfaceMonitorMapper.insert(interfaceMonitor);
                    }
                    try {
                        Thread.sleep(intervalMs);
                    } catch (InterruptedException e) {
                        log.error("CollectInterfaceImpl监控线程被中断",e);
                    }
                } catch (Exception e) {
                    log.error("CollectInterfaceImpl监控线程 处理异常:",e);
                }catch (Throwable e){
                    log.error("CollectInterfaceImpl监控线程 崩溃:",e);
                }
            }
        });
        selfThread.setName("CollectInterface-"+ DateUtil.now());
        selfThread.start();
    }


    /**
     * 解析数据返回数字
     * @return
     */
    private long analysisData(String search,String olddata){
        Pattern pattern = Pattern.compile(search+"\\s+\\d+");
        Matcher matcher = pattern.matcher(olddata);
        if (matcher.find()){
            String data=matcher.group();
            pattern = Pattern.compile("\\d+");
            matcher = pattern.matcher(data);
           if( matcher.find()){
             return Convert.toLong(matcher.group());
           };

        };


        return -1;
    };

}

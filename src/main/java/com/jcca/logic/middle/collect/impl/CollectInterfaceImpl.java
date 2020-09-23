package com.jcca.logic.middle.collect.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jcca.common.config.mybits.PagePlugin;
import com.jcca.common.util.MyDateUtil;
import com.jcca.common.util.MyIdUtil;
import com.jcca.common.util.TelnetUtil;
import com.jcca.data.dao.common.entity.InterfaceMonitor;
import com.jcca.data.dao.common.mapper.InterfaceMonitorMapper;
import com.jcca.logic.middle.business.IInterfaceMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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

    @Autowired
    private IInterfaceMonitorService interfaceMonitorService;

    public void init(){
        selfThread=new Thread(()->{
            while (true){
                try {

                    if (!pause){
                        InterfaceMonitor interfaceMonitor=  getCollectData();
                        //获取上次的最新数据
                        IPage<InterfaceMonitor> pageObj = PagePlugin.startPageT(1, 1, InterfaceMonitor.class);
                        QueryWrapper<InterfaceMonitor> query = new QueryWrapper<InterfaceMonitor>();
                        query.orderByDesc("id");
                        IPage<InterfaceMonitor> result = interfaceMonitorService.page(pageObj, query);
                        List<InterfaceMonitor> list = result.getRecords();
                        if (CollUtil.isNotEmpty(list)){
                            InterfaceMonitor prev=list.get(0);
                            interfaceMonitor.setInBytesDiff(interfaceMonitor.getInBytes()-prev.getInBytes());
                            interfaceMonitor.setOutBytesDiff(interfaceMonitor.getOutBytes()-prev.getOutBytes());
                            Date now=new Date();
                            interfaceMonitor.setIntervalMs(DateUtil.betweenMs(now, MyDateUtil.yyyyMMddHHmmss2Date(prev.getCollectDate())));
                        }
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



    public InterfaceMonitor getCollectData(){
        TelnetUtil telnet = new TelnetUtil(ip, user, password,password2,telnetPort,interfaceName);
        //  System.setOut(new PrintStream("D:/telnet.txt"));
        telnet.connect();
        String data=telnet.getInterfaceData();
        Date now=new Date();
        long inBytes= analysisData("packets input,",data);
        long outBytes= analysisData("packets output,",data);
        telnet.disconnect();
        InterfaceMonitor interfaceMonitor=new InterfaceMonitor();
        interfaceMonitor.setIp(ip);
        interfaceMonitor.setCollectDate(DateUtil.format(now, "yyyyMMddHHmmss"));
        interfaceMonitor.setId(MyIdUtil.getIncId());
        interfaceMonitor.setInBytes(inBytes);
        interfaceMonitor.setOutBytes(outBytes);
        interfaceMonitor.setInterfaceName(interfaceName);
        interfaceMonitor.setIntervalMs(intervalMs);
        interfaceMonitor.setInBytesDiff(0L);
        interfaceMonitor.setOutBytesDiff(0L);
        return interfaceMonitor;
    }

}

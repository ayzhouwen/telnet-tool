package com.jcca.controller.ext;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jcca.common.bean.ResultVo;
import com.jcca.common.config.mybits.PagePlugin;
import com.jcca.common.util.MyDateUtil;
import com.jcca.common.util.MyIdUtil;
import com.jcca.data.dao.common.entity.DispatchMonitor;
import com.jcca.data.dao.common.entity.InterfaceMonitor;
import com.jcca.data.dao.common.mapper.DispatchMonitorMapper;
import com.jcca.logic.middle.business.IInterfaceMonitorService;
import com.jcca.logic.middle.business.impl.IDispatchMonitorService;
import com.jcca.logic.middle.collect.impl.CollectInterfaceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 提供外部接口
 * 
 * @author Lvyp
 *
 */
@Controller
@RequestMapping("/business")
@Slf4j
public class BusinessController {
    @Autowired
    IInterfaceMonitorService interfaceMonitorService;

    @Value("${route.ip}")
    private  String ip;

    @Value("${route.interface-name}")
    private  String interfaceName;


    @Autowired
    private IDispatchMonitorService dispatchMonitorService;

    @Autowired
    private DispatchMonitorMapper dispatchMonitorMapper;

    @Autowired
    CollectInterfaceImpl collectInterfaceImpl;
    @GetMapping("/index")
    public String index() {
        return "business/index";
    }

    @PostMapping("/list")
    @ResponseBody
    public ResultVo<?> list(Integer page, Integer limit) {

        IPage<InterfaceMonitor> pageObj = PagePlugin.startPageT(page, limit, InterfaceMonitor.class);
        QueryWrapper<InterfaceMonitor> query = new QueryWrapper<InterfaceMonitor>();
        query.orderByDesc("id");
        IPage<InterfaceMonitor> result = interfaceMonitorService.page(pageObj, query);
        List<InterfaceMonitor> list = result.getRecords();
        if (!list.isEmpty()) {
            list.forEach(e->{
                e.setCollectDate(MyDateUtil.yyyyMMddHHmmss2Date(e.getCollectDate()).toString());
            });
        }
        Map resultMap=new HashMap();
        resultMap.put("total",pageObj.getTotal());
        resultMap.put("list",list);
        return ResultVo.success(resultMap);
    }



    @GetMapping("/collect_index")
    public String collect_index() {
        return "business/collect_index";
    }

    @PostMapping("/collect_index_list")
    @ResponseBody
    public ResultVo<?> collect_index_list(Integer page, Integer limit) {

        IPage<DispatchMonitor> pageObj = PagePlugin.startPageT(page, limit, DispatchMonitor.class);
        QueryWrapper<DispatchMonitor> query = new QueryWrapper<>();
        query.orderByDesc("id");
        IPage<DispatchMonitor> result = dispatchMonitorService.page(pageObj, query);
        List<DispatchMonitor> list = result.getRecords();
        Map resultMap=new HashMap();
        resultMap.put("total",pageObj.getTotal());
        resultMap.put("list",list);
        return ResultVo.success(resultMap);
    }


    @PostMapping("/update_dispatch_monitor")
    @ResponseBody
    public ResultVo<?> update_dispatch_monitor(@RequestBody JSONObject jsonObject) {
        log.info("收到主节点发来的telnet消息:{}"+ JSON.toJSONString(jsonObject));
        //0:开始,1:结束
        try {
            Integer optFlag=jsonObject.getInteger("optFlag");
            if (optFlag.equals(0)){
                DispatchMonitor dm=new DispatchMonitor();
                dm.setCycleId(jsonObject.getLong("cycleId"));
                dm.setStationGroupId(jsonObject.getString("stationGroupId"));
                dm.setStartTime(MyDateUtil.ms2yyyyMMddHHmmss(jsonObject.getLong("startTime")));
                dm.setIp(ip);
                dm.setInterfaceName(interfaceName);
                dm.setId(MyIdUtil.getIncId());
                InterfaceMonitor interfaceMonitor=  collectInterfaceImpl.getCollectData();
                dm.setOneInBytes(interfaceMonitor.getInBytes());
                dm.setOneOutBytes(interfaceMonitor.getOutBytes());
                dispatchMonitorService.save(dm);
            }else if (optFlag.equals(1)){
                QueryWrapper<DispatchMonitor> query = new QueryWrapper<>();
                query.eq("cycle_id", jsonObject.getLong("cycleId"));
                query.eq("station_group_id", jsonObject.getLong("stationGroupId"));
//                DispatchMonitor dm=dispatchMonitorMapper.getInfo(jsonObject.getString("stationGroupId"),jsonObject.getLong("cycleId"));
                DispatchMonitor dm=dispatchMonitorMapper.selectOne(query);
                InterfaceMonitor interfaceMonitor=  collectInterfaceImpl.getCollectData();
                dm.setTwoInBytes(interfaceMonitor.getInBytes());
                dm.setTwoOutBytes(interfaceMonitor.getOutBytes());
                dm.setInBytesDiff(dm.getTwoInBytes()-dm.getOneInBytes());
                dm.setOutBytesDiff(dm.getTwoOutBytes()-dm.getOneOutBytes());
                dm.setSysDispatch(jsonObject.getLong("dispatch"));
                dm.setSysSend(jsonObject.getLong("send"));
                dm.setSysRevice(jsonObject.getLong("revice"));
                dm.setSysError(jsonObject.getLong("error"));


                //计算调度器运行时间
                List<Long> sortlist=new ArrayList<>();
                sortlist.add(jsonObject.getLong("lastSendTime"));
                sortlist.add(jsonObject.getLong("lastDispatchTime"));
                sortlist.add(jsonObject.getLong("lastReviceTime"));
                sortlist.add(jsonObject.getLong("lastErrorTime"));
                sortlist.add(jsonObject.getLong("lastHandleCompleteTime"));
                sortlist.add(jsonObject.getLong("lastHandleErrorTime"));
                sortlist.sort(Comparator.reverseOrder());
                dm.setSysRuntimeMs(sortlist.get(0)-jsonObject.getLong("startTime"));
                dm.setSysLastSendTime(MyDateUtil.ms2yyyyMMddHHmmss(jsonObject.getLong("lastSendTime")));
                dm.setSysLastDispatchTime(MyDateUtil.ms2yyyyMMddHHmmss(jsonObject.getLong("lastDispatchTime")));
                dm.setSysLastReviceTime(MyDateUtil.ms2yyyyMMddHHmmss(jsonObject.getLong("lastReviceTime")));
                dm.setSysLastErrorTime(MyDateUtil.ms2yyyyMMddHHmmss(jsonObject.getLong("lastErrorTime")));
                dm.setSysLastHandleCompleteTime(MyDateUtil.ms2yyyyMMddHHmmss(jsonObject.getLong("lastHandleCompleteTime")));
                dm.setSysLastHandleErrorTime(MyDateUtil.ms2yyyyMMddHHmmss(jsonObject.getLong("lastHandleErrorTime")));
                dm.setSysHandleComplete(jsonObject.getLong("handleComplete"));
                dm.setSysHandleError(jsonObject.getLong("handleError"));
                dm.setSysSumSendByte(jsonObject.getLong("sumSendByte"));
                dm.setSysSumReceiveByte(jsonObject.getLong("sumReceiveByte"));
                dm.setEndTime(DateUtil.now());
                ;
                long ms=DateUtil.betweenMs(DateUtil.parse(dm.getStartTime()),DateUtil.parse(dm.getEndTime()));
                dm.setIntervalMs(ms);
                dispatchMonitorService.updateById(dm);
            }else{
                ResultVo.collectError("optFlag 不能为空");
            }
        } catch (Exception e) {
            log.error("设置telnet消息异常{}",JSON.toJSONString(jsonObject),e);
        }
        return ResultVo.success("设置成功");
    }






}

package com.jcca.controller.ext;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jcca.common.bean.ResultVo;
import com.jcca.common.config.mybits.PagePlugin;
import com.jcca.common.util.MyDateUtil;
import com.jcca.data.dao.common.entity.InterfaceMonitor;
import com.jcca.data.dao.common.mapper.InterfaceMonitorMapper;
import com.jcca.logic.middle.business.IInterfaceMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

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
    @GetMapping("/index")
    public String index() {
        return "business/index";
    }

    @PostMapping("/list")
    @ResponseBody
    public ResultVo<?> list(Integer page, Integer limit) {

        IPage<InterfaceMonitor> pageObj = PagePlugin.startPageT(page, limit, InterfaceMonitor.class);
        QueryWrapper<InterfaceMonitor> query = new QueryWrapper<InterfaceMonitor>();
        query.orderByDesc("collect_date");
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
}

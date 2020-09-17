package com.jcca.controller.ext;

import com.alibaba.fastjson.JSONObject;
import com.jcca.common.bean.ResultVo;
import com.jcca.logic.middle.business.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sql")
public class SqlController {
    @Autowired
    private CommonService commonService;
    @GetMapping("/index")
    public String index(Model model) {
        return "sql/index";
    }

    @PostMapping("/selectsql")
    @ResponseBody
    public ResultVo<?> selectsql(@RequestBody JSONObject obj) {
        String sql=obj.getString("sql");
        List<?> list= commonService.runSelectSql(sql);
        return ResultVo.success(list);
    }

    @PostMapping("/gettable")
    @ResponseBody
    public ResultVo<?> gettable(@RequestBody JSONObject obj) {
        String sql="select * from sqlite_master ORDER BY type desc,name ";
       List<?> list= commonService.runSelectSql(sql);
        return ResultVo.success(list);
    }

}

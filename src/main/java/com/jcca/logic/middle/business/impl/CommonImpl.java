package com.jcca.logic.middle.business.impl;

import com.jcca.data.dao.common.mapper.CommonMapper;
import com.jcca.logic.middle.business.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommonImpl implements CommonService {


	@Autowired
	private CommonMapper commonMapper;

	@Override
	public List<Map<String, Object>> runSelectSql(String sql) {
		// todo 安全检测
		List<Map<String, Object>> list = commonMapper.runSelectSql(sql);
		return list;
	}
}

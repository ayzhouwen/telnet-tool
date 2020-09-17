package com.jcca.logic.middle.business;

import java.util.List;
import java.util.Map;

public interface CommonService {
	

	List<Map<String, Object>> runSelectSql(String sql);

}

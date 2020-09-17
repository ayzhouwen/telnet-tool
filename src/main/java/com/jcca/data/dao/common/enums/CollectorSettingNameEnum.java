package com.jcca.data.dao.common.enums;

/**
 * 系配置
 * @author Lvyp
 *
 */
public enum CollectorSettingNameEnum {
	
	/**
	 * 拉去NITSM资产的访问地址
	 */
	NITSM_GET_ASSET_URL,
	/**
	 * 推送高警地址
	 */
	NITSM_SEND_ALARM,
	/**
	 * 本车站IP
	 */
	STATION_IP,
	/**
	 * SNMP 初始化的端口
	 */
	SNMP_PORT,
	/**
	 * 是否首次启动
	 */
	FIRST_START;
}

package com.jcca.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.hutool.core.util.StrUtil;

/**
 * 正则工具
 * @author Lvyp
 *
 */
public class AppPattenUtils {
	
	
	private static Pattern OIDPATTERN =Pattern.compile("\\.*(\\d+)\\s*$");
	private static Pattern NUMBERPATTERN =Pattern.compile("^[0-9]*$");
	private static Pattern DOUBLEPATTERN =Pattern.compile("^(\\d|[1-9]\\d|100)(\\.\\d{1,2})?$");
	private static Pattern IPPATTERN =Pattern.compile("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");
	
	/**
	 * 正则判断OID
	 * @param oid
	 * @return
	 */
	public static Boolean isOid(String oid) {
		Matcher matcher = OIDPATTERN.matcher(oid);
		return matcher.find();
	}

	/**
	 * 正则判断是否正整数
	 * @param oid
	 * @return
	 */
	public static Boolean isNumber(String number) {
		if(StrUtil.isEmpty(number)) {
			return false;
		}
		Matcher matcher = NUMBERPATTERN.matcher(number);
		return matcher.find();
	}
	
	/**
	 * 是否为小数
	 * @param param
	 * @return
	 */
	public static Boolean isDouble(String param) {
		if(StrUtil.isEmpty(param)) {
			return false;
		}
		Matcher matcher = DOUBLEPATTERN.matcher(param);
		return matcher.find();
	}
	
	/**
	 * 正则判断是否IP
	 * @param oid
	 * @return
	 */
	public static Boolean isIp(String ip) {
		Matcher matcher = IPPATTERN.matcher(ip);
		return matcher.find();
	}
	
}

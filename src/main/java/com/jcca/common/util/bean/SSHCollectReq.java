package com.jcca.common.util.bean;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * ssh 采集请求参数
 * @author Lvyp
 *
 */
@Data
public class SSHCollectReq {

	/**
	 * 主机地址
	 */
	@NotEmpty(message = "地址不能空")
	private String host;
	/**
	 * 用户名
	 */
	@NotEmpty(message = "用户名不能空")
	private String userName;
	/**
	 * 密码
	 */
	@NotEmpty(message = "密码不能空")
	private String pwd;
	/**
	 * 端口号
	 */
	@NotNull(message = "端口不能空")
	private Integer port;
	/**
	 * 命令
	 */
	@NotEmpty(message = "命令不能空")
	private String command;
	
}

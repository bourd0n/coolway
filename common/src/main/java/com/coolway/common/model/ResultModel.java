package com.coolway.common.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * ajax请求结果封装.
 * 
 * @author chengdong
 */
public class ResultModel implements Serializable {

	public static final int PARAMETER_ERROR = -400;
	public static final int UNAUTHORIZED = -403;
	public static final int SERVER_ERROR = -500;

	private static final long serialVersionUID = 1L;

	private transient Map<String, Object> map = new HashMap<String, Object>();

	private static transient Map<Integer, String> errorMessage = new HashMap<Integer, String>();

	private static String resultCode = "resultCode";
	private static String resultMsg = "resultMsg";

	@JsonIgnore
	public boolean isSuccess() {
		return "0".equals(this.map.get(resultCode).toString());
	}

	@JsonIgnore
	public boolean isFailed() {
		return !isSuccess();
	}

	public ResultModel() {
		this.map.put(resultCode, 0);
		this.map.put(resultMsg, "success");
	}

	public int getResultCode() {
		return Integer.valueOf(this.map.get(resultCode).toString());
	}

	public void setResultCode(int code) {
		this.map.put(resultCode, code);
		this.map.put(resultMsg, errorMessage.get(Integer.valueOf(code)));
	}

	public void setData(Object data) {
		this.map.put("data", data);
	}

	public Object getData() {
		return this.map.get("data");
	}

	public String getResultMsg() {
		return this.map.get(resultMsg).toString();
	}

	static {
		// TODO 移动到配置文件中去
		errorMessage.put(PARAMETER_ERROR, "请求参数不正确");
		errorMessage.put(UNAUTHORIZED, "登陆失败");
		errorMessage.put(SERVER_ERROR, "服务端出现异常");
	}

}

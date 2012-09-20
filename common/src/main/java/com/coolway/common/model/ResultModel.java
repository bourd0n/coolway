package com.coolway.common.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * ajax请求结果封装.
 *
 * @author chengdong
 */
public class ResultModel extends HashMap<String, Object> implements Serializable {

    public static final int PARAMETER_ERROR = -400;
    public static final int UNAUTHORIZED = -403;
    public static final int SERVER_ERROR = -500;

    private static final long serialVersionUID = 1L;

    private static Map<Integer, String> errorMessage = new HashMap<Integer, String>();

    private static String resultCode = "resultCode";
    private static String resultMsg = "resultMsg";

    public boolean isSuccess() {
        return "0".equals(this.get(resultCode).toString());
    }

    public boolean isFailed() {
        return !isSuccess();
    }

    public ResultModel() {
        this.put(resultCode, 0);
        this.put(resultMsg, "success");
    }

    public int getResultCode() {
        return Integer.valueOf(get(resultCode).toString());
    }

    public void setResultCode(int code) {
        this.put(resultCode, code);
        this.put(resultMsg, errorMessage.get(Integer.valueOf(code)));
    }

    public void setData(Object data) {
        this.put("data", data);
    }

    public String getResultMsg() {
        return get(resultMsg).toString();
    }
    
    static {
        // TODO 移动到配置文件中去
        errorMessage.put(PARAMETER_ERROR, "请求参数不正确");
        errorMessage.put(UNAUTHORIZED, "登陆失败");
        errorMessage.put(SERVER_ERROR, "服务端出现异常");
    }

}

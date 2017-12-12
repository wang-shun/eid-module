package com.eid.common.model;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * rpc响应
 * Created by:ruben Date:2016/12/15 Time:下午3:05
 */
public class Response<T> implements Serializable {

    private static final long serialVersionUID = -3230276738684237793L;

    private T result;
    private boolean success;
    private String errorCode;
    private String errorMsg;

    public Response() {
    }

    public Response(T result) {
        this.success = true;
        this.result = result;
    }

    public Response(String errorCode) {
        this.success = false;
        this.errorCode = errorCode;
    }

    public Response(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        success = true;
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.success = false;
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String toString() {
        return Objects.toStringHelper(this).add("success", this.success).add("result", this.result).add("errorCode", this.errorCode).add("errorMsg", this.errorMsg).omitNullValues().toString();
    }
}

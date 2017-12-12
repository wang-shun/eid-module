package com.eid.dispatch.exception;

import com.eid.common.exception.FacadeException;

/**
 * 核心服务通用异常类
 * Created by:ruben Date:2017/3/8 Time:上午9:58
 */
public class DispatchException extends FacadeException {

    public DispatchException(DispatchErrorCode code, String message, Throwable cause) {
        super(null, message, cause);
    }

    public DispatchException(DispatchErrorCode code) {
        this(code, null, null);
    }

    public DispatchException(DispatchErrorCode code, String message) {
        this(code, message, null);
    }
}

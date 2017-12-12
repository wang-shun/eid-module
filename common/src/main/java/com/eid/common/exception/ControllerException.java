package com.eid.common.exception;

import com.eid.common.enums.ErrorCode;
import lombok.Getter;

/**
 * 描述类的作用
 * Created by:ruben Date:2016/12/15 Time:下午3:53
 */
@Getter
public class ControllerException extends RuntimeException {

    private static final long serialVersionUID = 1354640504081427314L;

    private String code;

    public ControllerException(ErrorCode code) {
        super(code.getDesc());
        this.code = code.getCode();
    }

    public ControllerException(ErrorCode code, Throwable throwable) {
        super(throwable);
        this.code = code.getCode();
    }

    public ControllerException(ErrorCode code, String message) {
        super(message);
        this.code = code.getCode();
    }

    public ControllerException(ErrorCode code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code.getCode();
    }

    public ControllerException(String rspCode, String resMsg) {
        super(resMsg);
        this.code = rspCode;
    }

}

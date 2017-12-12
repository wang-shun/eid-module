package com.eid.dispatch;

import com.eid.common.enums.CmdStatus;
import com.eid.dispatch.exception.DispatchErrorCode;
import com.eid.dispatch.exception.DispatchException;

import static com.eid.dispatch.common.MessageHelper.formatMsg;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/8 Time:上午10:54
 */
public class StatusUtils {

    private static final String UNSUPPORTED_STATUS = "非法的[{0}]状态[{1}]";

    /*-------------------------- CmdStatusEnums --------------------------*/
    public static boolean isCmdInitial(String status) {
        CmdStatus enums = getCmdStatusEnum(status);
        return enums == CmdStatus.Initial;
    }

    public static boolean isCmdEnd(String status) {
        CmdStatus enums = getCmdStatusEnum(status);

        switch (enums) {
            case Initial:
            case Wait:
                return false;
            case Success:
            case Processing:
            case Failure:
                return true;
            default:
                throw new DispatchException(DispatchErrorCode.VALUE_NOT_SUPPORT, formatMsg(UNSUPPORTED_STATUS, "命令", status));
        }
    }

    public static boolean isCmdSuccess(String status) {
        CmdStatus enums = getCmdStatusEnum(status);
        return enums == CmdStatus.Success;
    }

    public static boolean isCmdFailure(String status) {
        CmdStatus enums = getCmdStatusEnum(status);
        return enums == CmdStatus.Failure;
    }

    public static CmdStatus getCmdStatusEnum(String status) {
        CmdStatus enums = CmdStatus.getEnumsByCode(status);
        if (enums == null) {
            throw new DispatchException(DispatchErrorCode.VALUE_NOT_SUPPORT, formatMsg(UNSUPPORTED_STATUS, "命令", status));
        }

        return enums;
    }

}

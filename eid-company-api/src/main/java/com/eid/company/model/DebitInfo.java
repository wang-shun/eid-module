package com.eid.company.model;

import com.eid.company.enums.DebitType;
import lombok.Data;

import java.io.Serializable;

/**
 * 扣款
 * Created by:ruben Date:2017/1/21 Time:下午4:09
 */
@Data
public class DebitInfo implements Serializable {

    private static final long serialVersionUID = 3972457948523012741L;

    private String companyId;
    private DebitType debitType;
    private Long money;
    private String content;
}

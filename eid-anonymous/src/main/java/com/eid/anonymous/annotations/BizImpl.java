package com.eid.anonymous.annotations;

import com.eid.common.enums.BizType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/2/8 Time:下午3:40
 */
@Retention(RUNTIME)
@Target({FIELD, METHOD, TYPE, ANNOTATION_TYPE})
public @interface BizImpl {

    BizType[] value();
}


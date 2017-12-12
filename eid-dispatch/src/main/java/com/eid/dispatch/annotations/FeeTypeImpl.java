package com.eid.dispatch.annotations;

import com.eid.common.enums.FeeType;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/13 Time:下午6:18
 */
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD, ANNOTATION_TYPE})
public @interface FeeTypeImpl {

    FeeType[] value();
}

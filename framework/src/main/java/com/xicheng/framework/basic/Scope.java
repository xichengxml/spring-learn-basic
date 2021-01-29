package com.xicheng.framework.basic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/1/17 下午 09:32
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Scope {

	ScopeEnum value() default ScopeEnum.singleton;
}

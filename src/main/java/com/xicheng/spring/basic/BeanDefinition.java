package com.xicheng.spring.basic;

/**
 * description bean定义
 *
 * @author xichengxml
 * @date 2021/1/17 上午 10:23
 */
public class BeanDefinition {

	private ScopeEnum scope;

	private Boolean lazy;

	private Class<?> beanClass;

	public ScopeEnum getScope() {
		return scope;
	}

	public void setScope(ScopeEnum scope) {
		this.scope = scope;
	}

	public Boolean getLazy() {
		return lazy;
	}

	public void setLazy(Boolean lazy) {
		this.lazy = lazy;
	}

	public Class<?> getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}
}

package com.xicheng.framework.basic;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/1/17 上午 10:15
 */
public class LubanApplicationContext {

	private Class<?> configClass;

	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

	private final Map<String, Object> singletonMap = new ConcurrentHashMap<>();

	private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

	public LubanApplicationContext(Class<?> configClass) {
		this.configClass = configClass;
		// <1> 扫描package，得到BeanDefinition
		List<Class<?>> classList = scan(configClass);
		// 遍历这些类，生成BeanDefinition
		generateBeanDefinition(classList);
		// 基于class去创建单例bean
		for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
			String beanName = entry.getKey();
			BeanDefinition beanDefinition = entry.getValue();
			if (!ScopeEnum.singleton.equals(beanDefinition.getScope())) {
				continue;
			}
			try {
				Object o = doCreateBean(beanName, beanDefinition);
				singletonMap.put(beanName, o);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Object doCreateBean(String beanName, BeanDefinition beanDefinition) throws Exception {
		Object bean = beanDefinition.getBeanClass().getDeclaredConstructor().newInstance();
		// 属性填充
		Field[] fields = beanDefinition.getBeanClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Autowired.class)) {
				Object fieldBean = getBean(field.getName());
				field.setAccessible(true);
				field.set(bean, fieldBean);
			}
		}
		// aware回调
		if (bean instanceof BeanNameAware) {
			((BeanNameAware)bean).setBeanName(beanName);
		}

		// initializingBean
		if (bean instanceof InitializingBean) {
			((InitializingBean)bean).afterPropertiesSet();
		}

		// beanPostProcessor
		for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
			beanPostProcessor.postProcessAfterInitialization(beanName, bean);
		}
		return bean;
	}

	private void generateBeanDefinition(List<Class<?>> classList) {
		if (CollectionUtils.isEmpty(classList)) {
			return;
		}
		for (Class<?> clazz : classList) {
			BeanDefinition beanDefinition = new BeanDefinition();
			beanDefinition.setBeanClass(clazz);
			Component component = clazz.getAnnotation(Component.class);
			String beanName = component.value();
			if (clazz.isAnnotationPresent(Scope.class)) {
				Scope scope = clazz.getAnnotation(Scope.class);
				beanDefinition.setScope(scope.value());
			} else {
				beanDefinition.setScope(ScopeEnum.singleton);
			}
			beanDefinitionMap.put(beanName, beanDefinition);

			// 添加beanPostProcessor
			if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
				BeanPostProcessor beanPostProcessor = null;
				try {
					beanPostProcessor = (BeanPostProcessor) clazz.getDeclaredConstructor().newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				beanPostProcessorList.add(beanPostProcessor);
			}
		}
	}

	private List<Class<?>> scan(Class<?> configClass) {
		ComponentScan annotation = configClass.getAnnotation(ComponentScan.class);
		String path = annotation.value();
		System.out.println(path);
		// 转换成文件路径
		path = path.replace(".", "/");
		ClassLoader classLoader = LubanApplicationContext.class.getClassLoader();
		URL url = classLoader.getResource(path);
		if (url == null) {
			return null;
		}
		File file = new File(url.getFile());
		// 得到class
		List<Class<?>> classList = new ArrayList<>();
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (ArrayUtils.isEmpty(files)) {
				return null;
			}
			for (File f : files) {
				System.out.println(f);
				String absolutePath = f.getAbsolutePath();
				String className = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));
				try {
					Class<?> clazz = classLoader.loadClass(className.replace("\\", "."));
					if (clazz.isAnnotationPresent(Component.class)) {
						classList.add(clazz);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return classList;
	}

	public Object getBean(String beanName) throws Exception {
		BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
		ScopeEnum scope = beanDefinition.getScope();
		if (ScopeEnum.prototype.equals(scope)) {
			return doCreateBean(beanName, beanDefinition);
		} else {
			Object o = singletonMap.get(beanName);
			if (null == o) {
				Object o1 = doCreateBean(beanName, beanDefinition);
				singletonMap.put(beanName, o1);
				return o1;
			}
			return o;
		}
	}
}

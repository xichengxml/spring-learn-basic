package com.xichneg.springboot.starter.common;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/1/31 下午 08:53
 */
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{DateConfigUsingImport.class.getName()};
    }
}

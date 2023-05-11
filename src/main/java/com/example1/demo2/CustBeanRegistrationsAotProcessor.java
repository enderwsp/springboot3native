package com.example1.demo2;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.AnnotationConfigUtils;

import java.util.HashMap;
import java.util.Map;

public class CustBeanRegistrationsAotProcessor implements  BeanDefinitionRegistryPostProcessor {

    private Class<?> appCls;

    public CustBeanRegistrationsAotProcessor(Class<?> appClass) {
        appCls = appClass;
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory bf) throws BeansException {
        System.out.println("1111111111111111111111");
    }


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Map<String, Class<?>> fixedBeanDefines = new HashMap<>();
        try {
            fixedBeanDefines.put("refreshScope", registry.getClass().getClassLoader().loadClass("org.springframework.cloud.context.scope.refresh.RefreshScope"));
            fixedBeanDefines.put("refreshScopeBeanDefinitionEnhancer", registry.getClass().getClassLoader().loadClass("org.springframework.cloud.autoconfigure.RefreshAutoConfiguration$RefreshScopeBeanDefinitionEnhancer"));


            AnnotationConfigUtils.registerAnnotationConfigProcessors(registry);
            fixedBeanDefines.put(appCls.getSimpleName(), registry.getClass().getClassLoader().loadClass(appCls.getName()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        fixedBeanDefines.forEach((name, obj) -> {
            //注册Bean定义，容器根据定义返回bean
            BeanDefinitionBuilder builder =
                    BeanDefinitionBuilder.genericBeanDefinition(obj);
            BeanDefinition definition = builder.getRawBeanDefinition();
            registry.registerBeanDefinition(name, definition);
        });
    }

}

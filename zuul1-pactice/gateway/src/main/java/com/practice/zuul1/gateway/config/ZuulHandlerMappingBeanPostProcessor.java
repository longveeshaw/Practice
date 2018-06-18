package com.practice.zuul1.gateway.config;

import com.practice.zuul1.gateway.interceptor.KeyShiftInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.context.annotation.Configuration;

/**
 * @author Luo Bao Ding
 * @since 2018/6/16
 */
@Configuration
public class ZuulHandlerMappingBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

    private KeyShiftInterceptor keyShiftInterceptor;


    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (bean instanceof ZuulHandlerMapping) {
            ZuulHandlerMapping zuulHandlerMapping = (ZuulHandlerMapping) bean;
            zuulHandlerMapping.setInterceptors(keyShiftInterceptor);

        }
        return super.postProcessAfterInstantiation(bean, beanName);
    }

    @Autowired
    public void setKeyShiftInterceptor(KeyShiftInterceptor keyShiftInterceptor) {
        this.keyShiftInterceptor = keyShiftInterceptor;
    }
}

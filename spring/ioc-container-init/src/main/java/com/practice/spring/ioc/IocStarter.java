package com.practice.spring.ioc;

import com.practice.spring.ioc.bean.BeanA;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class IocStarter {

    public static void main(String[] args) {
        ClassPathResource resource = new ClassPathResource("applicationContext.xml");
//        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
//        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
//        reader.loadBeanDefinitions(resource);

        BeanFactory bf = new XmlBeanFactory(resource);
        BeanA beanA = (BeanA) bf.getBean("beanA");

        System.out.println("=========================");

        System.out.println("beanA = " + beanA);

    }
}

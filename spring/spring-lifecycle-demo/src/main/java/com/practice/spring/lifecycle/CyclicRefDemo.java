package com.practice.spring.lifecycle;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class CyclicRefDemo {
    public static void main(String[] args) {

        // 1 下面两句装载配置文件，并启动容器
        Resource res=new ClassPathResource("application.xml");
        BeanFactory bf=new XmlBeanFactory(res);

        bf.getBean("cyclicRefA");

    }
}

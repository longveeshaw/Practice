package com.practice.spring.lifecycle;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class BeanLifeCycle {
	
	private static void lifiCycleInBeanFactory(){
		// 1 下面两句装载配置文件，并启动容器
		Resource res=new  ClassPathResource("application.xml");
		BeanFactory bf=new XmlBeanFactory(res);

		// 2 向容器中注册MyBeanPostProcesser处理器
		((ConfigurableBeanFactory)bf).addBeanPostProcessor(new MyBeanPostProcessor());
         //3 向容器中注册MyInstantiationAwareBeanPostProcessor后处理器
		((ConfigurableBeanFactory)bf).addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());

//		==============
		bf.getBean("cyclicRefA");
//================

		//4 第一次从容器中获取car,将角触发容器实例化该Bean,这将引发Bean生命周期方法的调用
		Car car1=(Car)bf.getBean("car");
		car1.introduce();
    	car1.setColor("红色");

    	// 5第二次从容器中获取car，直接从缓存池中取(因为 scope="singleton")
		Car car2=(Car)bf.getBean("car");

		// 6 查看car1和car2是否指向同一引用
		System.out.println("car1==car2"+(car1==car2));

		// 7 关闭容器
		((XmlBeanFactory)bf).destroySingletons();
		
	}
	
	public static void main(String[] args) {
		lifiCycleInBeanFactory();
	}

}
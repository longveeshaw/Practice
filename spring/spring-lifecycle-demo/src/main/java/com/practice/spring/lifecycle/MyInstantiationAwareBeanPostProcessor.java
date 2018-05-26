package com.practice.spring.lifecycle;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

@SuppressWarnings("unchecked")
public class MyInstantiationAwareBeanPostProcessor extends
		InstantiationAwareBeanPostProcessorAdapter {
    
	//1 接口方法：实例化bean前进行调用
	public Object postProcessBeforeInstantiation(Class beanClass,String beanName) throws BeansException {
		//1-1仅对容器中的car-bean进行处理
		   if("car".equals(beanName)){
			  System.out.println("InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation()"); 
		   }
		   
		return null;
	}
	
	//2 接口方法：在实例化bean后进行调用
	public boolean postProcessAfterInstantiation(Object bean, String beanName )
	throws BeansException{
		   if("car".equals(beanName)){
				  System.out.println("InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation()"); 
			   }
		return true;
	}
	
	
	//3接口方法:在设置某个属性时调用
	public  PropertyValues postProcessPropertyValues(
			PropertyValues propertyvalues,
			PropertyDescriptor apropertydescriptor[], Object bean, String beanName)
			throws BeansException{
     //3-1仅对容器中car-bean进行处理，还可以通过post入参进行过滤，
		//仅对car的某个特定属性进行处理
            		if("car".equals(beanName)){
            		  System.out.println("InstantiationAwareBeanPostProcessor.postProcessPropertyValues");	
            		}
				return propertyvalues;
			}
}
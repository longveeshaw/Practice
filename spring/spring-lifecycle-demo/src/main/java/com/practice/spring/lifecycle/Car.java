package com.practice.spring.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Car implements BeanFactoryAware,BeanNameAware,InitializingBean,DisposableBean{
	private String brand;
	private String color;
	private int maxSpeed;
	private BeanFactory beanFactory;
	private String beanName;
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public String getBrand() {
		return brand;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public String getBeanName() {
		return beanName;
	}

	//1、管理Bean生命周期的接口
	public Car(){
		System.out.println("调用Car构造函数");
	}
	
	public void setBrand(String brand) {
		System.out.println("调用setBrand()设置属性");
		this.brand = brand;
	}

   public void introduce(){
	   System.out.println("bradn:"+brand+";color"+color+"; maxSpeed:"+maxSpeed);
   }
   
   //2、BeanFactoryAware接口方法
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("调用BeanFactoryAware.setBeanFactory()");
		this.beanFactory=beanFactory;
	}
     
	//3、BeanNameAware接口方法
	public void setBeanName(String beanName) {
		System.out.println("调用BeanNameAware.setBeanName()");
		this.beanName=beanName;
	}
    
	
	//4 nitializingBean接口方法
	public void afterPropertiesSet() throws Exception {
		System.out.println("调用InitializingBean.afterPropertiesSet()");
	}
    
	// 5  DisposableBean接口方法
	public void destroy() throws Exception {
		System.out.println("调用DisposableBean.destroy()");
	}
	
	// 6  通过<bean>的init-method属性指定的初始化方法
    public void myInit(){
    	System.out.println("调用inti-method所指定的myInit(),将maxSpeed设置为240.");
    	this.maxSpeed=240;
    }
    
    //7  通过<bean>的destory-method属性指定的销毁方法
    public void myDestory(){
    	System.out.println("调用destory-method所指定的myDestory()方法。");
    }
}
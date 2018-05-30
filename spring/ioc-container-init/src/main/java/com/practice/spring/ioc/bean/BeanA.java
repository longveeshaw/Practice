package com.practice.spring.ioc.bean;

public class BeanA {
    private String username;

    private BeanB beanB;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BeanB getBeanB() {
        return beanB;
    }

    public void setBeanB(BeanB beanB) {
        this.beanB = beanB;
    }

    @Override
    public String toString() {
        return "BeanA{" +
                "username='" + username + '\'' +
                ", beanB=" + beanB +
                '}';
    }
}

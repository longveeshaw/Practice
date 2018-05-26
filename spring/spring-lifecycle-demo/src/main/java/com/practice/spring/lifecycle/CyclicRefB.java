package com.practice.spring.lifecycle;

public class CyclicRefB {

    private CyclicRefA cyclicRefA;

    public CyclicRefA getCyclicRefA() {
        return cyclicRefA;
    }

    public void setCyclicRefA(CyclicRefA cyclicRefA) {
        this.cyclicRefA = cyclicRefA;
    }
}

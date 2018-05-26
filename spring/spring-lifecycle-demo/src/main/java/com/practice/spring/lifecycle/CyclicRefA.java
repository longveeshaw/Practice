package com.practice.spring.lifecycle;

public class CyclicRefA {

    private CyclicRefB cyclicRefB;

    public CyclicRefB getCyclicRefB() {
        return cyclicRefB;
    }

    public void setCyclicRefB(CyclicRefB cyclicRefB) {
        this.cyclicRefB = cyclicRefB;
    }
}

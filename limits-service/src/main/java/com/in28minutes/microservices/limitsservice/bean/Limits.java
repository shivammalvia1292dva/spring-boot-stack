package com.in28minutes.microservices.limitsservice.bean;

public class Limits {
    private int minmum;
    private int maximum;

    public int getMinmum() {
        return minmum;
    }

    public Limits() {
    }

    public void setMinmum(int minmum) {
        this.minmum = minmum;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public Limits(int minmum, int maximum) {
        this.minmum = minmum;
        this.maximum = maximum;
    }
}

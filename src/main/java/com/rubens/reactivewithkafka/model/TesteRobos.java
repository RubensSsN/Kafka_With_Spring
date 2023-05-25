package com.rubens.reactivewithkafka.model;

public class TesteRobos {

    public String name;

    public Integer nmr;

    public TesteRobos(String name, Integer nmr) {
        this.name = name;
        this.nmr = nmr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNmr() {
        return nmr;
    }

    public void setNmr(Integer nmr) {
        this.nmr = nmr;
    }
}

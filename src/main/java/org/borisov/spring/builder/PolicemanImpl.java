package org.borisov.spring.builder;

import javax.annotation.PostConstruct;

public class PolicemanImpl implements Policeman {

    @InjectByType
    Reccomendator reccomendator;

    @PostConstruct
    public void init() {
        System.out.println(reccomendator.getClass());
    }

    @Override
    public void getOutPeople() {
        System.out.println("все вон, пиф паф!!!");
    }
}

package org.borisov.spring.builder;

public class PolicemanEng implements Policeman {

    @InjectByType
    Reccomendator reccomendator;

    @Override
    public void getOutPeople() {
        System.out.println("Go go go");
    }
}

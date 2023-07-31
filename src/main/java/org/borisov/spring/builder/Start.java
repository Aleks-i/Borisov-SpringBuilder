package org.borisov.spring.builder;

import java.util.HashMap;
import java.util.Map;

public class Start {
    public static void main(String[] args) {
        ApplicationContext context = ApplicationRunner.run("org.borisov.spring.builder", new HashMap<>(Map.of(Policeman.class, PolicemanImpl.class)));
        context.getObject(CoronaDesinfector.class).start(new Room());
    }
}
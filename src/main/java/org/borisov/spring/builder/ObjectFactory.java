package org.borisov.spring.builder;

import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectFactory {
    private final ApplicationContext context;
    private final List<ObjectConfigurator> configurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.context = context;
        Set<Class<? extends ObjectConfigurator>> classes = context.getScaner().getSubTypesOf(ObjectConfigurator.class);
        for (Class<? extends ObjectConfigurator> aClass : classes) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {

        T t = type.getDeclaredConstructor().newInstance();

        configure(t);

        invokeInit(type, t);

        if (type.isAnnotationPresent(Deprecated.class)) {
            return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), type.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("Чтож ты урод пользуешься Deprecated классами " + t.getClass());
                    return method.invoke(t, args);
                }
            });
        }

        return t;
    }

    private static <T> void invokeInit(Class<T> type, T t) throws IllegalAccessException, InvocationTargetException {
        for (Method method : type.getMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t);
            }
        }
    }

    private <T> void configure(T t) {
        configurators.forEach(configurator -> configurator.configure(t, context));
    }
}

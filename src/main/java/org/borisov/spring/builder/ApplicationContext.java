package org.borisov.spring.builder;

import lombok.Getter;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    private final Map<Class, Object> cache = new ConcurrentHashMap<>();
    @Getter
    private Reflections scaner;
    private final JavaConfig config;
    private final ObjectFactory factory;

    public ApplicationContext(String packageToScan, Map<Class, Class> ifc2Impl) {
        scaner = new Reflections(packageToScan);
        config = new JavaConfig(scaner, ifc2Impl);
        factory = new ObjectFactory(this);
    }

    @SneakyThrows
    public <T> T getObject(Class<T> type) {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }
        Class<T> implClass = resolveImpl(type);
        T t = factory.createObject(implClass);

        if (implClass.isAnnotationPresent(Singleton.class)) {
            cache.put(type, t);
        }
        return t;
    }

    private <T> Class<T> resolveImpl(Class<T> type) {
        if (type.isInterface()) {
            type = (Class<T>) config.getImplClass(type);
        }
        return type;
    }
}

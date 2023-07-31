package org.borisov.spring.builder;

public interface Config {
    <T> Class<? extends T> getImplClass(Class<T> type);
}

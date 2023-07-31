package org.borisov.spring.builder;

import java.util.Map;

public class ApplicationRunner {
    public static ApplicationContext run(String packageToScan, Map<Class, Class> ifc2ImplClass) {
        return new ApplicationContext(packageToScan, ifc2ImplClass);
    }
}

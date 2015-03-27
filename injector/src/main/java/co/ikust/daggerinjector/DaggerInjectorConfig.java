package co.ikust.daggerinjector;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * A dummy class that will be used to trick class loader.
 */
public class DaggerInjectorConfig {

    abstract static class Injector<T> {
        public abstract T inject(Object[] modules);
    }

    static HashMap<Type, Injector> injectors = new HashMap<>();


}
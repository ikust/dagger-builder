package co.ikust.daggerinjector;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * A dummy class that will be used to trick class loader.
 */
public class DaggerInjector {

    private abstract static class Injector<T> {
        public abstract T inject(Object[] modules);
    }

    private static HashMap<Type, Injector> injectors = new HashMap<>();


    public static <T> T inject(Class<T> component, Object... modules) {
        //noinspection unchecked
        return ((Injector<T>) injectors.get(component)).inject(modules);
    }
}
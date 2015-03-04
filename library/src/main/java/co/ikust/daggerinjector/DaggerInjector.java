package co.ikust.daggerinjector;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by ivan on 02/03/15.
 */
public class DaggerInjector {

    private abstract static class Injector<T> {
        public abstract T inject(Object[] modules);
    }

    private static HashMap<Type, Injector> injectors = new HashMap<>();

    static {
        injectors.put(Object.class, new Injector() {
            @Override
            public Object inject(Object[] modules) {
                return null;
            }
        });


    }

    public static <T> T inject(Class<T> component, Object... modules) {
        //noinspection unchecked
        return ((Injector<T>) injectors.get(component)).inject(modules);
    }
}

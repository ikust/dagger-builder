package co.ikust.daggerinjector;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by ivan on 02/03/15.
 */
public class DaggerInjectorConfig {

    abstract static class Injector<T> {
        public abstract T inject(Object[] modules);
    }

    static HashMap<Type, Injector> injectors = new HashMap<>();

    static {
        ${injectorsInit}
    }

}

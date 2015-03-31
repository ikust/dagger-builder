package co.ikust.daggerbuilder;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * A dummy class that will be used to trick class loader.
 */
public class DaggerBuilderConfig {

    abstract static class Builder<T> {
        public abstract T build(Object[] modules);
    }

    static HashMap<Type, Builder> builders = new HashMap<>();


}
package co.ikust.daggerbuilder;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by ivan on 02/03/15.
 */
public class DaggerBuilderConfig {

    abstract static class Builder<T> {
        public abstract T build(Object[] modules);
    }

    static HashMap<Type, Builder> builders = new HashMap<>();

    static {
        ${injectorsInit}
    }

}

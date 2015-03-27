package co.ikust.daggerinjector;

/**
 * Created by ivan on 04/03/15.
 */
public class DaggerInjector {

    public static <T> T inject(Class<T> component, Object... modules) {
        //noinspection unchecked
        return (T)DaggerInjectorConfig.injectors.get(component).inject(modules);
    }
}

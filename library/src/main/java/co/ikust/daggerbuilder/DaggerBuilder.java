package co.ikust.daggerbuilder;

/**
 * Created by ivan on 04/03/15.
 */
public class DaggerBuilder {

    public static <T> T build(Class<T> component, Object... modules) {
        //noinspection unchecked
        return (T) DaggerBuilderConfig.builders.get(component).build(modules);
    }
}

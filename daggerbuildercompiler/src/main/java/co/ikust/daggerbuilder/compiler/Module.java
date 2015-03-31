package co.ikust.daggerbuilder.compiler;

/**
 * Created by ivan on 27/03/15.
 */
public class Module {

    private String methodName;

    private String name;

    public Module(String methodName, String name) {
        this.methodName = methodName;
        this.name = name;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getName() {
        return name;
    }
}

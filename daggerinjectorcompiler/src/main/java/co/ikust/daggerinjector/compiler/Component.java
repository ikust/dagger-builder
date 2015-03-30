package co.ikust.daggerinjector.compiler;

import java.util.List;

/**
 * Created by ivan on 03/03/15.
 */
public class Component {

    private String name;

    private String packageName;

    private List<Module> modules;

    public Component(String name, String packageName, List<Module> moduleNames) {
        this.name = name;
        this.packageName = packageName;
        this.modules = moduleNames;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public List<Module> getModules() {
        return modules;
    }
}

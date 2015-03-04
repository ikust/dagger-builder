package co.ikust.daggerinjector.compiler;

import java.util.ArrayList;

/**
 * Created by ivan on 03/03/15.
 */
public class Component {

    private String name;

    private ArrayList<String> moduleNames;

    public Component(String name, ArrayList<String> moduleNames) {
        this.name = name;
        this.moduleNames = moduleNames;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getModuleNames() {
        return moduleNames;
    }
}

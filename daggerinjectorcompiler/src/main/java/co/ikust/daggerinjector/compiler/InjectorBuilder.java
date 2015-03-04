package co.ikust.daggerinjector.compiler;

import java.util.ArrayList;

import co.ikust.simpletemplates.Template;
import co.ikust.simpletemplates.Templates;

/**
 * Created by ivan on 03/03/15.
 */
public class InjectorBuilder {

    private ArrayList<Component> components = new ArrayList<>();


    public void addComponent(String name, ArrayList<String> modules) {
        components.add(new Component(name, modules));
    }

    private Template generateInjector(Component component) {
        Template template = Templates.getInstance().read("injector_init.tpl");

        template.addReplacement("componentName", component.getName());

        for(int i = 0; i < component.getModuleNames().size(); i++) {
            template.addReplacement(
                    "injectImplementation",
                    generateTemplateParam(component.getModuleNames().get(i), i)
            );
        }

        return template;
    }

    private Template generateTemplateParam(String moduleName, int index) {
        Template template = Templates.getInstance().read("module_param.tpl");

        template.addReplacement("moduleName", moduleName);
        template.addReplacement("moduleIndex", String.valueOf(index));

        return template;
    }

    public String createImplementation() {
        Template template = Templates.getInstance().read("DaggerInjector.tpl");

        for(Component component : components) {
            template.addReplacementLine("injectorsInit", generateInjector(component));
        }

        return template.toString();
    }

}

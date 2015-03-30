package co.ikust.daggerinjector.compiler;

import java.util.ArrayList;

import co.ikust.simpletemplates.Template;
import co.ikust.simpletemplates.Templates;

/**
 * Created by ivan on 03/03/15.
 */
public class InjectorConfigBuilder {

    private ArrayList<Component> components = new ArrayList<>();

    public void addComponent(Component component) {
        components.add(component);
    }

    private Template generateInjector(Component component) {
        Template template = Templates.getInstance().read("/injector_init.tpl");

        template.addReplacement("componentName", component.getName());
        template.addReplacement("packageName", component.getPackageName());

        for(int i = 0; i < component.getModules().size(); i++) {
            template.addReplacement(
                    "injectImplementation",
                    generateTemplateParam(component.getModules().get(i), i)
            );
        }

        return template;
    }

    private Template generateTemplateParam(Module module, int index) {
        Template template = Templates.getInstance().read("/module_param.tpl");

        template.addReplacement("moduleMethodName", module.getMethodName());
        template.addReplacement("moduleName", module.getName());
        template.addReplacement("moduleIndex", String.valueOf(index));

        return template;
    }

    public String createImplementation() {
        Template template = Templates.getInstance().read("/DaggerInjectorConfig.tpl");

        for(Component component : components) {
            template.addReplacementLine("injectorsInit", generateInjector(component));
        }

        return template.toString();
    }

}

package co.ikust.daggerinjector.compiler;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import dagger.Component;

@SupportedAnnotationTypes(
        {
            "dagger.Component"
        }
)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class DaggerInjectorProcessor extends AbstractProcessor {

    /**
     * Used to generate source files.
     */
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        InjectorBuilder builder = new InjectorBuilder();

        //@Component
        for(Element element : roundEnv.getElementsAnnotatedWith(Component.class)) {
            Component componentAnnotation = element.getAnnotation(Component.class);

            TypeElement type = (TypeElement) element;

            String componentName = type.getSimpleName().toString();

            Class<?>[] modules = componentAnnotation.modules();
            ArrayList<String> moduleNames = new ArrayList<>();

            for(Class<?> module : modules) {
                moduleNames.add(module.getName());
            }

            builder.addComponent(componentName, moduleNames);
        }

        generateDatabaseHelperSourceFile(builder);

        return true;
    }

    private void generateDatabaseHelperSourceFile(InjectorBuilder builder) {
        try {
            JavaFileObject adapterSource = filer.createSourceFile("co.ikust.daggerinjector.DaggerInjector");

            Writer writer = adapterSource.openWriter();
            writer.write(builder.createImplementation());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

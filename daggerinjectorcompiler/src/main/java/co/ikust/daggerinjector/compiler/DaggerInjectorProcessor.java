package co.ikust.daggerinjector.compiler;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic;
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

    private static <T> T findAnnotationValue(Element element, String annotationClass,
            String valueName, Class<T> expectedType) {
        T ret = null;
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            DeclaredType annotationType = annotationMirror.getAnnotationType();
            TypeElement annotationElement = (TypeElement) annotationType
                    .asElement();
            if (annotationElement.getQualifiedName().contentEquals(
                    annotationClass)) {
                ret = extractValue(annotationMirror, valueName, expectedType);
                break;
            }
        }
        return ret;
    }

    private static <T> T extractValue(AnnotationMirror annotationMirror,
            String valueName, Class<T> expectedType) {
        Map<ExecutableElement, AnnotationValue> elementValues = new HashMap<ExecutableElement, AnnotationValue>(
                annotationMirror.getElementValues());
        for (Map.Entry<ExecutableElement, AnnotationValue> entry : elementValues
                .entrySet()) {
            if (entry.getKey().getSimpleName().contentEquals(valueName)) {
                Object value = entry.getValue().getValue();
                return expectedType.cast(value);
            }
        }
        return null;
    }

    private String getModuleMethodName(String moduleName) {
        return moduleName.substring(0, 1).toLowerCase() + moduleName.substring(1);
    }

    private String getPackageName(Element element) {
        TypeElement type = (TypeElement) element;

        String simpleName = element.getSimpleName().toString();
        String fullyQualifiedName = type.getQualifiedName().toString();

        return fullyQualifiedName.substring(0, fullyQualifiedName.length() - (simpleName.length() + 1));
    }

    private String getFullyQualifiedName(Element element) {
        TypeElement type = (TypeElement) element;

        return type.getQualifiedName().toString();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        InjectorConfigBuilder builder = new InjectorConfigBuilder();

        //@Component
        for(Element element : roundEnv.getElementsAnnotatedWith(Component.class)) {
            TypeElement type = (TypeElement) element;

            Component componentAnnotation = element.getAnnotation(Component.class);

            String componentName = type.getSimpleName().toString();
            String componentPackage = getPackageName(type);

            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, componentName);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, componentPackage);

            List<AnnotationValue> modules = findAnnotationValue(element, "dagger.Component", "modules", List.class);
            ArrayList<Module> moduleData = new ArrayList<>();

            for(AnnotationValue module : modules) {
                DeclaredType moduleType = (DeclaredType) module.getValue();

                Module moduleMetadata = new Module(
                        getModuleMethodName(moduleType.asElement().getSimpleName().toString()),
                        getFullyQualifiedName(moduleType.asElement())
                );

                moduleData.add(moduleMetadata);
            }

            co.ikust.daggerinjector.compiler.Component component = new co.ikust.daggerinjector.compiler.Component(
                    componentName,
                    componentPackage,
                    moduleData
            );

            builder.addComponent(component);
        }

        generateDatabaseHelperSourceFile(builder);

        return true;
    }

    private void generateDatabaseHelperSourceFile(InjectorConfigBuilder builder) {
        try {
            JavaFileObject adapterSource = filer.createSourceFile("co.ikust.daggerinjector.DaggerInjectorConfig");

            Writer writer = adapterSource.openWriter();
            writer.write(builder.createImplementation());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

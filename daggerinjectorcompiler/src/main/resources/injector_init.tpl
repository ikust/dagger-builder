injectors.put(${packageName}.${componentName}.class, new Injector() {
    @Override
    public Object inject(Object[] modules) {
        return ${packageName}.Dagger_${componentName}.builder()
            ${injectImplementation}
            .build();
    }
});
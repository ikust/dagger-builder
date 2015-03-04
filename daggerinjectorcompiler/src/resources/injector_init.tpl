injectors.put(${componentName}.class, new Injector() {
    @Override
    public Object inject(Object[] modules) {
        return Dagger_${componentName}.builder()
            ${injectImplementation}
            .build();
    }
});
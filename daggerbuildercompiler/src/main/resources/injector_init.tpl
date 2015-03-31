builders.put(${packageName}.${componentName}.class, new Builder() {
    @Override
    public Object build(Object[] modules) {
        return ${packageName}.Dagger_${componentName}.builder()
            ${injectImplementation}
            .build();
    }
});
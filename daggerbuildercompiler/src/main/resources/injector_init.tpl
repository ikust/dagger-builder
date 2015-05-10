builders.put(${packageName}.${componentName}.class, new Builder() {
    @Override
    public Object build(Object[] modules) {
        return ${packageName}.Dagger${componentName}.builder()
            ${injectImplementation}
            .build();
    }
});
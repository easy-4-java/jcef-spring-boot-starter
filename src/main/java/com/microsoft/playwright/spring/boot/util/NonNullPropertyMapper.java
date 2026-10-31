package com.microsoft.playwright.spring.boot.util;

import org.springframework.boot.context.properties.PropertyMapper;

/** Maps only configured values, preserving null-skipping across Spring Boot versions. */
public final class NonNullPropertyMapper {
    private static final PropertyMapper INSTANCE = PropertyMapper.get().alwaysApplying(new PropertyMapper.SourceOperator() {
        @Override
        public <T> PropertyMapper.Source<T> apply(PropertyMapper.Source<T> source) {
            return source.when(value -> value != null);
        }
    });

    private NonNullPropertyMapper() {
    }

    public static PropertyMapper get() {
        return INSTANCE;
    }
}

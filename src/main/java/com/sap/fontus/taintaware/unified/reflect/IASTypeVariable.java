package com.sap.fontus.taintaware.unified.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

public class IASTypeVariable<T extends GenericDeclaration> implements TypeVariable<T> {
    public final TypeVariable<T> original;

    public IASTypeVariable(TypeVariable<T> original) {
        this.original = original;
    }

    @Override
    public Type[] getBounds() {
        return Arrays.stream(this.original.getBounds()).map(IASType::new).toArray(Type[]::new);
    }

    @Override
    public T getGenericDeclaration() {
        return this.original.getGenericDeclaration();
    }

    @Override
    public String getName() {
        return this.original.getName();
    }

    @Override
    public AnnotatedType[] getAnnotatedBounds() {
        return this.original.getAnnotatedBounds();
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return this.original.getAnnotation(annotationClass);
    }

    @Override
    public Annotation[] getAnnotations() {
        return this.original.getAnnotations();
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return this.original.getDeclaredAnnotations();
    }
}
